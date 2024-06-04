package org.race.loko.controllers.export;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@RestController
//@RequestMapping("/user")
public class ExportController {
    ExportService exportService;

    @Autowired
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/export")
    public void generatePdf(HttpServletResponse response) throws IOException {
        Map<String , Object> objects = Map.of(
                "equipe" , "Equipe TEST",
                "points", "69"
        );

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=certificat.pdf");

        OutputStream writer = response.getOutputStream();
        exportService.exportPdf("templates/export/export" , objects , writer);
        writer.close();
    }
}
