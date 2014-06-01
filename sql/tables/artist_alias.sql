SET search_path = public, pg_catalog;

CREATE TABLE artist_alias (
  artist_id bigint,
  alias text,
  PRIMARY KEY(artist_id, alias)
);
