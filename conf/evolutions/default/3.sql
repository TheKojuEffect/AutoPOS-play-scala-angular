# --- !Ups

CREATE TABLE tag (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS tag;

