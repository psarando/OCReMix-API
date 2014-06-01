SET search_path = public, pg_catalog;

CREATE TABLE songs (
  id bigint PRIMARY KEY,
  name text NOT NULL,
  game bigint NOT NULL
);
