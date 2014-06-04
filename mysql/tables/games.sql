CREATE TABLE games (
  id bigint PRIMARY KEY,
  name text NOT NULL,
  name_short text,
  name_jp text,
  year date,
  publisher bigint NOT NULL,
  system varchar(255) NOT NULL
);

