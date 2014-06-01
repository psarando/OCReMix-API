SET search_path = public, pg_catalog;

ALTER TABLE ONLY game_art
    ADD CONSTRAINT game_art_game_id_fkey
    FOREIGN KEY (game_id)
    REFERENCES games(id);
