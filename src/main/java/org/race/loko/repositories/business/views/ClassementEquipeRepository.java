package org.race.loko.repositories.business.views;

import org.race.loko.models.business.views.ClassementCoureurEtape;
import org.race.loko.models.business.views.ClassementEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassementEquipeRepository extends JpaRepository<ClassementEquipe, Long> {
    @Query(value = "select c from ClassementEquipe c where c.course.id = ?1")
    List<ClassementEquipe> findByCourseId(Long courseId);
}
