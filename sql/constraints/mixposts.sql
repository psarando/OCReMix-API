SET search_path = public, pg_catalog;

ALTER TABLE ONLY mixposts
    ADD CONSTRAINT mixposts_remix_id_fkey
    FOREIGN KEY (remix_id)
    REFERENCES remixes(id);

