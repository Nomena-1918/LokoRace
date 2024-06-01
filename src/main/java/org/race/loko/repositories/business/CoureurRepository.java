package org.race.loko.repositories.business;


import org.race.loko.models.business.Coureur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoureurRepository extends JpaRepository<Coureur, Long> {
    List<Coureur> findByEquipeId(Long equipeId);
}
