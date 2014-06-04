ALTER TABLE album_artist
    ADD CONSTRAINT album_artist_album_id_fkey
    FOREIGN KEY (album_id)
    REFERENCES albums(id);

ALTER TABLE album_artist
    ADD CONSTRAINT album_artist_artist_id_fkey
    FOREIGN KEY (artist_id)
    REFERENCES artists(id);

