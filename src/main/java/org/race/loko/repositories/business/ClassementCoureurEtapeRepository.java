package org.race.loko.repositories.business;

import org.race.loko.models.business.ClassementCoureurEtape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassementCoureurEtapeRepository extends JpaRepository<ClassementCoureurEtape, Long> {
    @Query(value = "SELECT * FROM v_rang_coureur_etape WHERE id_etape =?1 ORDER BY id_etape, dateheure_arrivee", nativeQuery = true)
    List<ClassementCoureurEtape> findByEtapeId(Long etapeId);

    @Query(value = "select c from ClassementCoureurEtape c where c.coureurEtape.etape.course.id = ?1")
    List<ClassementCoureurEtape> findByCourseId(Long courseId);



}
