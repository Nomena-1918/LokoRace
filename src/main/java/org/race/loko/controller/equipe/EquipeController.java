package org.race.loko.controller.equipe;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equipe")
public class EquipeController {

    @GetMapping("/home")
    public String home() {
        return "pages/home";
    }

    @GetMapping("/liste-etape")
    public String listeEtape() {
        return "pages/equipe/liste-etape";
    }

}
