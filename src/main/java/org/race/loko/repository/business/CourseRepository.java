package org.race.loko.repository.business;

import org.race.loko.model.business.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select c from Course c where c.dateDebut = (select max(c2.dateDebut) from Course c2)")
    Course findLatestCourse();
}

