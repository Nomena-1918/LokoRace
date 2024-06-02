package org.race.loko;

import org.junit.jupiter.api.Test;
import org.race.loko.repositories.business.CourseRepository;
import org.race.loko.repositories.business.EtapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LokoRaceApplicationTests {

    private final CourseRepository courseRepository;
    private final EtapeRepository etapeRepository;

    @Autowired
    public LokoRaceApplicationTests(CourseRepository courseRepository, EtapeRepository etapeRepository) {
        this.courseRepository = courseRepository;
        this.etapeRepository = etapeRepository;
    }

    @Test
    void listeEtapes() {
        var course = courseRepository.findLatestCourse();
        var listeEtapes = etapeRepository.findByCourseId(course.getId());


        System.out.println("\n\n"+listeEtapes+"\n\n");
    }

}
