SET search_path = public, pg_catalog;

CREATE TABLE system_reference (
  system_id varchar,
  url varchar,
  PRIMARY KEY(system_id, url)
);

