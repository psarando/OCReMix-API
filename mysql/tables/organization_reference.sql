CREATE TABLE organization_reference (
  organization_id bigint,
  url varchar(255),
  PRIMARY KEY(organization_id, url)
);

