package org.race.loko.repositories.business;

import org.race.loko.models.business.PenaliteEtapeEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PenaliteEtapeEquipeRepository extends JpaRepository<PenaliteEtapeEquipe, Long> {
    @Query("select p from PenaliteEtapeEquipe p where p.etape.course.id = ?1")
    List<PenaliteEtapeEquipe> findAllByCourseId(Long courseId);
}
