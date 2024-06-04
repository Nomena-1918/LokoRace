package org.race.loko.models.business;

import groovy.transform.Immutable;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.race.loko.models.profil.Equipe;

import java.time.Duration;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "coureur_etapes")
public class CoureurEtape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_coureur", nullable = false)
    private Coureur coureur;

    @ManyToOne
    @JoinColumn(name = "id_etape", nullable = false)
    private Etape etape;

    @Column(name = "dateheure_depart")
    private LocalDateTime dateHeureDepart;

    @Column(name = "dateheure_arrivee")
    private LocalDateTime dateHeureArrivee;

}
