-- VUE POUR AVOIR LES RANGS DES COUREURS PAR ETAPE
CREATE OR REPLACE VIEW v_rang_coureur_etape AS
SELECT ce.id,
       e.id_course,
       id_coureur,
       id_etape,
       dateheure_depart,
       dateheure_arrivee,
       duree_penalite,
       (COALESCE(dateheure_arrivee, NOW()) - dateheure_depart + duree_penalite) AS duree_course,
       RANK() OVER (PARTITION BY id_etape ORDER BY (dateheure_arrivee + duree_penalite)) AS rang_coureur
FROM coureur_etapes ce
join etapes e on e.id = id_etape
ORDER BY id_etape, dateheure_arrivee;



-- VUE POUR AVOIR LES RANGS DES COUREURS EN FONCTION DE LEUR RANG
create or replace view v_classement_etape as
select
    row_number() over () as id,
    id_course,
    rce.id as id_rang_coureur_etape,
    id_etape,
    id_coureur,
    rang_coureur,
    coalesce(points, 0) as points
from v_rang_coureur_etape rce
         left join point_classements pc on rang_coureur = pc.rang
order by id_etape, rang_coureur;


-- VUE POUR AVOIR LES RANGS DES EQUIPES : SOMME DES POINTS PAR COUREUR ET LEUR RANG EN FONCTION DE LA SOMME DES POINTS
create or replace view v_classement_equipe as
select
    row_number() over () as id,
    id_course,
    id_equipe,
    sum(points) as points,
    row_number() over (order by sum(points) desc) as rang_equipe
from v_classement_etape
         join coureurs c on v_classement_etape.id_coureur = c.id
group by id_course, id_equipe
order by rang_equipe;
