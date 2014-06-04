ALTER TABLE mixpost_evaluator
    ADD CONSTRAINT mixpost_evaluator_remix_id_fkey
    FOREIGN KEY (remix_id)
    REFERENCES remixes(id);

ALTER TABLE mixpost_evaluator
    ADD CONSTRAINT mixpost_evaluator_artist_id_fkey
    FOREIGN KEY (artist_id)
    REFERENCES artists(id);

