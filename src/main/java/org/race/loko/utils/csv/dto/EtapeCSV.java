package org.race.loko.utils.csv.dto;


import lombok.Getter;
import lombok.Setter;
import org.race.loko.utils.CustomDateTimeUtils;
import org.race.loko.utils.csv.CsvBindName;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class EtapeCSV {
    @CsvBindName("etape")
    private String etape;

    @CsvBindName("longueur")
    private Double longueur;

    @CsvBindName("nb coureur")
    private Integer nbCoureur;

    @CsvBindName("rang")
    private Integer rang;

    @CsvBindName("date départ")
    private LocalDate dateDepart;

    @CsvBindName("date départ")
    private LocalTime heureDepart;


    public void setEtape(String etape) {
        this.etape = etape.trim();
    }

    public void setLongueur(String longueur) {
        String str = longueur
                                .trim()
                                .replace("%", "")
                                .replace(",", ".");
        this.longueur = Double.valueOf(str);
    }

    public void setNbCoureur(String nbCoureur) {
        String str = nbCoureur
                .trim()
                .replace("%", "")
                .replace(",", ".");
        this.nbCoureur = Integer.valueOf(str);
    }

    public void setRang(String rang) {
        String str = rang
                .trim()
                .replace("%", "")
                .replace(",", ".");
        this.rang = Integer.valueOf(str);
    }

    public void setDateDepart(String date) {
        try {
            dateDepart = CustomDateTimeUtils.stringToDate(date);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw IllegalArgumentException
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void setHeureDepart(String time) {
        try {
            heureDepart = CustomDateTimeUtils.stringToTime(time);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw IllegalArgumentException
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
