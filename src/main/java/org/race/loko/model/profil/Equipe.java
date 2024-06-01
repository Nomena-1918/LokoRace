package org.race.loko.model.profil;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

}
