# --- !Ups

CREATE TABLE `Category` (
  `id`   INTEGER AUTO_INCREMENT PRIMARY KEY,
  `shortName` VARCHAR(3) UNIQUE  NOT NULL,
  `name`      VARCHAR(50) UNIQUE NOT NULL
);


# --- !Downs

DROP TABLE IF EXISTS `Category`;

