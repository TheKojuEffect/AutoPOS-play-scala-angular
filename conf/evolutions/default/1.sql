# --- !Ups

CREATE TABLE `Brand` (
  `id`   INT(5) PRIMARY KEY  AUTO_INCREMENT,
  `name` VARCHAR(50) UNIQUE NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS `Brand`;

