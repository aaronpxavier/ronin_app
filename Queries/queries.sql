ALTER TABLE artist
    ADD COLUMN search_index tsvector;
update artist
set search_index =
            setweight(to_tsvector(username), 'A') ||
            setweight(to_tsvector(last_name), 'B') ||
            setweight(to_tsvector(first_name), 'C') ||
            setweight(to_tsvector(bio), 'D');
CREATE INDEX search_index
    ON artist
    USING GIN(search_index);

UPDATE artist
SET search_index =
            setweight(to_tsvector(username), 'A') ||
            setweight(to_tsvector(last_name), 'B') ||
            setweight(to_tsvector(first_name), 'C') ||
            setweight(to_tsvector(bio), 'D')
where id = 1;

SELECT * FROM artist
where search_index @@ plainto_tsquery('tomb raider')
order by ts_rank(search_index, plainto_tsquery('tomb raider')) desc;

CREATE FUNCTION artist_tsvector_trigger() RETURNS trigger AS $$
begin
    new.search_index :=
    setweight(to_tsvector(new.username), 'A') ||
    setweight(to_tsvector(new.last_name), 'B') ||
    setweight(to_tsvector(new.first_name), 'C') ||
    setweight(to_tsvector(new.bio), 'D');
return new;
end
$$ LANGUAGE plpgsql;

CREATE TRIGGER artistTSVectorUpdate BEFORE INSERT OR UPDATE
                                                         ON artist FOR EACH ROW EXECUTE PROCEDURE artist_tsvector_trigger();