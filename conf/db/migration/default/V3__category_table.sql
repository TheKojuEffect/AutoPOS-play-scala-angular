CREATE TABLE category (
  id              BIGSERIAL PRIMARY KEY,
  short_name      VARCHAR(3) UNIQUE        NOT NULL,
  name            VARCHAR(50) UNIQUE       NOT NULL,

  created_on      TIMESTAMP DEFAULT now()  NOT NULL,
  last_updated_on TIMESTAMP DEFAULT now()  NOT NULL
);

-- Trigger to set audit dates before insert
CREATE TRIGGER set_audit_dates_on_category
BEFORE INSERT
ON category
FOR EACH ROW
EXECUTE PROCEDURE set_audit_dates();

-- Trigger to update audit dates before update
CREATE TRIGGER update_audit_dates_on_category
BEFORE UPDATE
ON category
FOR EACH ROW
EXECUTE PROCEDURE update_audit_dates();
