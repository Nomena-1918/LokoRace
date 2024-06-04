package org.race.loko.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class PenaliteEquipeForm {
    Long etape;
    Long equipe;
    LocalTime dureePenalite;

    public PenaliteEquipeForm() {
    }
}
