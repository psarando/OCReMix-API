SET search_path = public, pg_catalog;

ALTER TABLE ONLY song_alias
    ADD CONSTRAINT song_alias_song_id_fkey
    FOREIGN KEY (song_id)
    REFERENCES songs(id);
