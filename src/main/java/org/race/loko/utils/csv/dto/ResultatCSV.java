package org.race.loko.utils.csv.dto;


import lombok.Getter;
import lombok.Setter;
import org.race.loko.utils.csv.CsvBindName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class ResultatCSV {
    @CsvBindName("etape_rang")
    private Integer etapeRang;

    @CsvBindName("numero dossard")
    private Integer numeroDossard;

    @CsvBindName("nom")
    private String nom;

    @CsvBindName("genre")
    private String genre;

    @CsvBindName("date naissance")
    private LocalDate dateNaissance;

    @CsvBindName("equipe")
    private String equipe;

    @CsvBindName("arrivée")
    private LocalDateTime arrivee;


    public void setEtapeRang(String etapeRang) {
        String str = etapeRang
                .trim()
                .replace("%", "")
                .replace(",", ".");
        this.etapeRang = Integer.valueOf(str);
    }

    public void setNumeroDossard(String numeroDossard) {
        String str = numeroDossard
                .trim()
                .replace("%", "")
                .replace(",", ".");
        this.numeroDossard = Integer.valueOf(str);
    }

    public void setNom(String nom) {
        this.nom = nom.trim();
    }

    public void setGenre(String genre) {
        this.genre = genre.trim();
    }


    public void setDateNaissance(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            date = date.trim();
            // If parsing successful, format the date to yyyy-MM-dd
            this.dateNaissance = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw IllegalArgumentException
            throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy format.");
        }
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe.trim();
    }

    public void setArrivee(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        try {
            date = date.trim();
            // If parsing successful, format the date to yyyy-MM-dd HH:mm:ss
            this.arrivee = LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw IllegalArgumentException
            throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy format.");
        }
    }

}
