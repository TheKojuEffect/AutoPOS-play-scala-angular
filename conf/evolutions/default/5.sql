# --- !Ups

CREATE TABLE item_tag
(
  item_id BIGINT  NOT NULL REFERENCES item (id),
  tag_id  INTEGER NOT NULL REFERENCES tag (id),
  CONSTRAINT item_tag_pkey PRIMARY KEY (item_id, tag_id)
);


# -- !Downs

DROP TABLE IF EXISTS item_tag;