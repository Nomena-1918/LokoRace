package org.race.loko.utils.csv.repo;


import org.race.loko.utils.csv.dto.EtapeCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ImportEtapeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImportEtapeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void createTemporaryTable() {
        String insertCourseQuery = """
                INSERT INTO courses (nom, date_debut, date_fin)
                VALUES ('ULTIMATE TEAM RACE', '2024-06-01', '2024-06-02')
                """;
        jdbcTemplate.update(insertCourseQuery);

        String createTableQuery = """
                CREATE TEMPORARY TABLE import_etape (
                      etape VARCHAR(255),
                      longueur numeric,
                      nb_coureur int,
                      rang int,
                      date_depart date,
                      heure_depart time
                )
                """;
        jdbcTemplate.update(createTableQuery);
    }

    public void insertIntoTemporaryTable(EtapeCSV etapeCSV) {
        String insertQuery = """
                INSERT INTO import_etape (etape,longueur,nb_coureur,rang,date_depart,heure_depart)
                VALUES (?,?,?,?,?,?)
                """;
        jdbcTemplate.update(insertQuery, etapeCSV.getEtape(), etapeCSV.getLongueur(), etapeCSV.getNbCoureur(), etapeCSV.getRang(), etapeCSV.getDateDepart(), etapeCSV.getHeureDepart());
    }

    public void importDataIntoTables() {

        String insertEtapeQuery = """
                INSERT INTO etapes (nom, distance_km, dateheure_debut, rang_etape, nombre_coureur_equipe, id_course)
                SELECT etape,longueur, CONCAT(date_depart, ' ', heure_depart)::timestamp, rang, nb_coureur, 1  FROM import_etape
                """;
        jdbcTemplate.execute(insertEtapeQuery);

    }

    public void dropTemporaryTable() {
        String dropTableQuery = "DROP TABLE import_etape";
        jdbcTemplate.execute(dropTableQuery);
    }
}