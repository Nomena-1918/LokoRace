
-- Function and trigger for checking the number of coureurs per team on insert
create or replace function check_nombre_coureur_equipe() returns trigger as
$$
begin
    if
        (select count(*)
         from coureur_etapes ce
                  join coureurs c on ce.id_coureur = c.id
         where c.id_equipe = (select id_equipe from coureurs where id = NEW.id_coureur)
           and ce.id_etape = NEW.id_etape) + 1
            >
        (select nombre_coureur_equipe from etapes where id = NEW.id_etape)
    then
        raise exception 'Le nombre de coureurs par équipe est dépassé';
    end if;
    return NEW;
end;
$$ language plpgsql;


-- Function and trigger for checking the number of coureurs per team before race debut
create trigger check_nombre_coureur_equipe
    before insert
    on coureur_etapes
    for each row
execute procedure check_nombre_coureur_equipe();

-- Function and trigger for checking the number of coureurs per team EXACT
create or replace function check_nombre_coureur_equipe_exact() returns trigger as
$$
begin
    if
        (select count(*)
         from coureur_etapes ce
                  join coureurs c on ce.id_coureur = c.id
         where c.id_equipe = (select id_equipe from coureurs where id = NEW.id_coureur)
           and ce.id_etape = NEW.id_etape)
            !=
        (select nombre_coureur_equipe from etapes where id = NEW.id_etape)
    then
        raise exception 'Le nombre de coureurs par équipe invalide : % coureurs attendus, % coureurs présents',
            (select nombre_coureur_equipe from etapes where id = NEW.id_etape),
            (select count(*)
             from coureur_etapes ce
                      join coureurs c on ce.id_coureur = c.id
             where c.id_equipe = (select id_equipe from coureurs where id = NEW.id_coureur)
               and ce.id_etape = NEW.id_etape);
    end if;
    return NEW;
end;
$$ language plpgsql;

-- Function for reinitializing the database
CREATE OR REPLACE FUNCTION reset_database()
    RETURNS boolean AS $$
DECLARE
BEGIN
    -- Disable triggers to avoid foreign key constraint issues
    ALTER TABLE coureur_etapes DISABLE TRIGGER ALL;
    ALTER TABLE coureur_categories DISABLE TRIGGER ALL;
    ALTER TABLE coureurs DISABLE TRIGGER ALL;
    ALTER TABLE genres DISABLE TRIGGER ALL;
    ALTER TABLE equipes DISABLE TRIGGER ALL;
    ALTER TABLE categories DISABLE TRIGGER ALL;
    ALTER TABLE etapes DISABLE TRIGGER ALL;
    ALTER TABLE point_classements DISABLE TRIGGER ALL;
    ALTER TABLE courses DISABLE TRIGGER ALL;
    ALTER TABLE penalite_etape_equipe DISABLE TRIGGER ALL;


    -- Truncate all tables except "admin"
    TRUNCATE TABLE coureur_etapes RESTART IDENTITY CASCADE;
    TRUNCATE TABLE coureur_categories RESTART IDENTITY CASCADE;
    TRUNCATE TABLE coureurs RESTART IDENTITY CASCADE;
    TRUNCATE TABLE genres RESTART IDENTITY CASCADE;
    TRUNCATE TABLE equipes RESTART IDENTITY CASCADE;
    TRUNCATE TABLE categories RESTART IDENTITY CASCADE;
    TRUNCATE TABLE etapes RESTART IDENTITY CASCADE;
    TRUNCATE TABLE point_classements RESTART IDENTITY CASCADE;
    TRUNCATE TABLE courses RESTART IDENTITY CASCADE;
    TRUNCATE TABLE penalite_etape_equipe RESTART IDENTITY CASCADE ;


    -- Enable triggers after truncation
    ALTER TABLE coureur_etapes ENABLE TRIGGER ALL;
    ALTER TABLE coureur_categories ENABLE TRIGGER ALL;
    ALTER TABLE coureurs ENABLE TRIGGER ALL;
    ALTER TABLE genres ENABLE TRIGGER ALL;
    ALTER TABLE equipes ENABLE TRIGGER ALL;
    ALTER TABLE categories ENABLE TRIGGER ALL;
    ALTER TABLE etapes ENABLE TRIGGER ALL;
    ALTER TABLE point_classements ENABLE TRIGGER ALL;
    ALTER TABLE courses ENABLE TRIGGER ALL;
    ALTER TABLE penalite_etape_equipe ENABLE TRIGGER ALL;


    -- Return true if everything went well
    RETURN true;

EXCEPTION
    WHEN OTHERS THEN
        -- Rollback any changes in case of error
        RAISE EXCEPTION 'Error during database reset: %', SQLERRM;
END;
$$ LANGUAGE plpgsql;

--select reset_database();

/*
-- Attribuer les catégories aux coureurs en fonction de l'âge calculé
INSERT INTO coureur_categories (id_coureur, id_categorie)
SELECT c.id, p.id_categorie
FROM coureurs c
         JOIN parametrage_categorie p
              ON c.id_genre = p.id_genre
                  AND (EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.date_naissance))::int <@ p.tranche_age);
*/
CREATE OR REPLACE FUNCTION assign_categories_to_runners()
    RETURNS void AS $$
BEGIN
    BEGIN
        -- Attribuer les catégories aux coureurs en fonction de l'âge calculé
        INSERT INTO coureur_categories (id_coureur, id_categorie)
        SELECT c.id, p.id_categorie
        FROM coureurs c
                 JOIN parametrage_categorie p
                      ON c.id_genre = coalesce(p.id_genre, c.id_genre)
                          AND (EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.date_naissance))::int <@ p.tranche_age);
    EXCEPTION
        WHEN OTHERS THEN
            -- Lever une exception pour annuler la transaction
            RAISE EXCEPTION 'An error occurred: %', SQLERRM;
    END;
END;
$$ LANGUAGE plpgsql;


select assign_categories_to_runners();
