ALTER TABLE chiptunes
    ADD CONSTRAINT chiptunes_game_fkey
    FOREIGN KEY (game)
    REFERENCES games(id);

