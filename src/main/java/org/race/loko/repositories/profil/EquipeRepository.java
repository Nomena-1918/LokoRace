package org.race.loko.repositories.profil;

import org.race.loko.models.profil.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    @Query("select e from Equipe e where e.email = :email and e.mdp = :mdp")
    Optional<Equipe> findEquipeByEmailAndAndMdp(String email, String mdp);
}