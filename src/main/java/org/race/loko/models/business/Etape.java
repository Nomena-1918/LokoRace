package org.race.loko.models.business;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.race.loko.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

@Setter
@Getter
@Entity
@Table(name = "etapes")
public class Etape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "distance_km", nullable = false)
    private Double distanceKm;

    @Column(name = "dateheure_debut", nullable = false)
    private LocalDateTime dateHeureDebut;

    @Column(name = "rang_etape", nullable = false, unique = true)
    private Integer rangEtape;

    @Column(name = "nombre_coureur_equipe", nullable = false)
    private Integer nombreCoureurEquipe;

    @OneToMany(mappedBy = "etape")
    private List<CoureurEtape> coureursEtape;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    private Course course;

    @Override
    public String toString() {
        return new StringJoiner(", ", Etape.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("nom='" + nom + "'")
                .add("distanceKm=" + distanceKm)
                .add("dateHeureDebut=" + dateHeureDebut)
                .add("rangEtape=" + rangEtape)
                .add("nombreCoureurEquipe=" + nombreCoureurEquipe)
                .add("course=" + course)
                .toString();
    }

    public String getDateHeureDebutStr() {
        return DateTimeUtils.formatFR(dateHeureDebut);
    }

    public Etape setId(Long id) {
        this.id = id;
        return this;
    }
}
