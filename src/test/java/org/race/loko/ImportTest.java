package org.race.loko;

import org.race.loko.utils.csv.service.ImportEtapeService;
import org.race.loko.utils.csv.service.ImportPointService;
import org.race.loko.utils.csv.service.ImportResultatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImportTest {
    private final ImportEtapeService importEtapeService;
    private final ImportPointService importPointService;
    private final ImportResultatService importResultatService;

    @Autowired
    public ImportTest(ImportEtapeService importEtapeService, ImportPointService importPointService, ImportResultatService importResultatService) {
        this.importEtapeService = importEtapeService;
        this.importPointService = importPointService;
        this.importResultatService = importResultatService;
    }
/*
    @Test
    void etape() {
        List<String> output = importEtapeService.importDataFile(new File("src/main/resources/data/etape.csv"));

        System.out.println("\n\nOutput : \n");
        for (String s : output)
            System.out.println(s);
        System.out.println("\n\n");
    }

    @Test
    void point() {
        List<String> output = importPointService.importDataFile(new File("src/main/resources/data/points.csv"));

        System.out.println("\n\nOutput : \n");
        for (String s : output)
            System.out.println(s);
        System.out.println("\n\n");
    }

    @Test
    void resultats() {
        List<String> output = importResultatService.importDataFile(new File("src/main/resources/data/resultat.csv"));

        System.out.println("\n\nOutput : \n");
        for (String s : output)
            System.out.println(s);
        System.out.println("\n\n");
    }

    @Test
    void name() {
        // Exemple de durée
        Duration duration = Duration
                .ofHours(2)
                .plusMinutes(30)
                .plusSeconds(45); // 2 heure, 30 minutes et 45 secondes

        // Formater la durée en HH:mm:ss
        String formattedDuration = formatDuration(duration);

        // Afficher le résultat
        System.out.println(formattedDuration);
    }*/
}
