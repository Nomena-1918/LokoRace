package org.race.loko.models.profil;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.StringJoiner;

@Setter
@Getter
@Entity
@Table(name = "equipes")
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, unique = true)
    public String nom;

    @Column(name = "email", nullable = false, unique = true)
    public String email;

    @Column(name = "mot_de_passe", nullable = false, unique = true)
    public String mdp;

    public Equipe setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Equipe.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("nom='" + nom + "'")
                .add("email='" + email + "'")
                .add("mdp='" + mdp + "'")
                .toString();
    }
}
