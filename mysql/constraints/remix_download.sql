ALTER TABLE remix_download
    ADD CONSTRAINT remix_download_remix_id_fkey
    FOREIGN KEY (remix_id)
    REFERENCES remixes(id);

