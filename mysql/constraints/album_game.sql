ALTER TABLE album_game
    ADD CONSTRAINT album_game_album_id_fkey
    FOREIGN KEY (album_id)
    REFERENCES albums(id);

ALTER TABLE album_game
    ADD CONSTRAINT album_game_game_id_fkey
    FOREIGN KEY (game_id)
    REFERENCES games(id);

