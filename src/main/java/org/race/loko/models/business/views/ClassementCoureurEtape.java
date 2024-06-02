package org.race.loko.models.business.views;

import groovy.transform.Immutable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.race.loko.models.business.CoureurEtape;
import org.race.loko.models.business.Course;

@Getter
@Setter
@Entity
@Immutable // car c'est une vue, les données ne sont pas modifiables
@Table(name = "v_classement_etape")
public class ClassementCoureurEtape {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "id_rang_coureur_etape", nullable = false)
    private CoureurEtape coureurEtape;

    @Column(name = "rang_coureur")
    private Integer rangCoureur;

    @Column(name = "points")
    private Integer points;
}