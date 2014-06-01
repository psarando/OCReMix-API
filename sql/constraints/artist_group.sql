SET search_path = public, pg_catalog;

ALTER TABLE ONLY artist_group
    ADD CONSTRAINT artist_group_artist_id_fkey
    FOREIGN KEY (artist_id)
    REFERENCES artists(id);

ALTER TABLE ONLY artist_group
    ADD CONSTRAINT artist_group_group_id_fkey
    FOREIGN KEY (group_id)
    REFERENCES artists(id);
