CREATE TABLE albums (
  id bigint PRIMARY KEY,
  name text NOT NULL,
  torrent varchar(255),
  catalog text,
  publisher bigint NOT NULL,
  release_date char(10),
  media varchar(255),
  vgmdb_id bigint,
  forum_link varchar(255)
);

