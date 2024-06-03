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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            date = date.trim();
            // If parsing successful, format the date to yyyy-MM-dd
            this.dateDepart = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw IllegalArgumentException
            throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy format.");
        }
    }

    public void setHeureDepart(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            date = date.trim();
            // If parsing successful, format the date to HH:mm:ss
            this.heureDepart = LocalTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw IllegalArgumentException
            throw new IllegalArgumentException("Invalid date format. Please use HH:mm:ss format.");
        }
    }

}
