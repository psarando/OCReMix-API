SET search_path = public, pg_catalog;

CREATE TABLE artists (
  id bigint PRIMARY KEY,
  name text NOT NULL,
  name_jp text,
  gender char(1),
  birthdate date,
  birthplace varchar,
  forum_link varchar
);
