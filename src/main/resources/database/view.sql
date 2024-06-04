--- VUE SOMME DES PENALITES DES EQUIPES PAR ETAPES
-- ...
create or replace view v_penalite_equipe_total as
select row_number() over ()    as id,
       e.id_course,
       pee.id_equipe,
       e2.nom                  as nom_equipe,
       e.id                    as id_etape,
       e.nom                   as nom_etape,
       sum(pee.duree_penalite) AS duree_penalite_equipe
from penalite_etape_equipe pee
         join
     etapes e ON pee.id_etape = e.id
         join public.equipes e2 on pee.id_equipe = e2.id
group by pee.id_equipe, e.id_course, e2.nom, e.id;



-- VUE POUR AVOIR LES RANGS DES COUREURS PAR ETAPE
CREATE OR REPLACE VIEW v_rang_coureur_etape AS
SELECT ce.id,
       e.id_course,
       id_coureur,
       e2.id                                                                                as id_equipe,
       c2.nom                                                                               as nom_coureur,
       e2.nom                                                                               as nom_equipe,
       ce.id_etape,
       dateheure_depart,
       dateheure_arrivee,
       coalesce(duree_penalite_equipe, '00:00:00'::interval)                                as duree_penalite,
       (COALESCE(dateheure_arrivee, NOW()) - dateheure_depart +
        coalesce(duree_penalite_equipe, '00:00:00'::interval))                              AS duree_course,
       dense_rank()
       OVER (PARTITION BY ce.id_etape ORDER BY (dateheure_arrivee + coalesce(duree_penalite_equipe, '00:00:00'::interval))) AS rang_coureur
FROM coureur_etapes ce
         join etapes e on e.id = id_etape
         join coureurs c2 on c2.id = ce.id_coureur
         join public.equipes e2 on c2.id_equipe = e2.id
         left join v_penalite_equipe_total vpet on vpet.id_etape = ce.id_etape and vpet.id_equipe = e2.id
ORDER BY id_etape, (dateheure_arrivee + coalesce(duree_penalite_equipe, '00:00:00'::interval)), rang_coureur;



-- VUE POUR AVOIR LES RANGS DES COUREURS EN FONCTION DE LEUR RANG
create or replace view v_classement_etape as
select row_number() over () as id,
       id_course,
       rce.id               as id_rang_coureur_etape,
       id_etape,
       id_coureur,
       nom_coureur,
       nom_equipe,
       rang_coureur,
       coalesce(points, 0)  as points
from v_rang_coureur_etape rce
         left join point_classements pc on rang_coureur = pc.rang
order by id_etape, rang_coureur;



-- VUE POUR AVOIR LES RANGS DES EQUIPES : SOMME DES POINTS PAR COUREUR ET LEUR RANG EN FONCTION DE LA SOMME DES POINTS
create or replace view v_classement_equipe as
select row_number() over ()                          as id,
       id_course,
       id_equipe,
       nom_equipe,
       sum(points)                                   as points,
       dense_rank() over (order by sum(points) desc) as rang_equipe
from v_classement_etape
         join coureurs c on v_classement_etape.id_coureur = c.id
group by id_course, id_equipe, nom_equipe
order by rang_equipe;


