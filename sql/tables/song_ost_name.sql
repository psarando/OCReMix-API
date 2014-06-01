SET search_path = public, pg_catalog;

CREATE TABLE song_ost_name (
  song_id bigint,
  name text,
  PRIMARY KEY(song_id, name)
);
