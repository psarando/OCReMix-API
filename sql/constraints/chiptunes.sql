SET search_path = public, pg_catalog;

ALTER TABLE ONLY chiptunes
    ADD CONSTRAINT chiptunes_game_fkey
    FOREIGN KEY (game)
    REFERENCES games(id);
