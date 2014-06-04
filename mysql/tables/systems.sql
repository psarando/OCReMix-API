CREATE TABLE systems (
  id varchar(255) PRIMARY KEY,
  name text NOT NULL,
  name_jp text,
  year date,
  release_date char(10),
  publisher bigint NOT NULL
);

