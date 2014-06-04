ALTER TABLE album_reference
    ADD CONSTRAINT album_reference_album_id_fkey
    FOREIGN KEY (album_id)
    REFERENCES albums(id);

