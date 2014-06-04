ALTER TABLE game_reference
    ADD CONSTRAINT game_reference_game_id_fkey
    FOREIGN KEY (game_id)
    REFERENCES games(id);

