-- --------------------------------------------------------
-- ホスト:                          127.0.0.1
-- サーバーのバージョン:                   8.0.26 - MySQL Community Server - GPL
-- サーバー OS:                      Win64
-- HeidiSQL バージョン:               12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--  テーブル e1.achievement の構造をダンプしています
CREATE TABLE IF NOT EXISTS `achievement` (
  `achieve_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(10) NOT NULL,
  `family_id` varchar(10) NOT NULL,
  `housework_id` int NOT NULL DEFAULT (0),
  `date` date DEFAULT (curdate()),
  `achieve_history` int NOT NULL,
  PRIMARY KEY (`achieve_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- エクスポートするデータが選択されていません

--  テーブル e1.category の構造をダンプしています
CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(15) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- エクスポートするデータが選択されていません

--  テーブル e1.family の構造をダンプしています
CREATE TABLE IF NOT EXISTS `family` (
  `family_id` varchar(10) NOT NULL,
  `fami_pass` varchar(64) NOT NULL,
  PRIMARY KEY (`family_id`),
  UNIQUE KEY `family_id` (`family_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- エクスポートするデータが選択されていません

--  テーブル e1.housework の構造をダンプしています
CREATE TABLE IF NOT EXISTS `housework` (
  `housework_id` int NOT NULL AUTO_INCREMENT,
  `housework_name` varchar(20) NOT NULL,
  `family_id` varchar(10) NOT NULL,
  `category_id` int NOT NULL,
  `housework_level` int DEFAULT '1',
  `noti_flag` int DEFAULT '0',
  `noti_time` time DEFAULT '07:00:00',
  `frequency` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `manual` varchar(200) DEFAULT NULL,
  `fixed_role` varchar(10) DEFAULT NULL,
  `variable_role` varchar(10) DEFAULT NULL,
  `log` int DEFAULT '0',
  PRIMARY KEY (`housework_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- エクスポートするデータが選択されていません

--  テーブル e1.notification の構造をダンプしています
CREATE TABLE IF NOT EXISTS `notification` (
  `noti_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(10) NOT NULL,
  `noti_datetime` datetime DEFAULT (now()),
  `noti_content` varchar(50) NOT NULL,
  PRIMARY KEY (`noti_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- エクスポートするデータが選択されていません

--  テーブル e1.today_housework の構造をダンプしています
CREATE TABLE IF NOT EXISTS `today_housework` (
  `today_housework_id` int NOT NULL AUTO_INCREMENT,
  `housework_id` int NOT NULL DEFAULT (0),
  `date` date DEFAULT (curdate()),
  PRIMARY KEY (`today_housework_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- エクスポートするデータが選択されていません

--  テーブル e1.today_memo の構造をダンプしています
CREATE TABLE IF NOT EXISTS `today_memo` (
  `memo_id` int NOT NULL AUTO_INCREMENT,
  `family_id` varchar(10) NOT NULL,
  `memo` varchar(200) DEFAULT NULL,
  `date` date DEFAULT (curdate()),
  PRIMARY KEY (`memo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- エクスポートするデータが選択されていません

--  テーブル e1.user の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` varchar(10) NOT NULL,
  `family_id` varchar(10) NOT NULL,
  `password` varchar(64) NOT NULL,
  `share_goal` float DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- エクスポートするデータが選択されていません

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
