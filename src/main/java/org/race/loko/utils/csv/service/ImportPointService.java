package org.race.loko.utils.csv.service;

import jakarta.transaction.Transactional;
import org.race.loko.models.business.PointClassement;
import org.race.loko.repositories.business.PointClassementRepository;
import org.race.loko.utils.csv.CSVService;
import org.race.loko.utils.csv.dto.EtapeCSV;
import org.race.loko.utils.csv.dto.PointCSV;
import org.race.loko.utils.csv.repo.ImportEtapeRepository;
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
public class ImportPointService {
    private final PointClassementRepository pointClassementRepository;

    @Autowired
    public ImportPointService(PointClassementRepository pointClassementRepository) {
        this.pointClassementRepository = pointClassementRepository;
    }

    @Transactional
    public List<String> importDataFile(File csvFilePath) {
        List<String> output = new ArrayList<>();
        List<PointCSV> list = new ArrayList<>();
        try (FileReader reader = new FileReader(csvFilePath)) {
            list = CSVService.readObjectsFromCSV(PointCSV.class, reader);
        } catch (IOException e) {
            output.add("Error reading CSV file: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            List<PointClassement> pointClassementList = new ArrayList<>();
            for (PointCSV p : list)
                pointClassementList.add(new PointClassement(p));

            pointClassementRepository.saveAll(pointClassementList);
        }
        catch (Exception e) {
            output.add("Error importing data into tables: " + e.getMessage());
        }

        if (output.isEmpty())
            output.add("Data imported successfully !");

        return output;
    }

}