
-- Function and trigger for checking the number of coureurs per team on insert
create or replace function check_nombre_coureur_equipe() returns trigger as
$$
begin
    if
        (select count(*)
         from coureur_etapes ce
                  join coureurs c on ce.id_coureur = c.id
         where c.id_equipe = (select id_equipe from coureurs where id = NEW.id_coureur)
           and ce.id_etape = NEW.id_etape)
            >
        (select nombre_coureur_equipe from etapes where id = NEW.id_etape)
    then
        raise exception 'Le nombre de coureur par équipe est dépassé';
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

    -- Return true if everything went well
    RETURN true;

EXCEPTION
    WHEN OTHERS THEN
        -- Rollback any changes in case of error
        RAISE EXCEPTION 'Error during database reset: %', SQLERRM;
END;
$$ LANGUAGE plpgsql;

select reset_database();
