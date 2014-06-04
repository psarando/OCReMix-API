ALTER TABLE mixposts
    ADD CONSTRAINT mixposts_remix_id_fkey
    FOREIGN KEY (remix_id)
    REFERENCES remixes(id);

