SET search_path = public, pg_catalog;

CREATE TABLE game_reference (
  game_id bigint,
  url varchar,
  PRIMARY KEY(game_id, url)
);
