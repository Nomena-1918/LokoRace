package org.race.loko.models.business.views;

import groovy.transform.Immutable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.race.loko.models.business.Categorie;
import org.race.loko.models.business.Course;
import org.race.loko.models.profil.Equipe;

import java.util.StringJoiner;

@Getter
@Setter
@Entity
@Immutable // car c'est une vue, les données ne sont pas modifiables
@Table(name = "v_classement_equipe_categorie")
public class ClassementEquipeCategorie {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "id_categorie", nullable = false)
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "id_equipe", nullable = false)
    private Equipe equipe;

    @Column(name = "rang_equipe")
    private Integer rangEquipe;

    @Column(name = "points")
    private Integer points;

    @Override
    public String toString() {
        return new StringJoiner(", ", ClassementEquipeCategorie.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("course=" + course)
                .add("equipe=" + equipe)
                .add("rangEquipe=" + rangEquipe)
                .add("points=" + points)
                .toString();
    }

    public String getCouleur() {
        String c = categorie.getNom();
        c = switch (c) {
            case "Homme" -> "bg-dark text-light";
            case "Femme" -> "bg-danger text-light";
            case "Junior" -> "bg-success text-dark";
            case "Senior" -> "bg-secondary text-primary";
            default -> "";
        };
        return c;
    }
}
