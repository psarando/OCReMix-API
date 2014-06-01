SET search_path = public, pg_catalog;

CREATE TABLE remix_artist (
  remix_id char(8),
  artist_id bigint,
  PRIMARY KEY(remix_id, artist_id)
);
