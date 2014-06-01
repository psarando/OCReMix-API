SET search_path = public, pg_catalog;

CREATE TABLE song_alias (
  song_id bigint,
  alias text,
  PRIMARY KEY(song_id, alias)
);
