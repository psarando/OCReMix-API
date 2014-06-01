SET search_path = public, pg_catalog;

ALTER TABLE ONLY album_reference
    ADD CONSTRAINT album_reference_album_id_fkey
    FOREIGN KEY (album_id)
    REFERENCES albums(id);
