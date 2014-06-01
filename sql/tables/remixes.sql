SET search_path = public, pg_catalog;

CREATE TABLE remixes (
  id char(8) PRIMARY KEY,
  title text NOT NULL,
  game bigint NOT NULL,
  year date,
  album bigint,
  encoder text,
  size bigint,
  md5 char(32),
  torrent varchar,
  comment text,
  lyrics text
);
