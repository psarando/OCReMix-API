SET search_path = public, pg_catalog;

ALTER TABLE ONLY song_ost_name
    ADD CONSTRAINT song_ost_name_song_id_fkey
    FOREIGN KEY (song_id)
    REFERENCES songs(id);
