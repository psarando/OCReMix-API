SET search_path = public, pg_catalog;

ALTER TABLE ONLY artist_reference
    ADD CONSTRAINT artist_reference_artist_id_fkey
    FOREIGN KEY (artist_id)
    REFERENCES artists(id);
