package org.race.loko.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class CoureurEtapeForm {
    Long idCoureur;
    LocalDateTime dateHeureDepart;
    LocalTime temps;
    Duration dureePenalite;

    public CoureurEtapeForm() {
    }
}
