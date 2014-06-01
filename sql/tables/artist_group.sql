SET search_path = public, pg_catalog;

CREATE TABLE artist_group (
  artist_id bigint,
  group_id bigint,
  PRIMARY KEY(artist_id, group_id)
);
