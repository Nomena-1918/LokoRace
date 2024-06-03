package org.race.loko.utils.csv.dto;


import lombok.Getter;
import lombok.Setter;
import org.race.loko.utils.csv.CsvBindName;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class PointCSV {
    @CsvBindName("classement")
    private Integer classement;

    @CsvBindName("points")
    private Integer points;


    public void setClassement(String classement) {
        String str = classement
                .trim();
        this.classement = Integer.valueOf(str);
    }
    public void setPoints(String points) {
        String str = points
                .trim();
        this.points = Integer.valueOf(str);
    }
}
