package org.race.loko.controller.equipe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equipe")
public class EquipeController {
    @GetMapping(value = "/home")
    public String equipeHome() {
        return "pages/equipe-home";
    }

}
