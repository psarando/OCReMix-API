CREATE TABLE artist_alias (
  artist_id bigint,
  alias varchar(255),
  PRIMARY KEY(artist_id, alias)
);

