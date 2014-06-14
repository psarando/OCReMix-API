SET search_path = public, pg_catalog;

CREATE TABLE albums (
  id bigint PRIMARY KEY,
  name text NOT NULL,
  torrent varchar,
  catalog text,
  publisher bigint NOT NULL,
  release_date char(10),
  media varchar,
  vgmdb_id bigint,
  forum_link varchar
);
