CREATE TABLE movie
(
  id     BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  director VARCHAR(255),
  title VARCHAR(255),
  year INT(11)                NOT NULL,
  genre  VARCHAR(255),
  rating   INT(11)                NOT NULL
);

