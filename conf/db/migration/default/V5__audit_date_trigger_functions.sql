-- Set created_on and last_updated_on to current timestamp
CREATE FUNCTION set_audit_dates()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  NEW.created_on := now();
  NEW.last_updated_on := now();
  RETURN NEW;
END;
$BODY$
LANGUAGE PLPGSQL;

-- Set created_on to original value and last_updated_on to current timestamp if values are changed
CREATE FUNCTION update_audit_dates()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  IF ROW (NEW.*) IS DISTINCT FROM ROW (OLD.*)
  THEN
    NEW.created_on := OLD.created_on;
    NEW.last_updated_on := now();
    RETURN NEW;
  ELSE
    RETURN OLD;
  END IF;
END;
$BODY$
LANGUAGE PLPGSQL;