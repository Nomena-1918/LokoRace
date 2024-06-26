package org.race.loko.controllers.business;

import org.race.loko.models.business.Categorie;
import org.race.loko.models.business.Coureur;
import org.race.loko.models.business.views.ClassementEquipe;
import org.race.loko.models.business.views.ClassementEquipeCategorie;
import org.race.loko.models.dto.CoureurEtapeForm;
import org.race.loko.models.profil.Equipe;
import org.race.loko.repositories.business.CoureurRepository;
import org.race.loko.repositories.business.CourseRepository;
import org.race.loko.repositories.business.EtapeRepository;
import org.race.loko.repositories.business.views.ClassementCoureurEtapeRepository;
import org.race.loko.repositories.business.views.ClassementEquipeCategorieRepository;
import org.race.loko.repositories.business.views.ClassementEquipeRepository;
import org.race.loko.repositories.business.views.DetailClassementEquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private final CourseRepository courseRepository;
    private final EtapeRepository etapeRepository;
    private final CoureurRepository coureurRepository;
    private final ClassementCoureurEtapeRepository classementCoureurEtapeRepository;
    private final ClassementEquipeRepository classementEquipeRepository;
    private final ClassementEquipeCategorieRepository classementEquipeCategorieRepository;
    private final DetailClassementEquipeRepository detailClassementEquipeRepository;

    @Autowired
    public UserController(CourseRepository courseRepository, EtapeRepository etapeRepository, CoureurRepository coureurRepository, ClassementCoureurEtapeRepository classementCoureurEtapeRepository, ClassementEquipeRepository classementEquipeRepository, ClassementEquipeCategorieRepository classementEquipeCategorieRepository, DetailClassementEquipeRepository detailClassementEquipeRepository) {
        this.courseRepository = courseRepository;
        this.etapeRepository = etapeRepository;
        this.coureurRepository = coureurRepository;
        this.classementCoureurEtapeRepository = classementCoureurEtapeRepository;
        this.classementEquipeRepository = classementEquipeRepository;
        this.classementEquipeCategorieRepository = classementEquipeCategorieRepository;
        this.detailClassementEquipeRepository = detailClassementEquipeRepository;
    }

    @GetMapping("/home")
    public String home() {
        return "pages/home";
    }

    @GetMapping("/liste-etape")
    public String listeEtape(Model model, @SessionAttribute("user") Object user) {
        var course = courseRepository.findLatestCourse();
        var listeEtapes = etapeRepository.findByCourseId(course.getId());

        List<Coureur> listeCoureurs = new ArrayList<>();
        if (user instanceof Equipe)
            listeCoureurs = coureurRepository.findByEquipeId(((Equipe) user).getId());

        if (model.containsAttribute("error")) {
            String errorMessage = (String) model.getAttribute("error");
            model.addAttribute("error", errorMessage);
        }

        model.addAttribute("course", course);
        model.addAttribute("listeEtapes", listeEtapes);
        model.addAttribute("listeCoureurs", listeCoureurs);
        model.addAttribute("coureurEtapeForm", new CoureurEtapeForm());

        return "pages/etape/liste-etape";
    }

    /*@GetMapping("/classement-general-individuel")
    public String classementGeneralIndividuel(Model model) {
        var course = courseRepository.findLatestCourse();
        var classements = classementCoureurEtapeRepository.findByCourseId(course.getId());

// Regrouper les classements par étape
        Map<Etape, List<ClassementCoureurEtape>> classementsParEtape = classements.stream()
                .collect(Collectors.groupingBy(classement -> classement.getCoureurEtape().getEtape()));

        model.addAttribute("course", course);
        model.addAttribute("classementsParEtape", classementsParEtape); // Passer les classements regroupés au modèle

        return "pages/classement/classement-general-individuel";
    }*/

    @GetMapping("/classement-general-individuel")
    public String classementGeneralIndividuel(Model model) {
        var course = courseRepository.findLatestCourse();
        var classements = classementCoureurEtapeRepository.findByCourseId(course.getId());

        model.addAttribute("course", course);
        model.addAttribute("classements", classements);

        return "pages/classement/classement-general-individuel";
    }

    @GetMapping("/classement-general-individuel-etape/{nom}")
    public String classementGeneralIndividuel(@PathVariable("nom") String etape, Model model) {
        var detailClassementEquipes = detailClassementEquipeRepository.findAllByEquipeNomOrderByEtapeRangEtape(etape);
        model.addAttribute("details", detailClassementEquipes);

        return "pages/classement/classement-general-individuel-etape";
    }


    @GetMapping("/classement-general-equipe")
    public String classementGeneralEquipe(Model model) {
        var course = courseRepository.findLatestCourse();
        var classements = classementEquipeRepository.findByCourseId(course.getId());

        model.addAttribute("course", course);
        model.addAttribute("classements", classements);

        List<String> listEquipe = new ArrayList<>();
        List<Integer> listPoints = new ArrayList<>();
        for (ClassementEquipe c : classements) {
            listEquipe.add(c.getEquipe().getNom());
            listPoints.add(c.getPoints());
        }

        model.addAttribute("listEquipe", listEquipe);
        model.addAttribute("listPoints", listPoints);

        return "pages/classement/classement-general-equipe";
    }

    @GetMapping("/classement-general-equipe-categorie")
    public String classementGeneralEquipeCategorie(Model model) {
        var course = courseRepository.findLatestCourse();
        var classements = classementEquipeCategorieRepository.findByCourseId(course.getId());

        // Trier les classements par nom de catégorie
        classements.sort(Comparator.comparing(item -> item.getCategorie().getNom()));

        // Regrouper les classements par catégorie
        Map<Categorie, List<ClassementEquipeCategorie>> groupedByCategory = classements.stream()
                .collect(Collectors.groupingBy(ClassementEquipeCategorie::getCategorie));

        //groupedByCategory = new LinkedHashMap<>(groupedByCategory);

        // ASSIGNATION COULEUR EX AQUEO
        String couleur = "bg-success text-dark";
        ClassementEquipeCategorie precedent, actuelle;
        for (Map.Entry<Categorie, List<ClassementEquipeCategorie>> entry : groupedByCategory.entrySet()) {
            for (int i = 1; i < entry.getValue().size(); i++) {
                precedent = entry.getValue().get(i - 1);
                actuelle = entry.getValue().get(i);

                if (Objects.equals(actuelle.getRangEquipe(), precedent.getRangEquipe())) {
                    precedent.couleur = couleur;
                    actuelle.couleur = couleur;
                }
            }
        }


        model.addAttribute("course", course);
        model.addAttribute("groupedByCategory", groupedByCategory);

        // Données pour Charts
        var categoriesData = groupedByCategory.entrySet().stream()
                .map(entry -> new HashMap<String, Object>() {{
                    put("id", entry.getKey().getId());
                    put("labels", entry.getValue().stream().map(item -> item.getEquipe().getNom()).collect(Collectors.toList()));
                    put("data", entry.getValue().stream().map(ClassementEquipeCategorie::getPoints).collect(Collectors.toList()));
                }}).collect(Collectors.toList());

        model.addAttribute("categoriesData", categoriesData);


        return "pages/classement/classement-general-equipe-categorie";
    }


}
