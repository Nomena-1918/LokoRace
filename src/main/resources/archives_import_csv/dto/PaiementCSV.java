package dto;

import com.bTP.service.csv.CsvBindName;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PaiementCSV {
    @CsvBindName("ref_devis")
    private String refDevis;
    @CsvBindName ("ref_paiement")
    private String refPaiement;
    @CsvBindName ("date_paiement")
    private LocalDate datePaiement;
    @CsvBindName ("montant")
    private Double montant;


    public void setMontant(String montant) {
        String str = montant.trim().replace(",", ".");
        this.montant = Double.valueOf(str);
    }

    public void setDatePaiement(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            date = date.trim();
            // If parsing successful, format the date to yyyy-MM-dd
            this.datePaiement = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw IllegalArgumentException
            throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy format.");
        }
    }


    public void setRefDevis(String refDevis) {
        this.refDevis = refDevis.trim();
    }

    public void setRefPaiement(String refPaiement) {
        this.refPaiement = refPaiement.trim();
    }

    public String getRefDevis() {
        return refDevis;
    }

    public String getRefPaiement() {
        return refPaiement;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public Double getMontant() {
        return montant;
    }
}
