CREATE TABLE mixposts (
  remix_id char(8) PRIMARY KEY,
  posted date NOT NULL,
  forum_link varchar(255) NOT NULL,
  review text NOT NULL
);

