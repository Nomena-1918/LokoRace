package org.race.loko.utils.csv.repo;

import org.race.loko.utils.csv.dto.EtapeCSV;
import org.race.loko.utils.csv.dto.ResultatCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ImportResultatRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImportResultatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTemporaryTable() {
        String createTableQuery = """
                CREATE TEMPORARY TABLE import_resultat (
                      etape_rang int,
                      numero_dossard int,
                      nom varchar(255),
                      genre char(1),
                      date_naissance date,
                      equipe varchar(255),
                      arrivee timestamp
                )
                """;
        jdbcTemplate.update(createTableQuery);
    }

    public void insertIntoTemporaryTable(ResultatCSV resultatCSV) {
        String insertQuery = """
                INSERT INTO import_resultat (etape_rang,numero_dossard,nom,genre,date_naissance,equipe,arrivee)
                VALUES (?,?,?,?,?,?,?)
                """;
        jdbcTemplate.update(insertQuery, resultatCSV.getEtapeRang(), resultatCSV.getNumeroDossard(), resultatCSV.getNom(), resultatCSV.getGenre(), resultatCSV.getDateNaissance(), resultatCSV.getEquipe(), resultatCSV.getArrivee());
    }

    @Transactional
    public void importDataIntoTables() {
        // INSERT GENRE
        String insertGenresQuery = """
                INSERT INTO genres (nom)
                SELECT DISTINCT i.genre
                FROM import_resultat i
                LEFT JOIN genres g ON i.genre = g.nom
                WHERE g.nom IS NULL
                """;
        jdbcTemplate.update(insertGenresQuery);

        // INSERT EQUIPE
        String insertEquipesQuery = """
                INSERT INTO equipes (nom, email, mot_de_passe)
                SELECT DISTINCT i.equipe, i.equipe, i.equipe
                FROM import_resultat i
                LEFT JOIN equipes e ON i.equipe = e.nom
                WHERE e.nom IS NULL
                """;
        jdbcTemplate.update(insertEquipesQuery);

        // INSERT COUREUR
        String insertCoureursQuery = """
                INSERT INTO coureurs (nom, prenom, numero_dossard, date_naissance, id_genre, id_equipe)
                SELECT DISTINCT i.nom, '', i.numero_dossard, i.date_naissance, g.id, e.id
                FROM import_resultat i
                LEFT JOIN coureurs c ON i.numero_dossard = c.numero_dossard
                JOIN genres g ON i.genre = g.nom
                JOIN equipes e ON i.equipe = e.nom
                WHERE c.numero_dossard IS NULL
                """;
        jdbcTemplate.update(insertCoureursQuery);

        // INSERT COUREUR_ETAPES
        String insertCoureurEtapesQuery = """
                INSERT INTO coureur_etapes (id_coureur, id_etape, dateheure_depart, dateheure_arrivee)
                SELECT c.id, e.id, e.dateheure_debut, i.arrivee
                FROM import_resultat i
                JOIN coureurs c ON i.numero_dossard = c.numero_dossard
                JOIN etapes e ON i.etape_rang = e.rang_etape
                LEFT JOIN coureur_etapes ce ON c.id = ce.id_coureur AND e.id = ce.id_etape
                WHERE ce.id_coureur IS NULL AND ce.id_etape IS NULL
                """;
        jdbcTemplate.update(insertCoureurEtapesQuery);
    }

    public void dropTemporaryTable() {
        String dropTableQuery = "DROP TABLE import_resultat";
        jdbcTemplate.execute(dropTableQuery);
    }
}
