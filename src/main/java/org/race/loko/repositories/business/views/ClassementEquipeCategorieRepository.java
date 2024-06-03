package org.race.loko.repositories.business.views;

import org.race.loko.models.business.views.ClassementEquipeCategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassementEquipeCategorieRepository extends JpaRepository<ClassementEquipeCategorie, Long> {
    @Query(value = "select c from ClassementEquipeCategorie c where c.course.id = ?1")
    List<ClassementEquipeCategorie> findByCourseId(Long courseId);

    @Query(value = "select c from ClassementEquipeCategorie c where c.course.id = ?1 and c.categorie.id = ?2")
    List<ClassementEquipeCategorie> findByCourseIdAndCategorieId(Long courseId, Long categorieId);

}
