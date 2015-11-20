# --- !Ups

CREATE TABLE `people` (
  `id`   INT(5) PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `age`  INT(5)      NOT NULL
);

CREATE TABLE `Brand` (
  `id`   INT(5) PRIMARY KEY  AUTO_INCREMENT,
  `name` VARCHAR(50) UNIQUE NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS `people`;

DROP TABLE IF EXISTS `Brand`;

