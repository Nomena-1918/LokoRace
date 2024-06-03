package org.race.loko.utils.csv.service;

import jakarta.transaction.Transactional;
import org.race.loko.utils.csv.CSVService;
import org.race.loko.utils.csv.dto.EtapeCSV;
import org.race.loko.utils.csv.dto.ResultatCSV;
import org.race.loko.utils.csv.repo.ImportEtapeRepository;
import org.race.loko.utils.csv.repo.ImportResultatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*

    Méthodes Import (Excel, CSV) "ELT" :

    Nettoyer les données (trim)
    Transformer en liste d'objets exactement
    Insérer dans une table même structure
    Insert from select (group by pour éviter les doublons) vers les autres tables (normalisées)
        Si erreur insert dans une table d'erreur temporaire et continuer
    Afficher les erreurs durant l'import (avec la ligne)

 */

@Service
public class ImportResultatService {
    private final ImportResultatRepository importResultatRepository;

    @Autowired
    public ImportResultatService(ImportResultatRepository importResultatRepository) {
        this.importResultatRepository = importResultatRepository;
    }


    @Transactional
    public List<String> importDataFile(File csvFilePath) {
        List<String> output = new ArrayList<>();
        try (FileReader reader = new FileReader(csvFilePath)) {
            // 1. Create temporary table
            importResultatRepository.createTemporaryTable();

            List<ResultatCSV> list = CSVService.readObjectsFromCSV(ResultatCSV.class, reader);
            for (int k = 0; k < list.size(); k++) {
                try {
                    // 2. Insert into temporary table
                    importResultatRepository.insertIntoTemporaryTable(list.get(k));
                } catch (Exception e) {
                    output.add("Error importing line " + k + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            output.add("Error reading CSV file: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            // 3. Import data into tables
            importResultatRepository.importDataIntoTables();

            // 4. Drop temporary table
            importResultatRepository.dropTemporaryTable();
        }
        catch (Exception e) {
            output.add("Error importing data into tables: " + e.getMessage());
        }

        if (output.isEmpty())
            output.add("Data imported successfully !");

        return output;
    }

}