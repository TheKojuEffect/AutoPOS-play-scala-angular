CREATE TABLE item (
  id           BIGSERIAL PRIMARY KEY,
  code         VARCHAR(14) UNIQUE      NOT NULL CHECK (length(code) >= 3),
  name         VARCHAR(50)             NOT NULL,
  marked_price DECIMAL(10, 2)          NOT NULL CHECK (marked_price > 0),
  category_id  BIGINT REFERENCES category (id),
  brand_id     BIGINT REFERENCES brand (id),
  description  VARCHAR(250),
  location     VARCHAR(250),
  remarks      VARCHAR(250)
);

ALTER SEQUENCE item_id_seq RESTART WITH 789;

CREATE TABLE item_tag (
  item_id BIGINT NOT NULL REFERENCES item (id),
  tag_id  BIGINT NOT NULL REFERENCES tag (id),
  CONSTRAINT item_tag_pkey PRIMARY KEY (item_id, tag_id)
);