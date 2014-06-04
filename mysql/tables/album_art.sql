CREATE TABLE album_art (
  album_id bigint,
  url varchar(255),
  PRIMARY KEY(album_id, url)
);

