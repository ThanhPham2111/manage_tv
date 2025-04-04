-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 04, 2025 lúc 08:51 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `tv`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `MaSP` varchar(50) NOT NULL,
  `MaLSP` varchar(50) NOT NULL,
  `TenSP` varchar(50) NOT NULL,
  `DonGia` int(255) NOT NULL,
  `SoLuong` int(255) UNSIGNED NOT NULL DEFAULT 1,
  `HinhAnh` varchar(100) NOT NULL,
  `TrangThai` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`MaSP`, `MaLSP`, `TenSP`, `DonGia`, `SoLuong`, `HinhAnh`, `TrangThai`) VALUES
('SP1', 'LSP1', 'TIVI Den', 5000000, 100, 'tv1.png', 0),
('SP2', 'LSP1', 'TIVI Den', 5000000, 50, 'tv1.png', 0),
('SP3', 'LSP1', 'TIVI Den', 5000000, 0, 'tv1.png', 0),
('SP4', 'LSP1', 'TIVI Den', 5000000, 0, 'tv1.png', 0),
('SP5', 'LSP1', 'TIVI Den', 5000000, 200, 'tv1.png', 0),
('SP6', 'LSP1', 'TIVI Den', 5000000, 0, 'tv1.png', 0),
('SP7', 'LSP1', 'TIVI Den', 5000000, 0, 'tv1.png', 0),
('TV001', 'LSP005', 'Tivi Samsung 43 inch 4K UHD', 8000000, 30, 'tv1.png', 1),
('TV002', 'LSP005', 'Tivi LG 55 inch OLED', 15000000, 20, 'tv1.png', 1),
('TV003', 'LSP005', 'Tivi Sony 50 inch Smart TV', 10000000, 25, 'tv1.png', 1),
('TV004', 'LSP005', 'Tivi Panasonic 40 inch HD', 6000000, 40, 'tv1.png', 1),
('TV005', 'LSP005', 'Tivi TCL 65 inch 4K', 12000000, 15, 'tv1.png', 1),
('TV006', 'LSP005', 'Tivi Samsung 55 inch QLED', 18000000, 18, 'tv1.png', 1),
('TV007', 'LSP005', 'Tivi LG 48 inch UHD', 9000000, 35, 'tv1.png', 1),
('TV008', 'LSP005', 'Tivi Sony 43 inch Bravia', 8500000, 28, 'tv1.png', 1),
('TV009', 'LSP005', 'Tivi Philips 50 inch Ambilight', 11000000, 22, 'tv1.png', 1),
('TV010', 'LSP005', 'Tivi Toshiba 55 inch 4K', 13000000, 20, 'tv1.png', 1),
('TV011', 'LSP005', 'Tivi Samsung 65 inch 8K', 25000000, 10, 'tv1.png', 1),
('TV012', 'LSP005', 'Tivi LG 60 inch OLED', 20000000, 15, 'tv1.png', 1),
('TV013', 'LSP005', 'Tivi Sony 55 inch 4K HDR', 14000000, 25, 'tv1.png', 1),
('TV014', 'LSP005', 'Tivi Panasonic 49 inch Smart', 7000000, 30, 'tv1.png', 1),
('TV015', 'LSP005', 'Tivi TCL 43 inch Full HD', 6500000, 45, 'tv1.png', 1),
('TV016', 'LSP005', 'Tivi Samsung 48 inch QLED', 9500000, 28, 'tv1.png', 1),
('TV017', 'LSP005', 'Tivi LG 65 inch NanoCell', 17000000, 12, 'tv1.png', 1),
('TV018', 'LSP005', 'Tivi Sony 60 inch 4K', 16000000, 18, 'tv1.png', 1),
('TV019', 'LSP005', 'Tivi Philips 55 inch 4K', 13000000, 20, 'tv1.png', 1),
('TV020', 'LSP005', 'Tivi Toshiba 50 inch Smart', 9000000, 25, 'tv1.png', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `userEmail` varchar(150) NOT NULL COMMENT 'login = userEmail',
  `userPassword` varchar(50) NOT NULL,
  `userFullName` varchar(50) NOT NULL,
  `isAdmin` tinyint(10) NOT NULL COMMENT '1: admin; 0: user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`userID`, `userName`, `userEmail`, `userPassword`, `userFullName`, `isAdmin`) VALUES
(1, 'admin', 'admin@gmail.com', '123', 'admin', 1),
(3, 'thanh', 'thanh@gmail.com', '123456', 'pham thanh', 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`MaSP`),
  ADD KEY `MaLSP` (`MaLSP`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
