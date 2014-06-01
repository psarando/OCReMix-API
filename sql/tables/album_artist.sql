SET search_path = public, pg_catalog;

CREATE TABLE album_artist (
  album_id bigint,
  artist_id bigint,
  PRIMARY KEY(album_id, artist_id)
);
