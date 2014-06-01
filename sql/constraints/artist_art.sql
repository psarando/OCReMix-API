SET search_path = public, pg_catalog;

ALTER TABLE ONLY artist_art
    ADD CONSTRAINT artist_art_artist_id_fkey
    FOREIGN KEY (artist_id)
    REFERENCES artists(id);
