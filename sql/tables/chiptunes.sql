SET search_path = public, pg_catalog;

CREATE TABLE chiptunes (
  id bigint PRIMARY KEY,
  name text NOT NULL,
  size bigint,
  file varchar,
  url varchar,
  md5 char(32),
  format text,
  songs int,
  game_id bigint
);
