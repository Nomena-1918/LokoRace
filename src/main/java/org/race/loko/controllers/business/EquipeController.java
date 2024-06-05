package org.race.loko.controllers.business;

import org.postgresql.util.PSQLException;
import org.race.loko.models.business.Coureur;
import org.race.loko.models.business.CoureurEtape;
import org.race.loko.repositories.business.CoureurEtapeRepository;
import org.race.loko.repositories.business.EtapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.util.Objects;

@Controller
@RequestMapping("/equipe")
public class EquipeController {
    private final CoureurEtapeRepository coureurEtapeRepository;
    private final EtapeRepository etapeRepository;

    @Autowired
    public EquipeController(CoureurEtapeRepository coureurEtapeRepository, EtapeRepository etapeRepository) {
        this.coureurEtapeRepository = coureurEtapeRepository;
        this.etapeRepository = etapeRepository;
    }

    @GetMapping("/home")
    public String home() {
        return "pages/home";
    }


    @PostMapping("/affecter-coureur")
    public String affecterCoureur(@RequestParam Long idEtape, @RequestParam Long idCoureur, RedirectAttributes redirectAttributes) {
        try {
            CoureurEtape coureurEtape = new CoureurEtape();
            var etape = etapeRepository.findById(idEtape);
            if (etape.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "L'étape n'existe pas");
                return "redirect:/user/liste-etape";
            }
            coureurEtape.setEtape(etape.get());
            coureurEtape.setCoureur(new Coureur().setId(idCoureur));
            coureurEtape.setDateHeureDepart(etape.get().getDateHeureDebut());

            coureurEtapeRepository.save(coureurEtape);
        } catch (DataAccessException e) {
            /*if (e.getCause() instanceof PSQLException psqlException) {
                //String errorMessage = Objects.requireNonNull(psqlException.getServerErrorMessage()).getMessage();
                //String errorMessage = "Le nombre de coureurs est dépassé";
                redirectAttributes.addFlashAttribute("error", "Erreur lors de l'affectation du coureur : " + errorMessage);
            } else {
                // Handle other types of DataAccessExceptions
                redirectAttributes.addFlashAttribute("error", "Erreur lors de l'affectation du coureur : " + e.getMessage());
            }*/
            String errorMessage = "Le nombre de coureurs est dépassé";
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'affectation du coureur : " + errorMessage);
            return "redirect:/user/liste-etape";
        }

        catch (Exception e) {
            String errorMessage = e.getMessage();
            //String errorMessage = "Le nombre de coureurs est dépassé";

            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'affectation du coureur : " + errorMessage);
            return "redirect:/user/liste-etape";
        }



        return "redirect:/user/liste-etape";
    }

}
