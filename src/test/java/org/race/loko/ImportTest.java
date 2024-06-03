package org.race.loko;

import org.junit.jupiter.api.Test;
import org.race.loko.utils.csv.service.ImportEtapeService;
import org.race.loko.utils.csv.service.ImportPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
public class ImportTest {
    private final ImportEtapeService importEtapeService;
    private final ImportPointService importPointService;
    @Autowired
    public ImportTest(ImportEtapeService importEtapeService, ImportPointService importPointService) {
        this.importEtapeService = importEtapeService;
        this.importPointService = importPointService;
    }

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
}