-- VUE POUR AVOIR LES RANGS DES EQUIPES : SOMME DES POINTS PAR COUREUR ET LEUR RANG EN FONCTION DE LA SOMME DES POINTS
-- LES EQUIPES AVEC DES PARTICIPANTS EN MOINS NE SONT PAS PRISES EN COMPTE
/*CREATE OR REPLACE VIEW v_classement_equipe AS
SELECT ROW_NUMBER() OVER ()                          AS id,
       id_course,
       id_equipe,
       SUM(points)                                   AS points,
       RANK() OVER (ORDER BY SUM(points) DESC) AS rang_equipe
FROM (SELECT vce.id_course,
             vce.id_etape,
             c.id_equipe,
             vce.points,
             (SELECT COUNT(DISTINCT ce.id)
              FROM coureur_etapes ce
                       JOIN coureurs c2 ON ce.id_coureur = c2.id
              WHERE c2.id_equipe = c.id_equipe
                AND ce.id_etape = vce.id_etape) AS nb_coureurs_inscrits,
             e.nombre_coureur_equipe            AS nb_coureurs_requis
      FROM v_classement_etape vce
               JOIN coureurs c ON vce.id_coureur = c.id
               JOIN etapes e ON vce.id_etape = e.id) subquery
WHERE nb_coureurs_inscrits = nb_coureurs_requis
GROUP BY id_course, id_equipe
ORDER BY rang_equipe;*/

-- ========== CATEGORIE ======================


-- VUE POUR AVOIR LES RANGS DES COUREURS PAR ETAPE PAR CATEGORIE
CREATE OR REPLACE VIEW v_rang_coureur_etape_categorie AS
SELECT vrce.id,
       id_course,
       vrce.id_coureur,
       cc.id_categorie,
       c.nom                                                                                                                       as nom_categorie,
       nom_coureur,
       nom_equipe,
       id_etape,
       dateheure_depart,
       dateheure_arrivee,
       duree_penalite,
       duree_course,
       dense_rank()
       OVER (PARTITION BY id_etape, cc.id_categorie ORDER BY (dateheure_arrivee + coalesce(duree_penalite, '00:00:00'::interval))) AS rang_coureur
FROM v_rang_coureur_etape vrce
         join coureur_categories cc on cc.id_coureur = vrce.id_coureur
         join categories c on c.id = cc.id_categorie
ORDER BY id_etape, dateheure_arrivee;



-- VUE POUR AVOIR LES RANGS DES COUREURS EN FONCTION DE LEUR RANG PAR CATEGORIE
create or replace view v_classement_etape_categorie as
select row_number() over () as id,
       id_course,
       vrce.id              as id_v_rang_coureur_etape_categorie,
       id_etape,
       id_coureur,
       id_categorie,
       nom_categorie,
       nom_coureur,
       nom_equipe,
       rang_coureur,
       coalesce(points, 0)  as points
from v_rang_coureur_etape_categorie vrce
         left join point_classements pc on rang_coureur = pc.rang
order by id_etape, id_categorie, rang_coureur;



-- VUE POUR AVOIR LES RANGS DES EQUIPES : SOMME DES POINTS PAR COUREUR ET LEUR RANG EN FONCTION DE LA SOMME DES POINTS PAR CATEGORIE
create or replace view v_classement_equipe_categorie as
select row_number() over ()                                                    as id,
       id_course,
       id_categorie,
       nom_categorie,
       id_equipe,
       nom_equipe,
       sum(points)                                                             as points,
       dense_rank() OVER (PARTITION BY id_categorie ORDER BY SUM(points) DESC) AS rang_equipe
from v_classement_etape_categorie
         join coureurs c on v_classement_etape_categorie.id_coureur = c.id
group by id_course, id_categorie, id_equipe, nom_equipe, nom_categorie
order by id_categorie, rang_equipe;


-- PENALITE PAR ÉTAPE PAR ÉQUIPE
/*
CREATE VIEW v_penalite_equipe AS
SELECT row_number() over () as id,
    e.id_course,
       c.id_equipe,
       eq.nom            as nom_equipe,
       ce.id_etape,
       e.nom             as nom_etape,
       ce.duree_penalite AS duree_penalite_equipe
FROM coureur_etapes ce
         JOIN
     coureurs c ON ce.id_coureur = c.id
         join etapes e ON ce.id_etape = e.id
         join equipes eq on c.id_equipe = e.id
GROUP BY c.id_equipe,
         ce.id_etape, ce.duree_penalite, e.id_course, e.nom, eq.nom
order by e.id_course, ce.id_etape, c.id_equipe;
 */