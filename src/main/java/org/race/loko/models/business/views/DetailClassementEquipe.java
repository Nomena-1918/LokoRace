package org.race.loko.models.business.views;

import groovy.transform.Immutable;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.race.loko.models.business.CoureurEtape;
import org.race.loko.models.business.Course;
import org.race.loko.models.business.Etape;
import org.race.loko.models.profil.Equipe;
import org.race.loko.utils.CustomDateTimeUtils;

import java.time.Duration;

@Getter
@Setter
@Entity
@Immutable // car c'est une vue, les données ne sont pas modifiables
@Table(name = "v_detail_classement_equipe")
public class DetailClassementEquipe {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_equipe", nullable = false)
    private Equipe equipe;

    @ManyToOne
    @JoinColumn(name = "id_etape", nullable = false)
    private Etape etape;

    @Column(name = "points")
    private Integer points;

}