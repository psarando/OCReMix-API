SET search_path = public, pg_catalog;

CREATE TABLE album_game (
  album_id bigint,
  game_id bigint,
  PRIMARY KEY(album_id, game_id)
);
