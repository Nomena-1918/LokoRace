package org.race.loko.controllers.equipe;

import org.race.loko.models.business.Coureur;
import org.race.loko.models.business.CoureurEtape;
import org.race.loko.models.business.Etape;
import org.race.loko.models.profil.Equipe;
import org.race.loko.repositories.business.CoureurEtapeRepository;
import org.race.loko.repositories.business.CoureurRepository;
import org.race.loko.repositories.business.CourseRepository;
import org.race.loko.repositories.business.EtapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/equipe")
public class EquipeController {
    private final CourseRepository courseRepository;
    private final EtapeRepository etapeRepository;
    private final CoureurRepository coureurRepository;
    private final CoureurEtapeRepository coureurEtapeRepository;

    @Autowired
    public EquipeController(CourseRepository courseRepository, EtapeRepository etapeRepository, CoureurRepository coureurRepository, CoureurEtapeRepository coureurEtapeRepository) {
        this.courseRepository = courseRepository;
        this.etapeRepository = etapeRepository;
        this.coureurRepository = coureurRepository;
        this.coureurEtapeRepository = coureurEtapeRepository;
    }

    @GetMapping("/home")
    public String home() {
        return "pages/home";
    }

    @GetMapping("/liste-etape")
    public String listeEtape(Model model, @SessionAttribute("user") Equipe equipe) {
        var course = courseRepository.findLatestCourse();
        var listeEtapes = etapeRepository.findByCourseId(course.getId());
        var listeCoureurs = coureurRepository.findByEquipeId(equipe.getId());

        model.addAttribute("course", course);
        model.addAttribute("listeEtapes", listeEtapes);
        model.addAttribute("listeCoureurs", listeCoureurs);

        return "pages/equipe/liste-etape";
    }



    @PostMapping("/affecter-coureur")
    public String affecterCoureur(@RequestParam Long idEtape, @RequestParam Long idCoureur) {
        CoureurEtape coureurEtape = new CoureurEtape();
        coureurEtape.setEtape(new Etape().setId(idEtape));
        coureurEtape.setCoureur(new Coureur().setId(idCoureur));
        coureurEtape.setDateHeureDepart(LocalDateTime.now());
        coureurEtape.setDureePenalite(Duration.ZERO);

        coureurEtapeRepository.save(coureurEtape);

        return "redirect:/liste-etape";
    }



}
