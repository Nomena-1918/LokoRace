package org.race.loko.controllers.business;

import jakarta.transaction.Transactional;
import org.postgresql.util.PSQLException;
import org.race.loko.models.CoureurEtapeForm;
import org.race.loko.models.business.CoureurEtape;
import org.race.loko.repositories.business.CoureurEtapeRepository;
import org.race.loko.utils.csv.service.ImportEtapeService;
import org.race.loko.utils.csv.service.ImportPointService;
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

    @Autowired
    public AdminController(CoureurEtapeRepository coureurEtapeRepository, ImportEtapeService importEtapeService, ImportPointService importPointService) {
        this.coureurEtapeRepository = coureurEtapeRepository;
        this.importEtapeService = importEtapeService;
        this.importPointService = importPointService;
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

            coureurEtape.setDateHeureDepart(coureurEtapeForm.getDateHeureDepart());
            coureurEtape.setDateHeureArrivee(coureurEtapeForm.getDateHeureDepart().plus(dureeCourse));
            //coureurEtape.setDureePenalite(coureurEtapeForm.getDureePenalite());

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
        //List<String> l1 =  importDevisService.importDataFile(fileDataDevis);


        model.addAttribute("message", etapeMess);
        //model.addAttribute("message1", l1);

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


}






