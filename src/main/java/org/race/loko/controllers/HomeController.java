package org.race.loko.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping
    public String index() {
        return "redirect:/login-equipe";
    }

    @GetMapping("/home")
    public String home() {
        return "pages/home";
    }

}
