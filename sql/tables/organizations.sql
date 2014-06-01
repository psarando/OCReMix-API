SET search_path = public, pg_catalog;

CREATE TABLE organizations (
  id bigint PRIMARY KEY,
  name text NOT NULL,
  name_jp text
);
