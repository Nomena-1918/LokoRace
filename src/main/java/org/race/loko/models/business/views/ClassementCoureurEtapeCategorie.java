package org.race.loko.models.business.views;

import groovy.transform.Immutable;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.race.loko.models.business.Categorie;
import org.race.loko.models.business.CoureurEtape;
import org.race.loko.models.business.Course;
import org.race.loko.utils.CustomDateTimeUtils;

import java.time.Duration;

@Getter
@Setter
@Entity
@Immutable // car c'est une vue, les données ne sont pas modifiables
@Table(name = "v_classement_etape_categorie")
public class ClassementCoureurEtapeCategorie {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "id_v_rang_coureur_etape_categorie", nullable = false)
    private CoureurEtape coureurEtape;

    @ManyToOne
    @JoinColumn(name = "id_categorie", nullable = false)
    private Categorie categorie;


    @Column(name = "rang_coureur")
    private Integer rangCoureur;

    @Column(name = "points")
    private Integer points;

    @Type(PostgreSQLIntervalType.class)
    @Column(name = "duree_penalite", columnDefinition = "interval")
    private Duration dureePenalite;

    public String getDureePenaliteStr() {
        return CustomDateTimeUtils.formatDuration(dureePenalite);
    }

}