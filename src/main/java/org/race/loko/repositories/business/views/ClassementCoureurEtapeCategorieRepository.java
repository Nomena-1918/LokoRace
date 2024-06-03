package org.race.loko.repositories.business.views;

import org.race.loko.models.business.views.ClassementCoureurEtape;
import org.race.loko.models.business.views.ClassementCoureurEtapeCategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassementCoureurEtapeCategorieRepository extends JpaRepository<ClassementCoureurEtapeCategorie, Long> {
    @Query(value = "SELECT * FROM v_rang_coureur_etape_categorie WHERE id_etape =?1 ORDER BY id_etape, dateheure_arrivee", nativeQuery = true)
    List<ClassementCoureurEtapeCategorie> findByEtapeId(Long etapeId);

    @Query(value = "select c from ClassementCoureurEtapeCategorie c where c.coureurEtape.etape.course.id = ?1")
    List<ClassementCoureurEtapeCategorie> findByCourseId(Long courseId);

    @Query(value = "select c from ClassementCoureurEtapeCategorie c where c.coureurEtape.etape.course.id = ?1 and c.categorie.id = ?2")
    List<ClassementCoureurEtapeCategorie> findByCourseIdAndCategorieId(Long courseId, Long categorieId);

}
