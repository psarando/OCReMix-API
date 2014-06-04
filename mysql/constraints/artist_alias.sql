ALTER TABLE artist_alias
    ADD CONSTRAINT artist_alias_artist_id_fkey
    FOREIGN KEY (artist_id)
    REFERENCES artists(id);

