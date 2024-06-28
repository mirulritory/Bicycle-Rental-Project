-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 28, 2024 at 07:42 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bicycle_rental`
--

-- --------------------------------------------------------

--
-- Table structure for table `bicycle`
--

CREATE TABLE `bicycle` (
  `bicycleID` varchar(4) NOT NULL,
  `status` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bicycle`
--

INSERT INTO `bicycle` (`bicycleID`, `status`) VALUES
('B001', 'available'),
('B002', 'available'),
('B003', 'available'),
('B004', 'available'),
('B005', 'available'),
('B006', 'available'),
('B007', 'available'),
('B008', 'available'),
('B009', 'available'),
('B010', 'available');

-- --------------------------------------------------------

--
-- Table structure for table `rental`
--

CREATE TABLE `rental` (
  `userID` varchar(10) NOT NULL,
  `bicycleID` varchar(4) NOT NULL,
  `rentalTime` datetime NOT NULL,
  `rentalID` int(11) NOT NULL,
  `returnStat` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rental`
--

INSERT INTO `rental` (`userID`, `bicycleID`, `rentalTime`, `rentalID`, `returnStat`) VALUES
('B032210102', 'B001', '2024-06-26 10:00:00', 1, 'return'),
('a', 'B001', '2024-06-27 20:37:19', 2, 'return'),
('a', 'B002', '2024-06-27 20:39:01', 3, 'return'),
('a', 'B001', '2024-06-27 23:15:51', 4, 'returned'),
('a', 'B001', '2024-06-28 00:41:32', 5, ''),
('a', 'B002', '2024-06-28 00:55:44', 6, ''),
('a', 'B001', '2024-06-28 00:57:41', 7, ''),
('a', 'B001', '2024-06-28 01:08:55', 8, ''),
('a', 'B002', '2024-06-28 02:26:57', 9, 'returned'),
('a', 'B001', '2024-06-28 23:49:43', 10, 'returned'),
('a', 'B001', '2024-06-29 00:01:26', 11, 'returned'),
('a', 'B002', '2024-06-29 00:01:38', 12, 'returned'),
('a', 'B001', '2024-06-29 00:02:08', 13, 'returned'),
('a', 'B002', '2024-06-29 00:03:01', 14, 'returned'),
('roysongram', 'B002', '2024-06-29 00:59:20', 15, 'returned'),
('JohnDoe', 'B003', '2024-06-28 10:00:00', 16, 'return'),
('JaneSmith', 'B004', '2024-06-28 11:30:00', 17, 'return'),
('MikeJohnso', 'B005', '2024-06-28 13:00:00', 18, 'return'),
('EmilyBrown', 'B006', '2024-06-28 14:30:00', 19, 'return'),
('DavidLee', 'B007', '2024-06-28 16:00:00', 20, 'returned'),
('SarahWilso', 'B008', '2024-06-28 17:30:00', 21, ''),
('ChrisDavis', 'B009', '2024-06-28 19:00:00', 22, ''),
('EmmaMiller', 'B010', '2024-06-28 20:30:00', 23, ''),
('AndrewTayl', 'B003', '2024-06-28 22:00:00', 24, ''),
('OliviaMoor', 'B004', '2024-06-28 23:30:00', 25, 'returned'),
('DanielWils', 'B005', '2024-06-29 01:00:00', 26, 'returned'),
('SophiaThom', 'B006', '2024-06-29 02:30:00', 27, 'returned'),
('JamesMarti', 'B007', '2024-06-29 04:00:00', 28, 'returned'),
('LilyGarcia', 'B008', '2024-06-29 05:30:00', 29, 'returned'),
('MichaelLop', 'B009', '2024-06-29 07:00:00', 30, 'returned'),
('AvaHernand', 'B010', '2024-06-29 08:30:00', 31, 'returned'),
('BenjaminYo', 'B003', '2024-06-29 10:00:00', 32, 'returned'),
('MiaKing', 'B004', '2024-06-29 11:30:00', 33, 'returned'),
('EthanHill', 'B005', '2024-06-29 13:00:00', 34, 'returned'),
('AmeliaScot', 'B006', '2024-06-29 14:30:00', 35, 'returned'),
('WilliamGre', 'B007', '2024-06-29 16:00:00', 36, 'returned'),
('CharlotteA', 'B008', '2024-06-29 17:30:00', 37, 'returned'),
('NoahBaker', 'B009', '2024-06-29 19:00:00', 38, 'returned'),
('IsabellaNe', 'B010', '2024-06-29 20:30:00', 39, 'returned');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `userID` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phoneNo` varchar(10) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`userID`, `name`, `phoneNo`, `password`) VALUES
('9603200100', 'Abu bin Ahmad', '0136759576', '1234'),
('irfan', 'Irfan Syafie', '0111285011', '1234abc');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userID` varchar(10) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `phoneNo` varchar(10) NOT NULL,
  `passwordU` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `Name`, `phoneNo`, `passwordU`) VALUES
('a', 'Khairul Rezza bin Amran', '0134017243', 'a'),
('AmeliaScot', 'Amelia Scott', '0175948263', 'password20'),
('AndrewTayl', 'Andrew Taylor', '0112345678', 'password9'),
('AvaHernand', 'Ava Hernandez', '0123456789', 'password16'),
('B032210102', 'Nurin Irdina binti Hamzah', '0146923745', 'test'),
('BenjaminYo', 'Benjamin Young', '0192837465', 'password17'),
('CharlotteA', 'Charlotte Adams', '0112345678', 'password22'),
('ChrisDavis', 'Chris Davis', '0175948263', 'password7'),
('DanielWils', 'Daniel Wilson', '0147859632', 'password11'),
('DavidLee', 'David Lee', '0147852369', 'password5'),
('EmilyBrown', 'Emily Brown', '0192837465', 'password4'),
('EmmaMiller', 'Emma Miller', '0134697258', 'password8'),
('EthanHill', 'Ethan Hill', '0164875923', 'password19'),
('IsabellaNe', 'Isabella Nelson', '0147859632', 'password24'),
('JamesMarti', 'James Martinez', '0167483920', 'password13'),
('JaneSmith', 'Jane Smith', '0987654321', 'password2'),
('JohnDoe', 'John Doe', '0123456789', 'password1'),
('LilyGarcia', 'Lily Garcia', '0174692815', 'password14'),
('MiaKing', 'Mia King', '0147852369', 'password18'),
('MichaelLop', 'Michael Lopez', '0156748392', 'password15'),
('MikeJohnso', 'Mike Johnson', '0112233445', 'password3'),
('NoahBaker', 'Noah Baker', '0198765432', 'password23'),
('OliviaMoor', 'Olivia Moore', '0198765432', 'password10'),
('roysongram', 'Izzrafiq Roy bin Siddeeq', '0113915418', 'abc'),
('SarahWilso', 'Sarah Wilson', '0164875923', 'password6'),
('SophiaThom', 'Sophia Thomas', '0183746592', 'password12'),
('WilliamGre', 'William Green', '0134697258', 'password21');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bicycle`
--
ALTER TABLE `bicycle`
  ADD PRIMARY KEY (`bicycleID`);

--
-- Indexes for table `rental`
--
ALTER TABLE `rental`
  ADD PRIMARY KEY (`rentalID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`userID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `rental`
--
ALTER TABLE `rental`
  MODIFY `rentalID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
