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

-- Start the id from 789 so that item code is at least 3 characters length
ALTER SEQUENCE item_id_seq RESTART WITH 789;

CREATE TABLE item_tag (
  item_id BIGINT NOT NULL REFERENCES item (id),
  tag_id  BIGINT NOT NULL REFERENCES tag (id),
  CONSTRAINT item_tag_pkey PRIMARY KEY (item_id, tag_id)
);

-- Function to get item code from item id
CREATE FUNCTION item_code(id BIGINT)
  RETURNS VARCHAR(14) AS $code$

DECLARE
  alphabet VARCHAR(24);
  base     INTEGER DEFAULT 0;
  code     VARCHAR(14);
  divisor  BIGINT;
  mod      INTEGER DEFAULT 0;

BEGIN
  alphabet := '3KMEQPNHABTGCWUVRYZFSXJD';
  base := char_length(alphabet);
  code := '';

  WHILE id >= base LOOP
    divisor := (id / base) :: BIGINT;
    mod := (id - (base * divisor)) :: INTEGER;
    code := concat(substring(alphabet FROM mod + 1 FOR 1), code);
    id := divisor;
  END LOOP;

  IF id > 0
  THEN
    code = concat(substring(alphabet FROM (id :: INTEGER) + 1 FOR 1), code);
  END IF;

  RETURN (code);

END; $code$
LANGUAGE PLPGSQL;

-- Trigger function to set item code
CREATE FUNCTION set_item_code()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  NEW.code := item_code(NEW.id);
  RETURN NEW;
END;
$BODY$
LANGUAGE PLPGSQL;

-- Trigger to set item code before insert
CREATE TRIGGER set_item_code_trigger
BEFORE INSERT
ON item
FOR EACH ROW
EXECUTE PROCEDURE set_item_code();