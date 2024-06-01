package org.race.loko.repository.business;

import org.race.loko.model.business.Etape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtapeRepository extends JpaRepository<Etape, Long> {
    List<Etape> findByCourseId(Long id);
}
