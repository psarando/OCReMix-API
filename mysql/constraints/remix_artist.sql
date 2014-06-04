ALTER TABLE remix_artist
    ADD CONSTRAINT remix_artist_remix_id_fkey
    FOREIGN KEY (remix_id)
    REFERENCES remixes(id);

ALTER TABLE remix_artist
    ADD CONSTRAINT remix_artist_artist_id_fkey
    FOREIGN KEY (artist_id)
    REFERENCES artists(id);

