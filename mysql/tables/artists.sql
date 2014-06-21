CREATE TABLE artists (
  id bigint PRIMARY KEY,
  name text NOT NULL,
  real_name text,
  gender char(1),
  birthdate date,
  birthplace varchar(255),
  forum_link varchar(255)
);

