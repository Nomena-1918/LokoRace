package repo;


import com.bTP.service.csv.dto.DevisCSV;
import com.bTP.service.csv.dto.MaisonTravauxCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ImportDevisRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImportDevisRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void createTemporaryTable() {
        String createTableQuery = """
                CREATE TEMPORARY TABLE import_devis (
                      client VARCHAR(255),
                      ref_devis VARCHAR(255),
                      type_maison VARCHAR(255),
                      finition VARCHAR(255),
                      taux_finition NUMERIC,
                      date_devis timestamp,
                      date_debut timestamp,
                      lieu VARCHAR(255)
                )
                """;
        jdbcTemplate.execute(createTableQuery);
    }

    public void insertIntoTemporaryTable(DevisCSV devisCSV) {
        String insertQuery = """
                INSERT INTO import_devis (client,ref_devis,type_maison,finition,
                    taux_finition,date_devis,date_debut,lieu)
                VALUES (?,?,?,?,?,?,?,?)
                """;
        jdbcTemplate.update(insertQuery, devisCSV.getClient(), devisCSV.getRefDevis(), devisCSV.getTypeMaison(),
                devisCSV.getFinition(), devisCSV.getTauxFinition(), devisCSV.getDateDevis(), devisCSV.getDateDebut(),
                devisCSV.getLieu());
    }

    public void importDataIntoTables() {
        // Insert into finition
        String insertFinitionQuery = """
                INSERT INTO type_finition (nom, pourcentagemajoration)
                SELECT DISTINCT finition, taux_finition FROM import_devis
                """;
        jdbcTemplate.execute(insertFinitionQuery);

        // Insert into genesis_user
        String insertGenesisQuery = """
                INSERT INTO genesis_user (username, authority)
                SELECT DISTINCT client, 5 FROM import_devis
                """;
        jdbcTemplate.execute(insertGenesisQuery);

        // Insert into client
        String insertClientQuery = """
                INSERT INTO client (numtel)
                SELECT DISTINCT client FROM import_devis
                """;
        jdbcTemplate.execute(insertClientQuery);

        // Insert into devis
        String insertDevisQuery = """
                INSERT INTO devis (idclient, dateheurecreation,
                idtypemaison, idtypefinition, dateheuredebuttravaux,
                lieu, ref_devis, pourcentage_majoration)
                SELECT c.id, date_devis, tm.id, tf.id,
                date_debut, lieu, ref_devis, i.taux_finition
                FROM import_devis i
                JOIN client c ON i.client = c.numtel
                JOIN type_maison tm ON i.type_maison = tm.nom
                JOIN type_finition tf ON i.finition = tf.nom
               """;
        jdbcTemplate.execute(insertDevisQuery);

        // Insert into document_devis
        // devis.csv
        // client,ref_devis,type_maison,finition,
        // taux_finition,date_devis,date_debut,lieu
        String insertDocumentDevisQuery = """
                INSERT INTO document_devis (nommaison, nomfinition, numtel,
                id_devis, dureechantier, pourcentage_finition)
                SELECT DISTINCT imd.type_maison, imd.finition, imd.client,
                d.id, tm.dureechantier, imd.taux_finition
                    FROM import_devis imd
                join type_finition tf on tf.pourcentagemajoration = imd.taux_finition
                join devis d on d.ref_devis = imd.ref_devis
                join type_maison tm on tm.nom = imd.type_maison
                """;
        jdbcTemplate.execute(insertDocumentDevisQuery);


        // Insert into devis_travaux
        String insertDevisTravauxQuery = """
                INSERT INTO devis_travaux (iddevis, idtravaux, prixunitaire,
                quantite, dateheuredebuttravaux,
                pourcentage_finition)
                SELECT d.id, tm.idtravaux, tm.prixunitaire*(1+d.pourcentage_majoration/100), tm.quantite, d.dateheuredebuttravaux, tf.pourcentagemajoration
                FROM devis d
                JOIN travaux_maison tm on tm.idtypemaison = d.idtypemaison
                join travaux t on t.id = tm.idtravaux
                join type_finition tf on tf.id = d.idtypefinition
               """;
        jdbcTemplate.execute(insertDevisTravauxQuery);


    }

    public void dropTemporaryTable() {
        String dropTableQuery = "DROP TABLE import_devis";
        jdbcTemplate.execute(dropTableQuery);
    }
}