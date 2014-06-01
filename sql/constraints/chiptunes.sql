SET search_path = public, pg_catalog;

ALTER TABLE ONLY chiptunes
    ADD CONSTRAINT chiptunes_game_id_fkey
    FOREIGN KEY (game_id)
    REFERENCES games(id);
