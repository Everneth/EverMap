USE `emi`;

DROP TABLE IF EXISTS `markers`;

CREATE TABLE IF NOT EXISTS `markers` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE,
  `label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE,
  `owned_by` int(10) NOT NULL,
  `type` ENUM('SHOP', 'BASE') NOT NULL,
  `verified` boolean NOT NULL,
  `verified_by` int (10),
  KEY `fk_markers_owned_by_idx` (`owned_by`),
  KEY `fk_markers_verified_by_idx` (`verified_by`),
  CONSTRAINT `fk_markers_owned_by_players` FOREIGN KEY (`owned_by`) REFERENCES `players` (`player_id`),
  CONSTRAINT `fk_markers_verified_by_players` FOREIGN KEY (`verified_by`) REFERENCES `players` (`player_id`)
);

INSERT INTO
  markers (
    `id`,
    `label`,
    `owned_by`,
    `type`,
    `verified`,
    `verified_by`
  )
VALUES
  (1, "This is a test label", 1, "base", 1, 3);