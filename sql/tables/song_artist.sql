SET search_path = public, pg_catalog;

CREATE TABLE song_artist (
  song_id bigint,
  artist_id bigint,
  PRIMARY KEY(song_id, artist_id)
);
