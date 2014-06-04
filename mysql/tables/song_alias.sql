CREATE TABLE song_alias (
  song_id bigint,
  alias varchar(255),
  PRIMARY KEY(song_id, alias)
);

