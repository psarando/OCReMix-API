ALTER TABLE album_art
    ADD CONSTRAINT album_art_album_id_fkey
    FOREIGN KEY (album_id)
    REFERENCES albums(id);

