# --- !Ups

CREATE TABLE AutoPOS.Item_Tag
(
  itemId bigint NOT NULL,
  tagId int NOT NULL,
  FOREIGN KEY (itemId) REFERENCES Item (id),
  FOREIGN KEY (tagId) REFERENCES Tag (id)
);

# -- !Downs

DROP TABLE IF EXISTS `Item_Tag`;