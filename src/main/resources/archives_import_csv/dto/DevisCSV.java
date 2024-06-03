package dto;


import com.bTP.service.csv.CsvBindName;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DevisCSV {
    @CsvBindName("client")
    private String client;
    @CsvBindName("ref_devis")
    private String refDevis;
    @CsvBindName("type_maison")
    private String typeMaison;
    @CsvBindName("finition")
    private String finition;
    @CsvBindName("taux_finition")
    private Double tauxFinition;
    @CsvBindName("date_devis")
    private LocalDate dateDevis;
    @CsvBindName("date_debut")
    private LocalDate dateDebut;
    @CsvBindName("lieu")
    private String lieu;


    public void setClient(String client) {
        this.client = client.trim();
    }

    public void setRefDevis(String refDevis) {
        this.refDevis = refDevis.trim();
    }

    public void setTypeMaison(String typeMaison) {
        this.typeMaison = typeMaison.trim();
    }

    public void setFinition(String finition) {
        this.finition = finition.trim();
    }

    public void setLieu(String lieu) {
        this.lieu = lieu.trim();
    }

    public void setTauxFinition(String tauxFinition) {
        String str = tauxFinition
                                .trim()
                                .replace("%", "")
                                .replace(",", ".");
        this.tauxFinition = Double.valueOf(str);
    }

    public void setDateDevis(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            date = date.trim();
            // If parsing successful, format the date to yyyy-MM-dd
            this.dateDevis = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw IllegalArgumentException
            throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy format.");
        }
    }

    public void setDateDebut(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            date = date.trim();
            // If parsing successful, format the date to yyyy-MM-dd
            this.dateDebut = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw IllegalArgumentException
            throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy format.");
        }
    }
    public String getClient() {
        return client;
    }

    public String getRefDevis() {
        return refDevis;
    }

    public String getTypeMaison() {
        return typeMaison;
    }

    public String getFinition() {
        return finition;
    }

    public Double getTauxFinition() {
        return tauxFinition;
    }

    public LocalDate getDateDevis() {
        return dateDevis;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public String getLieu() {
        return lieu;
    }
}
