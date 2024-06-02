package org.race.loko;

import org.junit.jupiter.api.Test;
import org.race.loko.repositories.business.views.ClassementCoureurEtapeRepository;
import org.race.loko.repositories.business.CourseRepository;
import org.race.loko.repositories.business.EtapeRepository;
import org.race.loko.repositories.business.views.ClassementEquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LokoRaceApplicationTests {

    private final CourseRepository courseRepository;
    private final EtapeRepository etapeRepository;
    private final ClassementCoureurEtapeRepository classementCoureurEtapeRepository;

    private final ClassementEquipeRepository classementEquipeRepository;
    @Autowired
    public LokoRaceApplicationTests(CourseRepository courseRepository, EtapeRepository etapeRepository, ClassementCoureurEtapeRepository classementCoureurEtapeRepository, ClassementEquipeRepository classementEquipeRepository) {
        this.courseRepository = courseRepository;
        this.etapeRepository = etapeRepository;
        this.classementCoureurEtapeRepository = classementCoureurEtapeRepository;
        this.classementEquipeRepository = classementEquipeRepository;
    }

    @Test
    void listeEtapes() {
        var course = courseRepository.findLatestCourse();
        var listeEtapes = etapeRepository.findByCourseId(course.getId());

        System.out.println("\n\n"+listeEtapes+"\n\n");
    }

    @Test
    void classementsCoureurs() {
        var course = courseRepository.findLatestCourse();
        var classements = classementCoureurEtapeRepository.findByCourseId(course.getId());

        System.out.println("\n\n"+classements+"\n\n");
    }

    @Test
    void classementsEquipes() {
        var course = courseRepository.findLatestCourse();
        var classements = classementEquipeRepository.findByCourseId(course.getId());

        System.out.println("\n\n"+classements+"\n\n");
    }

}
