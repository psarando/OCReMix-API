SET search_path = public, pg_catalog;

CREATE TABLE remix_download (
  remix_id char(8),
  url varchar,
  PRIMARY KEY(remix_id, url)
);
