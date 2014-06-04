ALTER TABLE composer_song
    ADD CONSTRAINT composer_song_composer_id_fkey
    FOREIGN KEY (composer_id)
    REFERENCES artists(id);

ALTER TABLE composer_song
    ADD CONSTRAINT composer_song_song_id_fkey
    FOREIGN KEY (song_id)
    REFERENCES songs(id);

