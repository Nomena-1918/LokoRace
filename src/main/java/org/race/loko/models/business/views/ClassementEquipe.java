package org.race.loko.models.business.views;

import groovy.transform.Immutable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.race.loko.models.business.CoureurEtape;
import org.race.loko.models.business.Course;
import org.race.loko.models.profil.Equipe;

import java.util.StringJoiner;

@Getter
@Setter
@Entity
@Immutable // car c'est une vue, les données ne sont pas modifiables
@Table(name = "v_classement_equipe")
public class ClassementEquipe {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "id_equipe", nullable = false)
    private Equipe equipe;

    @Column(name = "rang_equipe")
    private Integer rangEquipe;

    @Column(name = "points")
    private Integer points;

    @Override
    public String toString() {
        return new StringJoiner(", ", ClassementEquipe.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("course=" + course)
                .add("equipe=" + equipe)
                .add("rangEquipe=" + rangEquipe)
                .add("points=" + points)
                .toString();
    }
}
