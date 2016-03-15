CREATE TABLE item_quantity (
  item_id         BIGINT PRIMARY KEY REFERENCES item (id),
  quantity        INT                      NOT NULL CHECK (quantity > 0),

  created_on      TIMESTAMP DEFAULT now()  NOT NULL,
  last_updated_on TIMESTAMP DEFAULT now()  NOT NULL
);

-- Trigger to set audit dates before insert
CREATE TRIGGER set_audit_dates_on_item_quantity
BEFORE INSERT
ON item_quantity
FOR EACH ROW
EXECUTE PROCEDURE set_audit_dates();

-- Trigger to update audit dates before update
CREATE TRIGGER update_audit_dates_on_item_quantity
BEFORE UPDATE
ON item_quantity
FOR EACH ROW
EXECUTE PROCEDURE update_audit_dates();