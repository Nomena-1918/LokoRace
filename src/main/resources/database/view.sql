-- VUE POUR AVOIR LES RANGS DES COUREURS
create or replace view v_rang_coureur_etape as
SELECT id,
       id_coureur,
       id_etape,
       dateheure_depart,
       dateheure_arrivee,
       duree_penalite,
       (coalesce(dateheure_arrivee, now()) - dateheure_depart + duree_penalite)                                       as duree_course,
       ROW_NUMBER()
       OVER (PARTITION BY id_etape ORDER BY (dateheure_arrivee + duree_penalite)) AS rang_coureur
FROM coureur_etapes
ORDER BY id_etape, dateheure_arrivee;

-- VUE POUR AVOIR LES RANGS DES COUREURS EN FONCTION DE LEUR RANG
create or replace view v_classement_etape as
select id_etape,
       id_coureur,
       rang_coureur,
       coalesce(points, 0) as points
from v_rang_coureur_etape
         left join point_classements pc on rang_coureur = pc.rang
order by id_etape, rang_coureur;
