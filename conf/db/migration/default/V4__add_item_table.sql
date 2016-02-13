CREATE TABLE item
(
  id           SERIAL PRIMARY KEY,
  code         VARCHAR(7) UNIQUE      NOT NULL CHECK (length(code) >= 3),
  name         VARCHAR(50)            NOT NULL,
  description  VARCHAR(250),
  remarks      VARCHAR(250),
  marked_price DECIMAL(10, 2)         NOT NULL CHECK (marked_price > 0),
  category_id  INTEGER REFERENCES category (id),
  brand_id     INTEGER REFERENCES brand (id)
);

ALTER SEQUENCE item_id_seq RESTART WITH 1001;

CREATE TABLE item_tag
(
  item_id INTEGER NOT NULL REFERENCES item (id),
  tag_id  INTEGER NOT NULL REFERENCES tag (id),
  CONSTRAINT item_tag_pkey PRIMARY KEY (item_id, tag_id)
);