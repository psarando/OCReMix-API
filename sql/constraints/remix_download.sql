SET search_path = public, pg_catalog;

ALTER TABLE ONLY remix_download
    ADD CONSTRAINT remix_download_remix_id_fkey
    FOREIGN KEY (remix_id)
    REFERENCES remixes(id);
