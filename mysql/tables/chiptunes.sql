CREATE TABLE chiptunes (
  id bigint PRIMARY KEY,
  name text NOT NULL,
  size bigint,
  file varchar(255),
  url varchar(255),
  md5 char(32),
  format text,
  songs int,
  game bigint
);

