SET search_path = public, pg_catalog;

CREATE TABLE organization_reference (
  organization_id bigint,
  url varchar,
  PRIMARY KEY(organization_id, url)
);

