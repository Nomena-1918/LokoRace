package org.race.loko.repositories.business;

import org.race.loko.models.business.CoureurEtape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoureurEtapeRepository extends JpaRepository<CoureurEtape, Long> {
    @Query("SELECT ce FROM CoureurEtape ce WHERE ce.etape.id = ?1 AND ce.coureur.id = ?2")
    CoureurEtape findByEtapeIdAndCoureurId(Long etape_id, Long coureur_id);
}

