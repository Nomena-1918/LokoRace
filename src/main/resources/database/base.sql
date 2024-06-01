create table courses
(
    id         serial primary key,
    nom        varchar(255) not null,
    date_debut date         not null,
    date_fin   date         not null check ( date_fin > date_debut)
);

create table etapes
(
    id                    serial primary key,
    nom                   varchar(255) not null,
    distance_km           numeric      not null check ( distance_km > 0 ),
    dateheure_debut       timestamp    not null,
    rang_etape            int unique   not null check ( rang_etape >= 0 ),
    nombre_coureur_equipe int          not null check ( nombre_coureur_equipe > 0),
    id_course             integer      not null,
    foreign key (id_course) references courses (id)
);

create table admin
(
    id           serial primary key,
    email        varchar(255) not null,
    mot_de_passe varchar(255) not null
);

create table equipes
(
    id           serial primary key,
    nom          varchar(255) not null,
    email        varchar(255) not null,
    mot_de_passe varchar(255) not null
);

create table genres
(
    id  serial primary key,
    nom varchar(255) unique not null
);

create table coureurs
(
    id             serial primary key,
    nom            varchar(255) not null,
    prenom         varchar(255) not null,
    numero_dossard integer      not null,
    date_naissance date         not null,
    id_genre       integer      not null,
    id_equipe      integer      not null,
    foreign key (id_genre) references genres (id),
    foreign key (id_equipe) references equipes (id),
    unique (nom, prenom, date_naissance, id_genre, id_equipe)
);

create table categories
(
    id  serial primary key,
    nom varchar(255) unique not null
);

create table coureur_categories
(
    id           serial primary key,
    id_coureur   integer not null,
    id_categorie integer not null,
    foreign key (id_coureur) references coureurs (id),
    foreign key (id_categorie) references categories (id),
    unique (id_coureur, id_categorie)
);

create table coureur_etapes
(
    id                serial primary key,
    id_coureur        integer not null,
    id_etape          integer not null,
    dateheure_depart  timestamp,
    dateheure_arrivee timestamp check ( dateheure_arrivee > dateheure_depart ),
    duree_penalite    interval not null check ( duree_penalite >= '00:00:00'::interval ) default '00:00:00'::interval,
    foreign key (id_coureur) references coureurs (id),
    foreign key (id_etape) references etapes (id),
    unique (id_coureur, id_etape)
);

create table point_classements
(
    id     serial primary key,
    rang   int unique not null check ( rang > 0 ),
    points int unique not null check ( points >= 0 )
);
