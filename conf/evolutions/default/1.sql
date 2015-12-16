# --- !Ups

CREATE TABLE brand (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS brand;
