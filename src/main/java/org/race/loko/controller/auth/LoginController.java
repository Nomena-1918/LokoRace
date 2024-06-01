package org.race.loko.controller.auth;

import jakarta.servlet.http.HttpSession;
import org.race.loko.model.profil.Admin;
import org.race.loko.model.profil.Equipe;
import org.race.loko.repository.profil.AdminRepository;
import org.race.loko.repository.profil.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final EquipeRepository equipeRepository;
    private final AdminRepository adminRepository;
    @Value("${session.keyuser}")
    private String keyusername="user";


    @Autowired
    public LoginController(EquipeRepository equipeRepository, AdminRepository adminRepository) {
        this.equipeRepository = equipeRepository;
        this.adminRepository = adminRepository;
    }

// ADMIN
    @GetMapping("/login-admin")
    public String logInAdmin(@RequestParam(required = false) String message, Model model) {
        model.addAttribute(new Admin());
        model.addAttribute("message", message);
        return "login-admin";
    }
    @PostMapping(value = "/submit-login-admin")
    public String submitLoginAdmin(HttpSession session, @ModelAttribute Admin adminForm, Model model) {
        Optional<Admin> admin = adminRepository.findAdminByEmailAndAndMdp(adminForm.getEmail(), adminForm.getMdp());

        if (admin.isPresent()) {
            session.setAttribute(keyusername, admin.get());
            model.addAttribute("pageTitle", "Home page");
            model.addAttribute("subTitle", "Loko Race - Home page");
            return "pages/home";
        }
        else {
            model.addAttribute("message", "Admin absent de la base de données");
            return "login-admin";
        }
    }


// EQUIPE
    @GetMapping("/login-equipe")
    public String logInEquipe(@RequestParam(required = false) String message, Model model) {
        model.addAttribute(new Equipe());
        model.addAttribute("message", message);
        return "login-equipe";
    }

    @PostMapping(value = "/submit-login-equipe")
    public String submitLoginEquipe(HttpSession session, @ModelAttribute Equipe equipeForm, Model model) {
        Optional<Equipe> equipe = equipeRepository.findEquipeByEmailAndAndMdp(equipeForm.getEmail(), equipeForm.getMdp());

        if (equipe.isPresent()) {
            session.setAttribute(keyusername, equipe.get());
            model.addAttribute("pageTitle", "Home page");
            model.addAttribute("subTitle", "Loko Race - Home page");
            return "pages/home";
        }
        else {
            model.addAttribute("message", "Equipe absente de la base de données");
            return "login-equipe";
        }
    }

////////////////////
    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login-equipe";
    }
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "pages/error/error-403";
    }
///////////////////

}
