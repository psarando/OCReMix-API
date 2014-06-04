ALTER TABLE artist_art
    ADD CONSTRAINT artist_art_artist_id_fkey
    FOREIGN KEY (artist_id)
    REFERENCES artists(id);

