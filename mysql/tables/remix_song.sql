CREATE TABLE remix_song (
  remix_id char(8),
  song_id bigint,
  PRIMARY KEY(remix_id, song_id)
);

