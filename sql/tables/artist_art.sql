SET search_path = public, pg_catalog;

CREATE TABLE artist_art (
  artist_id bigint,
  url varchar,
  PRIMARY KEY(artist_id, url)
);
