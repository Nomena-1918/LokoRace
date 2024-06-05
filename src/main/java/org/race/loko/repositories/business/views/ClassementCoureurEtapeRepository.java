package org.race.loko.repositories.business.views;

import org.race.loko.models.business.views.ClassementCoureurEtape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassementCoureurEtapeRepository extends JpaRepository<ClassementCoureurEtape, Long> {
    @Query(value = "select c from ClassementCoureurEtape c where c.coureurEtape.etape.course.id = ?1")
    List<ClassementCoureurEtape> findByCourseId(Long courseId);

    @Query(value = "select c from ClassementCoureurEtape c where c.coureurEtape.etape.course.id = ?1 AND c.coureurEtape.etape.id =?2 ORDER BY c.rangCoureur")
    List<ClassementCoureurEtape> findByCourseIdAndEtapeId(Long courseId, Long etapeId);

}
