package org.race.loko;

import org.junit.jupiter.api.Test;
import org.race.loko.repositories.business.ClassementCoureurEtapeRepository;
import org.race.loko.repositories.business.CourseRepository;
import org.race.loko.repositories.business.EtapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LokoRaceApplicationTests {

    private final CourseRepository courseRepository;
    private final EtapeRepository etapeRepository;
    private final ClassementCoureurEtapeRepository classementCoureurEtapeRepository;

    @Autowired
    public LokoRaceApplicationTests(CourseRepository courseRepository, EtapeRepository etapeRepository, ClassementCoureurEtapeRepository classementCoureurEtapeRepository) {
        this.courseRepository = courseRepository;
        this.etapeRepository = etapeRepository;
        this.classementCoureurEtapeRepository = classementCoureurEtapeRepository;
    }

    @Test
    void listeEtapes() {
        var course = courseRepository.findLatestCourse();
        var listeEtapes = etapeRepository.findByCourseId(course.getId());


        System.out.println("\n\n"+listeEtapes+"\n\n");
    }

    @Test
    void classements() {
        var course = courseRepository.findLatestCourse();
        var classements = classementCoureurEtapeRepository.findByCourseId(course.getId());

        System.out.println("\n\n"+classements+"\n\n");
    }

}
