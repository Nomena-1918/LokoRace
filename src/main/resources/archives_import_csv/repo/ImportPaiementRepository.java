package repo;


import com.bTP.service.csv.dto.PaiementCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ImportPaiementRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImportPaiementRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTemporaryTable() {
        String createTableQuery = """
                CREATE TEMPORARY TABLE import_paiement (
                      ref_devis VARCHAR(255),
                      ref_paiement VARCHAR(255),
                      date_paiement timestamp,
                      montant NUMERIC
                )
                """;
        jdbcTemplate.execute(createTableQuery);
    }

    public void insertIntoTemporaryTable(PaiementCSV paiementCSV) {
        String insertQuery = """
                INSERT INTO import_paiement (ref_devis,ref_paiement,
                date_paiement,montant)
                VALUES (?,?,?,?)
                """;
        jdbcTemplate.update(insertQuery, paiementCSV.getRefDevis(),
                paiementCSV.getRefPaiement(), paiementCSV.getDatePaiement(), paiementCSV.getMontant());
    }

    public void importDataIntoTables() {
        // Insert into finition
        String insertFinitionQuery = """
                INSERT INTO paiement (iddevis, montantpaye, dateheurepaiement, ref_devis, ref_paiement)
                SELECT DISTINCT d.id, ip.montant, ip.date_paiement, ip.ref_devis, ip.ref_paiement
                FROM import_paiement ip
                join devis d on d.ref_devis = ip.ref_devis
                """;
        jdbcTemplate.execute(insertFinitionQuery);
    }

    public void dropTemporaryTable() {
        String dropTableQuery = "DROP TABLE import_paiement";
        jdbcTemplate.execute(dropTableQuery);
    }
}