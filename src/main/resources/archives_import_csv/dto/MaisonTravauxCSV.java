package dto;


import org.race.loko.utils.csv.CsvBindName;

/*
 * MaisonTravauxCSV class
 ** PRESENCE OBLIGATOIRE DE SETTER AVEC STRING ARG **
 */
public class MaisonTravauxCSV {
    @CsvBindName("type_maison")
    String typeMaison;
    @CsvBindName("description")
    String description;
    @CsvBindName("surface")
    Double surface;
    @CsvBindName("code_travaux")
    String codeTravaux;
    @CsvBindName("type_travaux")
    String typeTravaux;
    @CsvBindName("unité")
    String unite;
    @CsvBindName("prix_unitaire")
    Double prixUnitaire;
    @CsvBindName("quantite")
    Double quantite;
    @CsvBindName("duree_travaux")
    Double dureeTravaux;

    public void setDureeTravaux(String dureeTravaux) {
        String str = dureeTravaux.trim().replace(",", ".");
        this.dureeTravaux = Double.valueOf(str);
    }
    public void setPrixUnitaire(String prixUnitaire) {
        String str = prixUnitaire.trim().replace(",", ".");
        this.prixUnitaire = Double.valueOf(str);
    }
    public void setQuantite(String quantite) {
        String str = quantite.replace(",", ".");
        this.quantite = Double.valueOf(str);
    }
    public void setSurface(String surface) {
        String str = surface.trim().replace(",", ".");
        this.surface = Double.valueOf(str);
    }
    public void setTypeMaison(String typeMaison) {
        this.typeMaison = typeMaison.trim();
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public void setCodeTravaux(String codeTravaux) {
        this.codeTravaux = codeTravaux.trim();
    }

    public void setTypeTravaux(String typeTravaux) {
        this.typeTravaux = typeTravaux.trim();
    }

    public void setUnite(String unite) {
        this.unite = unite.trim();
    }

    public String getTypeMaison() {
        return typeMaison;
    }

    public String getDescription() {
        return description;
    }

    public Double getSurface() {
        return surface;
    }

    public String getCodeTravaux() {
        return codeTravaux;
    }

    public String getTypeTravaux() {
        return typeTravaux;
    }

    public String getUnite() {
        return unite;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public Double getQuantite() {
        return quantite;
    }

    public Double getDureeTravaux() {
        return dureeTravaux;
    }


    /*
    public void setDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            date = date.trim();
            // If parsing successful, format the date to yyyy-MM-dd
            this.date = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw IllegalArgumentException
            throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy format.");
        }
    }
    public void setHeure(String heure) throws IllegalArgumentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            heure = heure.trim().toUpperCase();
            // Try parsing the time string
            // If parsing successful, format the time to HH:mm
            this.heure = LocalTime.parse(heure, formatter);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw IllegalArgumentException
            throw new IllegalArgumentException("Invalid time format. Please use HH:mm format.");
        }
    }
*/

}
