# --- !Ups

CREATE TABLE item
(
  id           BIGSERIAL PRIMARY KEY NOT NULL,
  name         VARCHAR(50)           NOT NULL,
  description  VARCHAR(250),
  remarks      VARCHAR(250),
  marked_price DECIMAL(10, 2)        NOT NULL CHECK (marked_price > 0),
  category_id  INTEGER REFERENCES category (id),
  brand_id     INTEGER REFERENCES brand (id)
);


# --- !Downs

DROP TABLE IF EXISTS item;

