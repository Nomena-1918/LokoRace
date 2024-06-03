package service;

import com.bTP.service.csv.CSVService;
import com.bTP.service.csv.dto.MaisonTravauxCSV;
import com.bTP.service.csv.repo.ImportMaisonTravauxRepository;
import jakarta.transaction.Transactional;
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
public class ImportMaisonTravauxService {
    private final ImportMaisonTravauxRepository importRepository;

    @Autowired
    public ImportMaisonTravauxService(ImportMaisonTravauxRepository importRepository) {
        this.importRepository = importRepository;
    }


    @Transactional
    public List<String> importDataFile(File csvFilePath) {
        List<String> output = new ArrayList<>();
        try (FileReader reader = new FileReader(csvFilePath)) {
            // 1. Create temporary table
            importRepository.createTemporaryTable();

            List<MaisonTravauxCSV> list = CSVService.readObjectsFromCSV(MaisonTravauxCSV.class, reader);
            for (int k = 0; k < list.size(); k++) {
                try {
                    // 2. Insert into temporary table
                    importRepository.insertIntoTemporaryTable(list.get(k));
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
            importRepository.importDataIntoTables();

            // 4. Drop temporary table
            importRepository.dropTemporaryTable();
        }
        catch (Exception e) {
            output.add("Error importing data into tables: " + e.getMessage());
        }

        if (output.isEmpty())
            output.add("Data imported successfully !");

        return output;
    }


    @Transactional
    public List<String> importData(String csvFilePath) {
        List<String> output = new ArrayList<>();
        try (FileReader reader = new FileReader(csvFilePath)) {
            // 1. Create temporary table
            importRepository.createTemporaryTable();

            List<MaisonTravauxCSV> list = CSVService.readObjectsFromCSV(MaisonTravauxCSV.class, reader);
            for (int k = 0; k < list.size(); k++) {
                try {
                    // 2. Insert into temporary table
                    importRepository.insertIntoTemporaryTable(list.get(k));
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
            importRepository.importDataIntoTables();

            // 4. Drop temporary table
            importRepository.dropTemporaryTable();
        }
        catch (Exception e) {
            output.add("Error importing data into tables: " + e.getMessage());
        }

        if (output.isEmpty())
            output.add("Data imported successfully !");

        return output;
    }
}