# --- !Ups

CREATE TABLE Item
(
  id          BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name        VARCHAR(50)        NOT NULL,
  description VARCHAR(250),
  remarks     VARCHAR(250),
  markedPrice DECIMAL(10, 2)     NOT NULL,
  categoryId  INT,
  brandId     INT,
  FOREIGN KEY (categoryId) REFERENCES Category (id),
  FOREIGN KEY (brandId) REFERENCES Brand (id)
);


# --- !Downs

DROP TABLE IF EXISTS `Item`;

