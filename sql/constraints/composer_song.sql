SET search_path = public, pg_catalog;

ALTER TABLE ONLY composer_song
    ADD CONSTRAINT composer_song_composer_id_fkey
    FOREIGN KEY (composer_id)
    REFERENCES artists(id);

ALTER TABLE ONLY composer_song
    ADD CONSTRAINT composer_song_song_id_fkey
    FOREIGN KEY (song_id)
    REFERENCES songs(id);
