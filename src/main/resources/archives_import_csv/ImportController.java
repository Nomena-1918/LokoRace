package archives_csv;/*package org.race.loko.controllers;

import com.bTP.service.csv.service.ImportDevisService;
import com.bTP.service.csv.service.ImportMaisonTravauxService;
import com.bTP.service.csv.service.ImportPaiementService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class ImportController {
    private final ImportMaisonTravauxService importMaisonTravauxService;
    private final ImportDevisService importDevisService;
    private final ImportPaiementService importPaiementService;

    @Autowired
    public ImportController(ImportMaisonTravauxService importMaisonTravauxService, ImportDevisService importDevisService, ImportPaiementService importPaiementService) {
        this.importMaisonTravauxService = importMaisonTravauxService;
        this.importDevisService = importDevisService;
        this.importPaiementService = importPaiementService;
    }

    @GetMapping("/import-maison-travaux")
    public String importMaisonTravaux(Model model) {
        model.addAttribute("viewpage", "importMaisonTravaux");
        return "layout/layout";
    }

    @GetMapping("/import-paiement")
    public String importPaiement(Model model) {
        model.addAttribute("viewpage", "importPaiement");
        return "layout/layout";
    }

    @Transactional
    @PostMapping("/import-maison-travaux")
    public String importMaisonTravaux(@RequestParam MultipartFile maisonTravaux, @RequestParam MultipartFile devis, Model model) throws IOException {
        /*File fileDataMaison = File.createTempFile("uploaded-", maisonTravaux.getOriginalFilename());
        maisonTravaux.transferTo(fileDataMaison);
                                                */
        File  fileDataMaison = new File(Objects.requireNonNull(maisonTravaux.getOriginalFilename()));
        byte[] bytes = maisonTravaux.getBytes();
        try(FileOutputStream fos = new FileOutputStream(fileDataMaison)) {
            fos.write(bytes);
        }
        List<String> l = importMaisonTravauxService.importDataFile(fileDataMaison);

        File  fileDataDevis = new File(Objects.requireNonNull(devis.getOriginalFilename()));
        byte[] bytes1 = devis.getBytes();
        try(FileOutputStream fos = new FileOutputStream(fileDataDevis)) {
            fos.write(bytes1);
        }
        List<String> l1 =  importDevisService.importDataFile(fileDataDevis);

        model.addAttribute("viewpage", "importMaisonTravaux");
        model.addAttribute("message", l);
        model.addAttribute("message1", l1);

        return "layout/layout";
    }

    @Transactional
    @PostMapping("/import-paiement")
    public String importPaiement(@RequestParam MultipartFile paiement, Model model) throws IOException {
        File  fileDataMaison = new File(Objects.requireNonNull(paiement.getOriginalFilename()));
        byte[] bytes1 = paiement.getBytes();
        try(FileOutputStream fos = new FileOutputStream(fileDataMaison)) {                                 
            fos.write(bytes1);
        }
        List<String> l = importPaiementService.importDataFile(fileDataMaison);

        model.addAttribute("viewpage", "importPaiement");
        model.addAttribute("message", l);

        return "layout/layout";
    }


}
