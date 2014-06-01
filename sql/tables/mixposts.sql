SET search_path = public, pg_catalog;

CREATE TABLE mixposts (
  remix_id char(8) PRIMARY KEY,
  posted date NOT NULL,
  forum_link varchar NOT NULL,
  review text NOT NULL
);
