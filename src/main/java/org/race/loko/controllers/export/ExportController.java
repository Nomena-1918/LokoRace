package org.race.loko.controllers.export;

import jakarta.servlet.http.HttpServletResponse;
import org.race.loko.models.business.views.ClassementEquipe;
import org.race.loko.repositories.business.views.ClassementEquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

//@RestController
@RequestMapping("/user")

@Controller
public class ExportController {
    ExportService exportService;
    private final ClassementEquipeRepository classementEquipeRepository;

    @Autowired
    public ExportController(ExportService exportService, ClassementEquipeRepository classementEquipeRepository) {
        this.exportService = exportService;
        this.classementEquipeRepository = classementEquipeRepository;
    }

    @GetMapping("/export")
    @ResponseBody
    public void generatePdf(HttpServletResponse response) throws IOException {
        List<ClassementEquipe> premiers = classementEquipeRepository.findBestEquipe();
        ClassementEquipe prems = premiers.get(0);

        Map<String , Object> objects = Map.of(
                "course", prems.getCourse().getNom(),
                "equipe" , prems.getEquipe().getNom(),
                "rang", prems.getRangEquipe()+"e",
                "points", prems.getPoints()
        );

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=certificat.pdf");

        OutputStream writer = response.getOutputStream();
        exportService.exportPdf("templates/export/export" , objects , writer);
        writer.close();

    }
/*
    @GetMapping("/export")
    public String generatePdf(Model model) throws IOException {

        model.addAttribute("equipe" , "Equipe TEST");
        model.addAttribute("points", "69");

        return "export/exportPDF";
    }
*/
}
