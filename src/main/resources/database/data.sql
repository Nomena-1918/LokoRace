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
VALUES ('Équipe A', 'equipea@ultimateteamrace.com', 'equipepasswordA'),
       ('Équipe B', 'equipeb@ultimateteamrace.com', 'equipepasswordB');

INSERT INTO coureurs (nom, prenom, numero_dossard, date_naissance, id_genre, id_equipe)
VALUES ('Razafindrakoto', 'Haja', 101, '1998-07-15', 1, 1),
       ('Rasolofoniaina', 'Tahiry', 102, '2001-02-22', 1, 1),
       ('Rakotoarisoa', 'Feno', 201, '1996-05-10', 2, 2),
       ('Rajaonarivelo', 'Fanja', 202, '1990-12-19', 2, 2);

INSERT INTO coureur_categories (id_coureur, id_categorie)
VALUES (1, 4), -- Haja Razafindrakoto en Senior
       (2, 3), -- Tahiry Rasolofoniaina en Junior
       (3, 4), -- Feno Rakotoarisoa en Senior
       (4, 4); -- Fanja Rajaonarivelo en Senior

INSERT INTO coureur_categories (id_coureur, id_categorie)
VALUES (1, 1), -- Haja Razafindrakoto en Homme
       (2, 1), -- Tahiry Rasolofoniaina en Homme
       (3, 2), -- Feno Rakotoarisoa en Femme
       (4, 2); -- Fanja Rajaonarivelo en Femme


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
