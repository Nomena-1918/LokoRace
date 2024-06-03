package repo;


import com.bTP.service.csv.dto.MaisonTravauxCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ImportMaisonTravauxRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImportMaisonTravauxRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTemporaryTable() {
        String createTableQuery = """
                CREATE TEMPORARY TABLE import_maison_travaux (
                    type_maison varchar(255),
                    description varchar(255),
                    surface numeric,
                    code_travaux varchar(255),
                    type_travaux varchar(255),
                    unite varchar(255),
                    prix_unitaire numeric,
                    quantite numeric,
                    duree_travaux numeric
                )
                """;
        jdbcTemplate.execute(createTableQuery);
    }

    public void insertIntoTemporaryTable(MaisonTravauxCSV maisonTravauxCSV) {
        String insertQuery = """
                INSERT INTO import_maison_travaux (type_maison,description,surface,code_travaux,type_travaux,unite,
                prix_unitaire,quantite,duree_travaux)
                VALUES (?,?,?,?,?,?,?,?,?)
                """;
        jdbcTemplate.update(insertQuery, maisonTravauxCSV.getTypeMaison(), maisonTravauxCSV.getDescription(),
                maisonTravauxCSV.getSurface(), maisonTravauxCSV.getCodeTravaux(), maisonTravauxCSV.getTypeTravaux(), maisonTravauxCSV.getUnite(),
                maisonTravauxCSV.getPrixUnitaire(), maisonTravauxCSV.getQuantite(), maisonTravauxCSV.getDureeTravaux());
    }

    public void importDataIntoTables() {
        // Insert into type_maison
        String insertTypeMaisonQuery = """
                INSERT INTO type_maison (nom, dureechantier, description)
                SELECT DISTINCT type_maison, duree_travaux, description FROM import_maison_travaux
                """;
        jdbcTemplate.execute(insertTypeMaisonQuery);

        // Insret into unite
        String insertUniteQuery = """
                INSERT INTO unite_mesure (nom)
                SELECT DISTINCT unite FROM import_maison_travaux
                """;
        jdbcTemplate.execute(insertUniteQuery);

        // Insert into type_travaux
        String insertTypeTravauxQuery = """
                INSERT INTO travaux (code, nom, idunite)
                SELECT DISTINCT code_travaux, type_travaux, u.id  FROM import_maison_travaux i
                join unite_mesure u on u.nom = i.unite
                """;
        jdbcTemplate.execute(insertTypeTravauxQuery);

        // Insert into travaux maison
        String insertTravauxMaisonQuery = """
                INSERT INTO travaux_maison (idtypemaison, idtravaux,
                quantite, prixunitaire)
                SELECT tm.id, t.id, imt.quantite, imt.prix_unitaire
                FROM import_maison_travaux imt
                join type_maison tm on tm.nom = imt.type_maison
                join travaux t on t.nom = imt.type_travaux
                """;
        jdbcTemplate.execute(insertTravauxMaisonQuery);
    }

    public void dropTemporaryTable() {
        String dropTableQuery = "DROP TABLE import_maison_travaux";
        jdbcTemplate.execute(dropTableQuery);
    }
}