SET search_path = public, pg_catalog;

CREATE TABLE album_art (
  album_id bigint,
  url varchar,
  PRIMARY KEY(album_id, url)
);
