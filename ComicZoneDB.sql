-- -------------------------------------------------------------
-- TablePlus 5.3.6(496)
--
-- https://tableplus.com/
--
-- Database: ComicZoneDB
-- Generation Time: 2023-05-22 00:19:41.0460
-- -------------------------------------------------------------


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


CREATE TABLE `books` (
  `id_books` varchar(20) NOT NULL,
  `nama_buku` varchar(255) NOT NULL,
  `harga` decimal(15,2) NOT NULL,
  PRIMARY KEY (`id_books`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customer` (
  `id_customer` varchar(255) NOT NULL,
  `name_customer` varchar(255) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `telepon` varchar(20) NOT NULL,
  PRIMARY KEY (`id_customer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `karyawan` (
  `id_karyawan` varchar(20) NOT NULL,
  `nama_karyawan` varchar(255) NOT NULL,
  `telepon` varchar(20) NOT NULL,
  PRIMARY KEY (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sewa` (
  `id_sewa` int NOT NULL AUTO_INCREMENT,
  `customer_id` varchar(255) DEFAULT NULL,
  `books_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tgl_peminjaman` date DEFAULT NULL,
  `tgl_pengembalian` date DEFAULT NULL,
  PRIMARY KEY (`id_sewa`),
  KEY `customer_id` (`customer_id`),
  KEY `books_id` (`books_id`),
  CONSTRAINT `sewa_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id_customer`),
  CONSTRAINT `sewa_ibfk_2` FOREIGN KEY (`books_id`) REFERENCES `books` (`id_books`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `books` (`id_books`, `nama_buku`, `harga`) VALUES
('Book-001', 'Love in chaos', 30000.00),
('Book-002', 'Disruption', 90000.00),
('Book-003', 'Bintang', 20000.00),
('Book-004', 'Sapiens', 15000.00),
('Book-005', 'The life changing mangic of tidying up', 13000.00),
('Book-006', 'Bumi', 12000.00);

INSERT INTO `customer` (`id_customer`, `name_customer`, `tanggal_lahir`, `telepon`) VALUES
('Cus-0001', 'Aldin', '1990-09-21', '08127776633'),
('Cus-0002', 'Berwyn', '1995-01-21', '08566666554'),
('Cus-0003', 'Deshi', '1997-02-08', '085202215664'),
('Cus-0004', 'Evania', '1996-06-06', '081285269462'),
('Cus-0005', 'Hansa', '1990-09-09', '085667894542'),
('Cus-0006', 'Harina', '1990-12-25', '081255446688');

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `telepon`) VALUES
('Kar-001', 'Javas', '08566666554'),
('Kar-002', 'Avast', '085202215664'),
('Kar-003', 'Skyper', '085667894542'),
('Kar-004', 'Nakama', '081255446699'),
('Kar-005', 'Jarvis', '081284587566'),
('Kar-006', 'Jevera', '081247856978');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;