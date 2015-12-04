# --- !Ups

CREATE TABLE `Category` (
  `id`        INT(5) PRIMARY KEY  AUTO_INCREMENT,
  `shortName` VARCHAR(3) UNIQUE  NOT NULL,
  `name`      VARCHAR(50) UNIQUE NOT NULL
);


# --- !Downs

DROP TABLE IF EXISTS `Category`;

