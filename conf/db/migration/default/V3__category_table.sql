CREATE TABLE category (
  id         BIGSERIAL PRIMARY KEY,
  short_name VARCHAR(3) UNIQUE  NOT NULL,
  name       VARCHAR(50) UNIQUE NOT NULL
);

