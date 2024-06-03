package org.race.loko.repositories.business;

import org.race.loko.models.business.CoureurCategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CoureurCategorieRepository extends JpaRepository<CoureurCategorie, Long> {

    @Transactional
    @Query(value = "SELECT assign_categories_to_runners()", nativeQuery = true)
    void assignCategoriesToRunners();
}
