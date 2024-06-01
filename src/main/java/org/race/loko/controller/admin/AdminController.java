package org.race.loko.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("pageTitle", "ADMIN page");
        model.addAttribute("subTitle", "Loko Race - Home page");
        return "pages/home";
    }
}
