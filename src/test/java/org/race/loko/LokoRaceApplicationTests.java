package org.race.loko;

import org.junit.jupiter.api.Test;
import org.race.loko.repositories.business.CoureurCategorieRepository;
import org.race.loko.repositories.business.CoureurRepository;
import org.race.loko.repositories.business.views.ClassementCoureurEtapeCategorieRepository;
import org.race.loko.repositories.business.views.ClassementCoureurEtapeRepository;
import org.race.loko.repositories.business.CourseRepository;
import org.race.loko.repositories.business.EtapeRepository;
import org.race.loko.repositories.business.views.ClassementEquipeCategorieRepository;
import org.race.loko.repositories.business.views.ClassementEquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LokoRaceApplicationTests {

    private final CourseRepository courseRepository;
    private final EtapeRepository etapeRepository;
    private final ClassementCoureurEtapeRepository classementCoureurEtapeRepository;
    private final ClassementEquipeRepository classementEquipeRepository;
    private final CoureurCategorieRepository coureurCategorieRepository;
    private final CoureurRepository coureurRepository;
    private final ClassementCoureurEtapeCategorieRepository classementCoureurEtapeCategorieRepository;
    private final ClassementEquipeCategorieRepository classementEquipeCategorieRepository;

    @Autowired
    public LokoRaceApplicationTests(CourseRepository courseRepository, EtapeRepository etapeRepository, ClassementCoureurEtapeRepository classementCoureurEtapeRepository, ClassementEquipeRepository classementEquipeRepository, CoureurCategorieRepository coureurCategorieRepository, CoureurRepository coureurRepository, CoureurRepository coureurRepository1, ClassementCoureurEtapeCategorieRepository classementCoureurEtapeCategorieRepository, ClassementEquipeCategorieRepository classementEquipeCategorieRepository) {
        this.courseRepository = courseRepository;
        this.etapeRepository = etapeRepository;
        this.classementCoureurEtapeRepository = classementCoureurEtapeRepository;
        this.classementEquipeRepository = classementEquipeRepository;
        this.coureurCategorieRepository = coureurCategorieRepository;
        this.coureurRepository = coureurRepository1;
        this.classementCoureurEtapeCategorieRepository = classementCoureurEtapeCategorieRepository;
        this.classementEquipeCategorieRepository = classementEquipeCategorieRepository;
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

    @Test
    void coureurCategorie() {
        var coureurCategorie = coureurCategorieRepository.findAll();

        System.out.println("\n\n"+coureurCategorie+"\n\n");
    }

    @Test
    void coureur() {
        var coureur = coureurRepository.findAll();

        System.out.println("\n\n"+coureur+"\n\n");
    }


    @Test
    void classementsCoureursCategorie() {
        var course = courseRepository.findLatestCourse();
        var classements = classementCoureurEtapeCategorieRepository.findByCourseId(course.getId());

        System.out.println("\n\n"+classements+"\n\n");
    }

    @Test
    void classementsEquipesCategorie() {
        var course = courseRepository.findLatestCourse();
        var classements = classementEquipeCategorieRepository.findByCourseId(course.getId());

        System.out.println("\n\n"+classements+"\n\n");
    }


}
