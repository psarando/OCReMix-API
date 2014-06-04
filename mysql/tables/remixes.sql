CREATE TABLE remixes (
  id char(8) PRIMARY KEY,
  title text NOT NULL,
  game bigint NOT NULL,
  year date,
  album bigint,
  encoder text,
  size bigint,
  md5 char(32),
  torrent varchar(255),
  comment text,
  lyrics text
);

