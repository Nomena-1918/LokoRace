package org.race.loko.controllers.business;

import jakarta.transaction.Transactional;
import org.postgresql.util.PSQLException;
import org.race.loko.models.business.Etape;
import org.race.loko.models.business.PenaliteEtapeEquipe;
import org.race.loko.models.dto.CoureurEtapeForm;
import org.race.loko.models.business.CoureurEtape;
import org.race.loko.models.dto.PenaliteEquipeForm;
import org.race.loko.models.profil.Equipe;
import org.race.loko.repositories.business.*;
import org.race.loko.repositories.profil.EquipeRepository;
import org.race.loko.utils.csv.service.ImportEtapeService;
import org.race.loko.utils.csv.service.ImportPointService;
import org.race.loko.utils.csv.service.ImportResultatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CoureurEtapeRepository coureurEtapeRepository;
    private final ImportEtapeService importEtapeService;
    private final ImportPointService importPointService;
    private final ImportResultatService importResultatService;
    private final CoureurCategorieRepository coureurCategorieRepository;
    private final CourseRepository courseRepository;
    private final PenaliteEtapeEquipeRepository penaliteEtapeEquipeRepository;
    private final EtapeRepository etapeRepository;
    private final EquipeRepository equipeRepository;

    @Autowired
    public AdminController(CoureurEtapeRepository coureurEtapeRepository, ImportEtapeService importEtapeService, ImportPointService importPointService, ImportResultatService importResultatService, CoureurCategorieRepository coureurCategorieRepository, CourseRepository courseRepository, PenaliteEtapeEquipeRepository penaliteEtapeEquipeRepository, EtapeRepository etapeRepository, EquipeRepository equipeRepository) {
        this.coureurEtapeRepository = coureurEtapeRepository;
        this.importEtapeService = importEtapeService;
        this.importPointService = importPointService;
        this.importResultatService = importResultatService;
        this.coureurCategorieRepository = coureurCategorieRepository;
        this.courseRepository = courseRepository;
        this.penaliteEtapeEquipeRepository = penaliteEtapeEquipeRepository;
        this.etapeRepository = etapeRepository;
        this.equipeRepository = equipeRepository;
    }

    @GetMapping("/home")
    public String home() {
        return "pages/home";
    }


    @PostMapping("/affecter-temps-coureur")
    public String affecterTempsCoureur(@RequestParam Long idEtape, @ModelAttribute CoureurEtapeForm coureurEtapeForm, RedirectAttributes redirectAttributes) {
        try {
            CoureurEtape coureurEtape = coureurEtapeRepository.findByEtapeIdAndCoureurId(idEtape, coureurEtapeForm.getIdCoureur());

            if (coureurEtape == null) {
                redirectAttributes.addFlashAttribute("error", "Le coureur n'est pas affecté à cette étape");
                return "redirect:/user/liste-etape";
            }

            // Convert LocalTime to Duration
            Duration dureeCourse = Duration.between(LocalTime.MIDNIGHT, coureurEtapeForm.getTemps());
            coureurEtape.setDateHeureDepart(coureurEtape.getEtape().getDateHeureDebut());

            coureurEtape.setDateHeureArrivee(coureurEtapeForm.getDateHeureDepart().plus(dureeCourse));

            coureurEtapeRepository.save(coureurEtape);
        } catch (DataAccessException e) {
            if (e.getCause() instanceof PSQLException) {
                redirectAttributes.addFlashAttribute("error", "Erreur lors de l'affectation du coureur : " + e.getMessage());
            } else {
                // Handle other types of DataAccessExceptions
                redirectAttributes.addFlashAttribute("error", "Erreur lors de l'affectation du coureur : " + e.getMessage());
            }
            return "redirect:/user/liste-etape";
        }
        return "redirect:/user/liste-etape";
    }

    @GetMapping("/import-data")
    public String importData() {
        return "pages/import/import-data";
    }

    @Transactional
    @PostMapping("/import-data")
    public String importData(@RequestParam MultipartFile etapes, @RequestParam MultipartFile resultats, Model model) throws IOException {
        /*File fileDataMaison = File.createTempFile("uploaded-", maisonTravaux.getOriginalFilename());
        maisonTravaux.transferTo(fileDataMaison);*/

        File fileDataEtapes = new File(Objects.requireNonNull(etapes.getOriginalFilename()));
        byte[] bytes = etapes.getBytes();
        try (FileOutputStream fos = new FileOutputStream(fileDataEtapes)) {
            fos.write(bytes);
        }
        List<String> etapeMess = importEtapeService.importDataFile(fileDataEtapes);


        File fileDataResultats = new File(Objects.requireNonNull(resultats.getOriginalFilename()));
        byte[] bytes1 = resultats.getBytes();
        try (FileOutputStream fos = new FileOutputStream(fileDataResultats)) {
            fos.write(bytes1);
        }
        List<String> resultatMess =  importResultatService.importDataFile(fileDataResultats);


        model.addAttribute("message", etapeMess);
        model.addAttribute("message1", resultatMess);

        return "pages/import/import-data";
    }

    @GetMapping("/import-points")
    public String importPoints() {
        return "pages/import/import-points";
    }


    @Transactional
    @PostMapping("/import-points")
    public String importPoints(@RequestParam MultipartFile points, Model model) throws IOException {
        /*File fileDataMaison = File.createTempFile("uploaded-", maisonTravaux.getOriginalFilename());
        maisonTravaux.transferTo(fileDataMaison);*/

        File fileDataPoints = new File(Objects.requireNonNull(points.getOriginalFilename()));
        byte[] bytes = points.getBytes();
        try (FileOutputStream fos = new FileOutputStream(fileDataPoints)) {
            fos.write(bytes);
        }
        List<String> pointMess = importPointService.importDataFile(fileDataPoints);


        model.addAttribute("message", pointMess);

        return "pages/import/import-points";
    }

    @GetMapping("/assign-categ-coureur")
    public String assignCategCoureur() {
        return "pages/import/assign-categ-coureur";
    }

    @Transactional
    @PostMapping("/assign-categ-coureur")
    public String assignCategCoureur(Model model) {
        String message = null;
        try {
            coureurCategorieRepository.assignCategoriesToRunners();
        }
        catch (Exception e) {
            message = e.getMessage();
        }
        if (message == null)
            message = "Catégories affectées aux coureurs !";

        model.addAttribute("message", message);

        return "pages/import/assign-categ-coureur";
    }


    @GetMapping("/penalite-equipe")
    public String penaliteEquipe(Model model) {
        var course = courseRepository.findLatestCourse();
        var listePenalite = penaliteEtapeEquipeRepository.findAllByCourseId(course.getId());
        var etapes = etapeRepository.findByCourseId(course.getId());
        var equipes = equipeRepository.findAll();

        model.addAttribute("course", course);
        model.addAttribute("listePenalite", listePenalite);
        model.addAttribute("etapes", etapes);
        model.addAttribute("equipes", equipes);
        model.addAttribute("penaliteEquipeForm", new PenaliteEquipeForm());

        if (model.containsAttribute("error")) {
            String errorMessage = (String) model.getAttribute("error");
            model.addAttribute("error", errorMessage);
        }

        return "pages/penalite/penalite-equipe";
    }

    @PostMapping("/penalite-equipe")
    public String ajouterPenalite(@ModelAttribute PenaliteEquipeForm penaliteEquipeForm,
                                  RedirectAttributes redirectAttributes) {
        try {
            PenaliteEtapeEquipe penalite = new PenaliteEtapeEquipe();
            penalite.setEtape(new Etape().setId(penaliteEquipeForm.getEtape()));
            penalite.setEquipe(new Equipe().setId(penaliteEquipeForm.getEquipe()));
            Duration dureePenalite = Duration.between(LocalTime.MIDNIGHT, penaliteEquipeForm.getDureePenalite());

            penalite.setDureePenalite(dureePenalite);
            penaliteEtapeEquipeRepository.save(penalite);
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            throw e;
        }

        return "redirect:/admin/penalite-equipe";
    }

    @PostMapping("/penalite-equipe/delete")
    public String supprimerPenalite(@RequestParam("id") Long penaliteId, RedirectAttributes redirectAttributes) {
        try {
            // Ajouter la logique pour supprimer une pénalité
            penaliteEtapeEquipeRepository.deleteById(penaliteId);
        }
        catch (Exception e ) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/penalite-equipe";
    }




}






