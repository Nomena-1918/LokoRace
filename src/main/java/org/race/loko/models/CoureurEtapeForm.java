package org.race.loko.models;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class CoureurEtapeForm {
    Long idCoureur;
    LocalDateTime dateHeureDepart;
    LocalDateTime dateHeureArrivee;
    Duration dureePenalite;

    public CoureurEtapeForm() {
    }
}
