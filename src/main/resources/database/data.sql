INSERT INTO courses (nom, date_debut, date_fin)
VALUES ('ULTIMATE TEAM RACE', '2024-06-01', '2024-06-02');

INSERT INTO etapes (nom, distance_km, dateheure_debut, rang_etape, nombre_coureur_equipe, id_course)
VALUES ('Étape 1', 18.0, '2024-06-01 10:00:00', 1, 1, 1),
       ('Étape 2', 19.0, '2024-06-02 10:00:00', 2, 2, 1);
/*
INSERT INTO admin (email, mot_de_passe)
VALUES ('admin@ultimateteamrace.com', 'adminpassword');
*/
INSERT INTO genres (nom)
VALUES ('Homme'),
       ('Femme');

INSERT INTO categories (nom)
VALUES ('Homme'),
       ('Femme'),
       ('Junior'),
       ('Senior');

INSERT INTO equipes (nom, email, mot_de_passe)
VALUES ('Équipe A', 'equipea@race.com', 'equipea'),
       ('Équipe B', 'equipeb@race.com', 'equipeb');



INSERT INTO coureurs (nom, prenom, numero_dossard, date_naissance, id_genre, id_equipe)
VALUES ('Razafindrakoto', 'Haja', 101, '1998-07-15', 1, 1),
       ('Rasolofoniaina', 'Tahiry', 102, '2001-02-22', 1, 1),
       ('Rakotoarisoa', 'Feno', 201, '1996-05-10', 2, 2),
       ('Rajaonarivelo', 'Fanja', 202, '1990-12-19', 2, 2);


-- Insertion des données de paramétrage
INSERT INTO parametrage_categorie (id_categorie, id_genre, tranche_age)
VALUES
    (3, 1, '[0,18]'),   -- Junior Homme
    (3, 2, '[0,18]'),   -- Junior Femme
    (4, 1, '[18,200]'), -- Senior Homme
    (4, 2, '[18,200]'); -- Senior Femme


-- Attribuer les catégories aux coureurs en fonction de l'âge calculé
INSERT INTO coureur_categories (id_coureur, id_categorie)
SELECT c.id, p.id_categorie
FROM coureurs c
         JOIN parametrage_categorie p
              ON c.id_genre = p.id_genre
                  AND (EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.date_naissance))::int <@ p.tranche_age);



/*
INSERT INTO coureur_categories (id_coureur, id_categorie)
VALUES (1, 4), -- Haja Razafindrakoto en Senior
       (2, 3), -- Tahiry Rasolofoniaina en Junior
       (3, 4), -- Feno Rakotoarisoa en Senior
       (4, 4); -- Fanja Rajaonarivelo en Senior
*/

--select EXTRACT(YEAR FROM AGE(CURRENT_DATE, '2004-06-01 10:00:00'));



INSERT INTO coureur_etapes (id_coureur, id_etape, dateheure_depart, dateheure_arrivee, duree_penalite)
VALUES (1, 1, '2024-06-01 10:00:00', '2024-06-01 14:30:00', '00:00:00'),
       (3, 1, '2024-06-01 10:00:00', '2024-06-01 14:30:00', '00:00:00'),

--       (1, 2, '2024-06-02 10:00:00', '2024-06-02 14:30:00', '00:00:00'),
       (2, 2, '2024-06-02 10:00:00', '2024-06-02 14:35:00', '00:02:00'),
       (3, 2, '2024-06-02 10:00:00', '2024-06-02 15:10:00', '00:00:00'),
       (4, 2, '2024-06-02 10:00:00', '2024-06-02 14:55:00', '00:03:00');

INSERT INTO point_classements (rang, points)
VALUES (1, 10),
       (2, 6),
       (3, 4),
       (4, 2),
       (5, 1);
