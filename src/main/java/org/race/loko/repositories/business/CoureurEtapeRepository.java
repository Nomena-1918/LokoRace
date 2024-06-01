package org.race.loko.repositories.business;

import org.race.loko.models.business.CoureurEtape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoureurEtapeRepository extends JpaRepository<CoureurEtape, Long> {
    List<CoureurEtape> findByEtapeId(Long etapeId);
}

