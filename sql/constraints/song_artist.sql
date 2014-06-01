SET search_path = public, pg_catalog;

ALTER TABLE ONLY song_artist
    ADD CONSTRAINT song_artist_song_id_fkey
    FOREIGN KEY (song_id)
    REFERENCES songs(id);

ALTER TABLE ONLY song_artist
    ADD CONSTRAINT song_artist_artist_id_fkey
    FOREIGN KEY (artist_id)
    REFERENCES artists(id);
