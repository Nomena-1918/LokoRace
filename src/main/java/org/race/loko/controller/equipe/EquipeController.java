package org.race.loko.controller.equipe;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equipe")
public class EquipeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("pageTitle", "EQUIPE page");
        model.addAttribute("subTitle", "Loko Race - Home page");
        return "pages/home";
    }


}
