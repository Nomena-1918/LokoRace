package org.race.loko.controllers.business;

import org.postgresql.util.PSQLException;
import org.race.loko.models.CoureurEtapeForm;
import org.race.loko.models.business.CoureurEtape;
import org.race.loko.repositories.business.CoureurEtapeRepository;
import org.race.loko.repositories.business.CoureurRepository;
import org.race.loko.repositories.business.CourseRepository;
import org.race.loko.repositories.business.EtapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.LocalTime;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CoureurEtapeRepository coureurEtapeRepository;


    @Autowired
    public AdminController(CoureurEtapeRepository coureurEtapeRepository) {
        this.coureurEtapeRepository = coureurEtapeRepository;
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
}






