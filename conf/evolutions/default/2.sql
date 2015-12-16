# --- !Ups

CREATE TABLE category (
  id         SERIAL PRIMARY KEY,
  short_name VARCHAR(3) UNIQUE  NOT NULL,
  name       VARCHAR(50) UNIQUE NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS category;

