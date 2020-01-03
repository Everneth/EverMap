USE `emi`;

DROP TABLE IF EXISTS `markers`;

CREATE TABLE IF NOT EXISTS `markers` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE,
  `label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE,
  `owned_by` int(10) NOT NULL,
  `type` ENUM('shop', 'base') NOT NULL,
  `verified` boolean,
  `verified_by` int (10) NOT NULL,
  `verified_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp null ON UPDATE CURRENT_TIMESTAMP,
  KEY `fk_markers_owned_by_idx` (`owned_by`),
  KEY `fk_markers_verified_by_idx` (`verified_by`),
  CONSTRAINT `fk_markers_owned_by_players` FOREIGN KEY (`owned_by`) REFERENCES `players` (`player_id`),
  CONSTRAINT `fk_markers_verified_by_players` FOREIGN KEY (`verified_by`) REFERENCES `players` (`player_id`)
);