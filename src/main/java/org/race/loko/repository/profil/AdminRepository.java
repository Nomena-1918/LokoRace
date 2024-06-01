package org.race.loko.repository.profil;

import org.race.loko.model.profil.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("select a from Admin a where a.email = :mail and a.mdp = :mdp")
    Optional<Admin> findAdminByEmailAndAndMdp(String mail, String mdp);
}
