package org.race.loko.controllers.business;

import org.race.loko.models.CoureurEtapeForm;
import org.race.loko.models.business.Coureur;
import org.race.loko.models.profil.Equipe;
import org.race.loko.repositories.business.CoureurRepository;
import org.race.loko.repositories.business.CourseRepository;
import org.race.loko.repositories.business.EtapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final CourseRepository courseRepository;
    private final EtapeRepository etapeRepository;
    private final CoureurRepository coureurRepository;

    @Autowired
    public UserController(CourseRepository courseRepository, EtapeRepository etapeRepository, CoureurRepository coureurRepository) {
        this.courseRepository = courseRepository;
        this.etapeRepository = etapeRepository;
        this.coureurRepository = coureurRepository;
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


}
