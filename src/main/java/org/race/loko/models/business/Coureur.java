package org.race.loko.models.business;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.race.loko.models.profil.Equipe;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "coureurs")
public class Coureur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "numero_dossard", nullable = false)
    private Integer numeroDossard;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @ManyToOne
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "id_equipe", nullable = false)
    private Equipe equipe;

    @OneToMany(mappedBy = "coureur", fetch = FetchType.EAGER)
    private List<CoureurCategorie> coureurCategories;

    public Coureur setId(Long id) {
        this.id = id;
        return this;
    }

    Duration d = Duration.ofDays(1).plus(Duration.ofHours(2));
}
