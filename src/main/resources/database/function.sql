
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

-- Function and trigger for checking