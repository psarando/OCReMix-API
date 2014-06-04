ALTER TABLE artist_group
    ADD CONSTRAINT artist_group_artist_id_fkey
    FOREIGN KEY (artist_id)
    REFERENCES artists(id);

ALTER TABLE artist_group
    ADD CONSTRAINT artist_group_group_id_fkey
    FOREIGN KEY (group_id)
    REFERENCES artists(id);

