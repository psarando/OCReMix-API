SET search_path = public, pg_catalog;

ALTER TABLE ONLY artist_alias
    ADD CONSTRAINT artist_alias_artist_id_fkey
    FOREIGN KEY (artist_id)
    REFERENCES artists(id);
