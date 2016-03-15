CREATE TABLE tag (
  id              BIGSERIAL PRIMARY KEY,
  name            VARCHAR(50) UNIQUE       NOT NULL,

  created_on      TIMESTAMP DEFAULT now()  NOT NULL,
  last_updated_on TIMESTAMP DEFAULT now()  NOT NULL
);

-- Trigger to set audit dates before insert
CREATE TRIGGER set_audit_dates_on_tag
BEFORE INSERT
ON tag
FOR EACH ROW
EXECUTE PROCEDURE set_audit_dates();

-- Trigger to update audit dates before update
CREATE TRIGGER update_audit_dates_on_tag
BEFORE UPDATE
ON tag
FOR EACH ROW
EXECUTE PROCEDURE update_audit_dates();
