package org.race.loko.repositories.business.views;

import org.race.loko.models.business.views.ClassementCoureurEtapeCategorie;
import org.race.loko.models.business.views.DetailClassementEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetailClassementEquipeRepository extends JpaRepository<DetailClassementEquipe, Long> {
    List<DetailClassementEquipe> findAllByEquipeNomOrderByEtapeRangEtape(String equipe);
}
