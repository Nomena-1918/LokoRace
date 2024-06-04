package org.race.loko.models.business;

import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.race.loko.models.profil.Equipe;

import java.time.Duration;

@Getter
@Setter
@Entity
@Immutable // car c'est une vue, les données ne sont pas modifiables
@Table(name = "penalite_etape_equipe")
public class PenaliteEtapeEquipe {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_etape", nullable = false)
    private Etape etape;

    @ManyToOne
    @JoinColumn(name = "id_equipe", nullable = false)
    private Equipe equipe;

    @Type(PostgreSQLIntervalType.class)
    @Column(name = "duree_penalite", nullable = false, columnDefinition = "interval default '00:00:00'")
    private Duration dureePenalite;

    public PenaliteEtapeEquipe() {
    }
}