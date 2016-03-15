CREATE TABLE brand (
  id              BIGSERIAL PRIMARY KEY,
  name            VARCHAR(50) UNIQUE       NOT NULL,

  created_on      TIMESTAMP DEFAULT now()  NOT NULL,
  last_updated_on TIMESTAMP DEFAULT now()  NOT NULL
);

-- Trigger to set audit dates before insert
CREATE TRIGGER set_audit_dates_on_brand
BEFORE INSERT
ON brand
FOR EACH ROW
EXECUTE PROCEDURE set_audit_dates();

-- Trigger to update audit dates before update
CREATE TRIGGER update_audit_dates_on_brand
BEFORE UPDATE
ON brand
FOR EACH ROW
EXECUTE PROCEDURE update_audit_dates();