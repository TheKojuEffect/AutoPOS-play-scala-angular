CREATE FUNCTION item_code(id INTEGER)
  RETURNS VARCHAR(7) AS $code$

DECLARE
  alphabet   VARCHAR(27);
  base_count INTEGER DEFAULT 0;
  code       VARCHAR(7);
  divisor    DECIMAL(12, 4);
  mod        INTEGER DEFAULT 0;

BEGIN
  alphabet := '3KMEQPINHABTOGCWUVRYZFSXJDL';
  base_count := char_length(alphabet);
  code := '';

  WHILE id >= base_count LOOP
    divisor := id / base_count;
    mod := (id - (base_count * trunc(divisor, 0)));
    code := concat(substring(alphabet FROM mod + 1 FOR 1), code);
    id := trunc(divisor, 0);
  END LOOP;

  code = concat(substring(alphabet FROM id + 1 FOR 1), code);

  RETURN (code);

END; $code$
LANGUAGE PLPGSQL;