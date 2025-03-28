-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 17, 2025 lúc 05:23 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `tracnghiem`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `answers`
--

CREATE TABLE `answers` (
  `awID` int(11) NOT NULL,
  `qID` int(11) NOT NULL COMMENT 'id câu hỏi',
  `awContent` text NOT NULL,
  `awPictures` text NOT NULL COMMENT 'url ảnh',
  `isRight` tinyint(4) NOT NULL COMMENT '1: đúng; 0: Sai',
  `awStatus` tinyint(4) NOT NULL COMMENT '1: active; 0: hidden'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `answers`
--

INSERT INTO `answers` (`awID`, `qID`, `awContent`, `awPictures`, `isRight`, `awStatus`) VALUES
(1, 384, '12', '', 1, 1),
(2, 384, '10', '', 0, 1),
(3, 384, '11', '', 0, 1),
(4, 384, '13', '', 0, 1),
(5, 385, '4', '', 1, 1),
(6, 385, '3', '', 0, 1),
(7, 385, '5', '', 0, 1),
(8, 385, '6', '', 0, 1),
(9, 386, '16', '', 1, 1),
(10, 386, '12', '', 0, 1),
(11, 386, '20', '', 0, 1),
(12, 386, '8', '', 0, 1),
(13, 387, '10', '', 1, 1),
(14, 387, '15', '', 0, 1),
(15, 387, '5', '', 0, 1),
(16, 387, '20', '', 0, 1),
(17, 388, '5 or -5', '', 1, 1),
(18, 388, '5', '', 0, 1),
(19, 388, '-5', '', 0, 1),
(20, 388, '25', '', 0, 1),
(21, 389, '5', '', 0, 1),
(22, 389, '4', '', 0, 1),
(23, 389, '6', '', 1, 1),
(24, 389, '3', '', 0, 1),
(25, 390, '4', '', 1, 1),
(26, 390, '5', '', 0, 1),
(27, 390, '3', '', 0, 1),
(28, 390, '6', '', 0, 1),
(29, 391, '16', '', 1, 1),
(30, 391, '15', '', 0, 1),
(31, 391, '18', '', 0, 1),
(32, 391, '20', '', 0, 1),
(33, 392, '24', '', 1, 1),
(34, 392, '20', '', 0, 1),
(35, 392, '22', '', 0, 1),
(36, 392, '26', '', 0, 1),
(37, 393, '8', '', 1, 1),
(38, 393, '6', '', 0, 1),
(39, 393, '9', '', 0, 1),
(40, 393, '7', '', 0, 1),
(41, 394, '5', '', 1, 1),
(42, 394, '4', '', 0, 1),
(43, 394, '6', '', 0, 1),
(44, 394, '3', '', 0, 1),
(45, 395, '8', '', 1, 1),
(46, 395, '6', '', 0, 1),
(47, 395, '10', '', 0, 1),
(48, 395, '4', '', 0, 1),
(49, 396, '8', '', 1, 1),
(50, 396, '6', '', 0, 1),
(51, 396, '10', '', 0, 1),
(52, 396, '12', '', 0, 1),
(53, 397, '16', '', 1, 1),
(54, 397, '14', '', 0, 1),
(55, 397, '15', '', 0, 1),
(56, 397, '17', '', 0, 1),
(57, 398, '7', '', 1, 1),
(58, 398, '6', '', 0, 1),
(59, 398, '8', '', 0, 1),
(60, 398, '5', '', 0, 1),
(61, 399, '5', '', 1, 1),
(62, 399, '4', '', 0, 1),
(63, 399, '6', '', 0, 1),
(64, 399, '3', '', 0, 1),
(65, 400, '20', '', 1, 1),
(66, 400, '25', '', 0, 1),
(67, 400, '15', '', 0, 1),
(68, 400, '10', '', 0, 1),
(69, 401, '12', '', 1, 1),
(70, 401, '10', '', 0, 1),
(71, 401, '14', '', 0, 1),
(72, 401, '8', '', 0, 1),
(73, 402, '24', '', 1, 1),
(74, 402, '20', '', 0, 1),
(75, 402, '22', '', 0, 1),
(76, 402, '26', '', 0, 1),
(77, 403, '20', '', 1, 1),
(78, 403, '25', '', 0, 1),
(79, 403, '15', '', 0, 1),
(80, 403, '10', '', 0, 1),
(81, 404, '5', '', 1, 1),
(82, 404, '6', '', 0, 1),
(83, 404, '4', '', 0, 1),
(84, 404, '7', '', 0, 1),
(85, 405, '18.84', '', 1, 1),
(86, 405, '12.56', '', 0, 1),
(87, 405, '15.70', '', 0, 1),
(88, 405, '9.42', '', 0, 1),
(89, 406, '24', '', 1, 1),
(90, 406, '22', '', 0, 1),
(91, 406, '23', '', 0, 1),
(92, 406, '25', '', 0, 1),
(93, 407, '9', '', 1, 1),
(94, 407, '8', '', 0, 1),
(95, 407, '10', '', 0, 1),
(96, 407, '7', '', 0, 1),
(97, 408, '42', '', 1, 1),
(98, 408, '36', '', 0, 1),
(99, 408, '40', '', 0, 1),
(100, 408, '48', '', 0, 1),
(101, 409, '10', '', 1, 1),
(102, 409, '9', '', 0, 1),
(103, 409, '11', '', 0, 1),
(104, 409, '8', '', 0, 1),
(105, 410, '27', '', 1, 1),
(106, 410, '30', '', 0, 1),
(107, 410, '25', '', 0, 1),
(108, 410, '20', '', 0, 1),
(109, 411, '9', '', 1, 1),
(110, 411, '8', '', 0, 1),
(111, 411, '10', '', 0, 1),
(112, 411, '7', '', 0, 1),
(113, 412, '7', '', 1, 1),
(114, 412, '6', '', 0, 1),
(115, 412, '8', '', 0, 1),
(116, 412, '5', '', 0, 1),
(117, 413, '9', '', 1, 1),
(118, 413, '8', '', 0, 1),
(119, 413, '10', '', 0, 1),
(120, 413, '7', '', 0, 1),
(121, 414, '13', '', 1, 1),
(122, 414, '12', '', 0, 1),
(123, 414, '14', '', 0, 1),
(124, 414, '15', '', 0, 1),
(125, 415, '9', '', 1, 1),
(126, 415, '8', '', 0, 1),
(127, 415, '10', '', 0, 1),
(128, 415, '7', '', 0, 1),
(129, 416, '78.5', '', 1, 1),
(130, 416, '75.36', '', 0, 1),
(131, 416, '84.78', '', 0, 1),
(132, 416, '62.8', '', 0, 1),
(133, 417, '27', '', 1, 1),
(134, 417, '24', '', 0, 1),
(135, 417, '30', '', 0, 1),
(136, 417, '21', '', 0, 1),
(137, 418, '6', '', 1, 1),
(138, 418, '5', '', 0, 1),
(139, 418, '7', '', 0, 1),
(140, 418, '4', '', 0, 1),
(141, 419, '5', '', 1, 1),
(142, 419, '4', '', 0, 1),
(143, 419, '6', '', 0, 1),
(144, 419, '8', '', 0, 1),
(145, 420, '20', '', 1, 1),
(146, 420, '25', '', 0, 1),
(147, 420, '15', '', 0, 1),
(148, 420, '10', '', 0, 1),
(149, 421, '24', '', 1, 1),
(150, 421, '20', '', 0, 1),
(151, 421, '28', '', 0, 1),
(152, 421, '16', '', 0, 1),
(153, 422, '15', '', 1, 1),
(154, 422, '12', '', 0, 1),
(155, 422, '10', '', 0, 1),
(156, 422, '18', '', 0, 1),
(161, 424, 'ran', '', 1, 1),
(162, 424, 'run', '', 0, 1),
(163, 424, 'running', '', 0, 1),
(164, 424, 'runs', '', 0, 1),
(165, 425, 'goes', '', 1, 1),
(166, 425, 'go', '', 0, 1),
(167, 425, 'went', '', 0, 1),
(168, 425, 'going', '', 0, 1),
(169, 426, 'unhappy', '', 1, 1),
(170, 426, 'happy', '', 0, 1),
(171, 426, 'angry', '', 0, 1),
(172, 426, 'excited', '', 0, 1),
(173, 427, 'She is reading a book.', '', 1, 1),
(174, 427, 'She are reading.', '', 0, 1),
(175, 427, 'She read yesterday ago.', '', 0, 1),
(176, 427, 'She reading book.', '', 0, 1),
(177, 428, 'joyful', '', 1, 1),
(178, 428, 'sad', '', 0, 1),
(179, 428, 'angry', '', 0, 1),
(180, 428, 'tired', '', 0, 1),
(181, 429, 'cats', '', 1, 1),
(182, 429, 'cat', '', 0, 1),
(183, 429, 'cates', '', 0, 1),
(184, 429, 'catz', '', 0, 1),
(185, 430, 'is', '', 1, 1),
(186, 430, 'are', '', 0, 1),
(187, 430, 'was', '', 0, 1),
(188, 430, 'were', '', 0, 1),
(189, 431, 'large', '', 1, 1),
(190, 431, 'small', '', 0, 1),
(191, 431, 'tiny', '', 0, 1),
(192, 431, 'short', '', 0, 1),
(193, 432, 'am', '', 1, 1),
(194, 432, 'is', '', 0, 1),
(195, 432, 'are', '', 0, 1),
(196, 432, 'be', '', 0, 1),
(197, 433, 'cold', '', 1, 1),
(198, 433, 'hot', '', 0, 1),
(199, 433, 'warm', '', 0, 1),
(200, 433, 'cool', '', 0, 1),
(201, 434, 'ate', '', 1, 1),
(202, 434, 'eat', '', 0, 1),
(203, 434, 'eaten', '', 0, 1),
(204, 434, 'eating', '', 0, 1),
(205, 435, 'went', '', 1, 1),
(206, 435, 'go', '', 0, 1),
(207, 435, 'goes', '', 0, 1),
(208, 435, 'going', '', 0, 1),
(209, 436, 'quick', '', 1, 1),
(210, 436, 'slow', '', 0, 1),
(211, 436, 'late', '', 0, 1),
(212, 436, 'easy', '', 0, 1),
(213, 437, 'He plays football.', '', 1, 1),
(214, 437, 'He play football.', '', 0, 1),
(215, 437, 'He playing football.', '', 0, 1),
(216, 437, 'He are playing.', '', 0, 1),
(217, 438, 'little', '', 1, 1),
(218, 438, 'big', '', 0, 1),
(219, 438, 'tall', '', 0, 1),
(220, 438, 'large', '', 0, 1),
(221, 439, 'children', '', 1, 1),
(222, 439, 'childs', '', 0, 1),
(223, 439, 'child', '', 0, 1),
(224, 439, 'childes', '', 0, 1),
(225, 440, 'is reading', '', 1, 1),
(226, 440, 'read', '', 0, 1),
(227, 440, 'reads', '', 0, 1),
(228, 440, 'reading', '', 0, 1),
(229, 441, 'positive', '', 1, 1),
(230, 441, 'negative', '', 0, 1),
(231, 441, 'bad', '', 0, 1),
(232, 441, 'sad', '', 0, 1),
(233, 442, 'are', '', 1, 1),
(234, 442, 'is', '', 0, 1),
(235, 442, 'am', '', 0, 1),
(236, 442, 'be', '', 0, 1),
(237, 443, 'short', '', 1, 1),
(238, 443, 'tall', '', 0, 1),
(239, 443, 'long', '', 0, 1),
(240, 443, 'high', '', 0, 1),
(241, 444, 'saw', '', 1, 1),
(242, 444, 'see', '', 0, 1),
(243, 444, 'seen', '', 0, 1),
(244, 444, 'seeing', '', 0, 1),
(245, 445, 'went', '', 1, 1),
(246, 445, 'go', '', 0, 1),
(247, 445, 'goes', '', 0, 1),
(248, 445, 'going', '', 0, 1),
(249, 446, 'not fast', '', 1, 1),
(250, 446, 'quick', '', 0, 1),
(251, 446, 'fast', '', 0, 1),
(252, 446, 'rapid', '', 0, 1),
(253, 447, 'They are playing.', '', 1, 1),
(254, 447, 'They is playing.', '', 0, 1),
(255, 447, 'They plays.', '', 0, 1),
(256, 447, 'They playing.', '', 0, 1),
(257, 448, 'quick', '', 1, 1),
(258, 448, 'slow', '', 0, 1),
(259, 448, 'late', '', 0, 1),
(260, 448, 'easy', '', 0, 1),
(261, 449, 'dogs', '', 1, 1),
(262, 449, 'dog', '', 0, 1),
(263, 449, 'doges', '', 0, 1),
(264, 449, 'dogz', '', 0, 1),
(265, 450, 'are', '', 1, 1),
(266, 450, 'is', '', 0, 1),
(267, 450, 'was', '', 0, 1),
(268, 450, 'were', '', 0, 1),
(269, 451, 'not good', '', 1, 1),
(270, 451, 'good', '', 0, 1),
(271, 451, 'great', '', 0, 1),
(272, 451, 'fine', '', 0, 1),
(273, 452, 'is', '', 1, 1),
(274, 452, 'am', '', 0, 1),
(275, 452, 'are', '', 0, 1),
(276, 452, 'be', '', 0, 1),
(277, 453, 'small', '', 1, 1),
(278, 453, 'big', '', 0, 1),
(279, 453, 'large', '', 0, 1),
(280, 453, 'tall', '', 0, 1),
(281, 454, 'drank', '', 1, 1),
(282, 454, 'drink', '', 0, 1),
(283, 454, 'drunk', '', 0, 1),
(284, 454, 'drinking', '', 0, 1),
(285, 455, 'go', '', 1, 1),
(286, 455, 'went', '', 0, 1),
(287, 455, 'goes', '', 0, 1),
(288, 455, 'going', '', 0, 1),
(289, 456, 'high', '', 1, 1),
(290, 456, 'short', '', 0, 1),
(291, 456, 'small', '', 0, 1),
(292, 456, 'low', '', 0, 1),
(293, 457, 'I am studying.', '', 1, 1),
(294, 457, 'I is studying.', '', 0, 1),
(295, 457, 'I studies.', '', 0, 1),
(296, 457, 'I studying.', '', 0, 1),
(297, 458, 'great', '', 1, 1),
(298, 458, 'bad', '', 0, 1),
(299, 458, 'poor', '', 0, 1),
(300, 458, 'sad', '', 0, 1),
(301, 459, 'books', '', 1, 1),
(302, 459, 'book', '', 0, 1),
(303, 459, 'bookes', '', 0, 1),
(304, 459, 'bookz', '', 0, 1),
(305, 460, 'is', '', 1, 1),
(306, 460, 'are', '', 0, 1),
(307, 460, 'was', '', 0, 1),
(308, 460, 'were', '', 0, 1),
(309, 461, 'little', '', 1, 1),
(310, 461, 'big', '', 0, 1),
(311, 461, 'tall', '', 0, 1),
(312, 461, 'large', '', 0, 1),
(313, 462, 'are', '', 1, 1),
(314, 462, 'is', '', 0, 1),
(315, 462, 'am', '', 0, 1),
(316, 462, 'be', '', 0, 1),
(317, 463, 'slow', '', 1, 1),
(318, 463, 'fast', '', 0, 1),
(319, 463, 'quick', '', 0, 1),
(320, 463, 'rapid', '', 0, 1),
(321, 464, 'George Washington', '', 1, 1),
(322, 464, 'Abraham Lincoln', '', 0, 1),
(323, 464, 'Thomas Jefferson', '', 0, 1),
(324, 464, 'John Adams', '', 0, 1),
(325, 465, '1914', '', 1, 1),
(326, 465, '1918', '', 0, 1),
(327, 465, '1939', '', 0, 1),
(328, 465, '1945', '', 0, 1),
(329, 466, 'Memphis', '', 1, 1),
(330, 466, 'Cairo', '', 0, 1),
(331, 466, 'Thebes', '', 0, 1),
(332, 466, 'Alexandria', '', 0, 1),
(333, 467, 'Christopher Columbus', '', 1, 1),
(334, 467, 'Marco Polo', '', 0, 1),
(335, 467, 'Vasco da Gama', '', 0, 1),
(336, 467, 'Ferdinand Magellan', '', 0, 1),
(337, 468, 'Japan’s surrender', '', 1, 1),
(338, 468, 'Germany’s invasion', '', 0, 1),
(339, 468, 'Treaty of Versailles', '', 0, 1),
(340, 468, 'Battle of Britain', '', 0, 1),
(341, 469, 'Adolf Hitler', '', 1, 1),
(342, 469, 'Joseph Stalin', '', 0, 1),
(343, 469, 'Benito Mussolini', '', 0, 1),
(344, 469, 'Winston Churchill', '', 0, 1),
(345, 470, '1789', '', 1, 1),
(346, 470, '1776', '', 0, 1),
(347, 470, '1812', '', 0, 1),
(348, 470, '1799', '', 0, 1),
(349, 471, 'Rome', '', 1, 1),
(350, 471, 'Athens', '', 0, 1),
(351, 471, 'Constantinople', '', 0, 1),
(352, 471, 'Alexandria', '', 0, 1),
(353, 472, 'Qin Shi Huang', '', 1, 1),
(354, 472, 'Han Wudi', '', 0, 1),
(355, 472, 'Tang Taizong', '', 0, 1),
(356, 472, 'Wu Zetian', '', 0, 1),
(357, 473, '1912', '', 1, 1),
(358, 473, '1910', '', 0, 1),
(359, 473, '1914', '', 0, 1),
(360, 473, '1908', '', 0, 1),
(361, 474, 'Thomas Jefferson', '', 1, 1),
(362, 474, 'George Washington', '', 0, 1),
(363, 474, 'John Adams', '', 0, 1),
(364, 474, 'Benjamin Franklin', '', 0, 1),
(365, 475, '1969', '', 1, 1),
(366, 475, '1970', '', 0, 1),
(367, 475, '1965', '', 0, 1),
(368, 475, '1972', '', 0, 1),
(369, 476, 'Slavery', '', 1, 1),
(370, 476, 'Taxes', '', 0, 1),
(371, 476, 'Land disputes', '', 0, 1),
(372, 476, 'Religion', '', 0, 1),
(373, 477, 'Elizabeth II', '', 1, 1),
(374, 477, 'Victoria', '', 0, 1),
(375, 477, 'Mary I', '', 0, 1),
(376, 477, 'Anne', '', 0, 1),
(377, 478, '1989', '', 1, 1),
(378, 478, '1990', '', 0, 1),
(379, 478, '1985', '', 0, 1),
(380, 478, '1991', '', 0, 1),
(381, 479, 'Genghis Khan', '', 1, 1),
(382, 479, 'Kublai Khan', '', 0, 1),
(383, 479, 'Tamerlane', '', 0, 1),
(384, 479, 'Ogedei Khan', '', 0, 1),
(385, 480, '1917', '', 1, 1),
(386, 480, '1914', '', 0, 1),
(387, 480, '1920', '', 0, 1),
(388, 480, '1910', '', 0, 1),
(389, 481, 'Egyptians', '', 1, 1),
(390, 481, 'Greeks', '', 0, 1),
(391, 481, 'Romans', '', 0, 1),
(392, 481, 'Mesopotamians', '', 0, 1),
(393, 482, 'Abraham Lincoln', '', 1, 1),
(394, 482, 'George Washington', '', 0, 1),
(395, 482, 'Ulysses S. Grant', '', 0, 1),
(396, 482, 'Andrew Johnson', '', 0, 1),
(397, 483, '1492', '', 1, 1),
(398, 483, '1498', '', 0, 1),
(399, 483, '1488', '', 0, 1),
(400, 483, '1500', '', 0, 1),
(401, 484, 'Latin', '', 1, 1),
(402, 484, 'Greek', '', 0, 1),
(403, 484, 'Italian', '', 0, 1),
(404, 484, 'Spanish', '', 0, 1),
(405, 485, 'Vladimir Lenin', '', 1, 1),
(406, 485, 'Joseph Stalin', '', 0, 1),
(407, 485, 'Leon Trotsky', '', 0, 1),
(408, 485, 'Nicholas II', '', 0, 1),
(409, 486, '1939', '', 1, 1),
(410, 486, '1941', '', 0, 1),
(411, 486, '1935', '', 0, 1),
(412, 486, '1945', '', 0, 1),
(413, 487, 'Inca', '', 1, 1),
(414, 487, 'Maya', '', 0, 1),
(415, 487, 'Aztec', '', 0, 1),
(416, 487, 'Olmec', '', 0, 1),
(417, 488, 'Margaret Thatcher', '', 1, 1),
(418, 488, 'Theresa May', '', 0, 1),
(419, 488, 'Elizabeth I', '', 0, 1),
(420, 488, 'Victoria', '', 0, 1),
(421, 489, '1775', '', 1, 1),
(422, 489, '1776', '', 0, 1),
(423, 489, '1789', '', 0, 1),
(424, 489, '1765', '', 0, 1),
(425, 490, 'Joseph Stalin', '', 1, 1),
(426, 490, 'Vladimir Lenin', '', 0, 1),
(427, 490, 'Nikita Khrushchev', '', 0, 1),
(428, 490, 'Leon Trotsky', '', 0, 1),
(429, 491, 'Mayflower', '', 1, 1),
(430, 491, 'Santa Maria', '', 0, 1),
(431, 491, 'Nina', '', 0, 1),
(432, 491, 'Pinta', '', 0, 1),
(433, 492, '1947', '', 1, 1),
(434, 492, '1945', '', 0, 1),
(435, 492, '1950', '', 0, 1),
(436, 492, '1939', '', 0, 1),
(437, 493, 'Augustus', '', 1, 1),
(438, 493, 'Nero', '', 0, 1),
(439, 493, 'Julius Caesar', '', 0, 1),
(440, 493, 'Constantine', '', 0, 1),
(441, 494, '1588', '', 1, 1),
(442, 494, '1492', '', 0, 1),
(443, 494, '1600', '', 0, 1),
(444, 494, '1575', '', 0, 1),
(445, 495, 'Johannes Gutenberg', '', 1, 1),
(446, 495, 'Leonardo da Vinci', '', 0, 1),
(447, 495, 'Galileo Galilei', '', 0, 1),
(448, 495, 'Isaac Newton', '', 0, 1),
(449, 496, '1929', '', 1, 1),
(450, 496, '1930', '', 0, 1),
(451, 496, '1925', '', 0, 1),
(452, 496, '1935', '', 0, 1),
(453, 497, 'Romans', '', 1, 1),
(454, 497, 'Greeks', '', 0, 1),
(455, 497, 'Egyptians', '', 0, 1),
(456, 497, 'Persians', '', 0, 1),
(457, 498, 'Woodrow Wilson', '', 1, 1),
(458, 498, 'Theodore Roosevelt', '', 0, 1),
(459, 498, 'Franklin D. Roosevelt', '', 0, 1),
(460, 498, 'William Taft', '', 0, 1),
(461, 499, '1994', '', 1, 1),
(462, 499, '1990', '', 0, 1),
(463, 499, '1989', '', 0, 1),
(464, 499, '1996', '', 0, 1),
(465, 500, 'Louis XVI', '', 1, 1),
(466, 500, 'Napoleon Bonaparte', '', 0, 1),
(467, 500, 'Robespierre', '', 0, 1),
(468, 500, 'Marie Antoinette', '', 0, 1),
(469, 501, '1975', '', 1, 1),
(470, 501, '1970', '', 0, 1),
(471, 501, '1965', '', 0, 1),
(472, 501, '1980', '', 0, 1),
(473, 502, 'Moors', '', 1, 1),
(474, 502, 'Romans', '', 0, 1),
(475, 502, 'Greeks', '', 0, 1),
(476, 502, 'Visigoths', '', 0, 1),
(477, 503, 'Ferdinand Magellan', '', 1, 1),
(478, 503, 'Christopher Columbus', '', 0, 1),
(479, 503, 'Vasco da Gama', '', 0, 1),
(480, 503, 'Marco Polo', '', 0, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `exams`
--

CREATE TABLE `exams` (
  `testCode` varchar(20) NOT NULL,
  `exOrder` varchar(1) NOT NULL COMMENT 'A;B;C;D;E;F',
  `exCode` varchar(20) NOT NULL COMMENT '=testCode + exOrder',
  `ex_quesIDs` text NOT NULL COMMENT 'mảng các id câu hỏi'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `exams`
--

INSERT INTO `exams` (`testCode`, `exOrder`, `exCode`, `ex_quesIDs`) VALUES
('ENG01', 'C', 'ENG01C', '445,446,447,448,449,450,451,452,453,454'),
('ENG010', 'A', 'ENG010A', '[431, 446, 461, 442, 439]'),
('ENG02', 'A', 'ENG02A', '424,427,430,433,436,439,442,445,448,451'),
('ENG02', 'B', 'ENG02B', '426,429,432,435,438,441,444,447,450,453'),
('ENG02', 'C', 'ENG02C', '434,437,440,443,446,449,452,455,458,461'),
('ENG03', 'A', 'ENG03A', '424,428,431,434,437,440,443,446,449,452'),
('ENG03', 'B', 'ENG03B', '426,430,433,436,439,442,445,448,451,454'),
('ENG03', 'C', 'ENG03C', '429,432,435,438,441,444,447,450,453,456'),
('HIST01', 'A', 'HIST01A', '464,467,473,475,483,465,470,476,479,493'),
('HIST01', 'B', 'HIST01B', '464,468,471,474,477,480,482,485,488,491'),
('HIST01', 'C', 'HIST01C', '466,469,472,475,478,FFFFFF481,484,487,490,493'),
('HIST02', 'A', 'HIST02A', '464,467,470,473,476,479,482,485,488,491'),
('HIST02', 'B', 'HIST02B', '465,468,471,474,477,480,483,486,489,492'),
('HIST02', 'C', 'HIST02C', '466,469,472,475,478,481,484,487,490,494'),
('HIST03', 'A', 'HIST03A', '464,467,470,473,476,479,482,485,489,495'),
('HIST03', 'B', 'HIST03B', '465,468,471,474,477,480,483,486,490,496'),
('HIST03', 'C', 'HIST03C', '466,469,472,475,478,481,484,487,491,497'),
('MATH01', 'A', 'MATH01A', '384,386,389,391,394,397,400,402,405,408'),
('MATH01', 'B', 'MATH01B', '385,388,390,393,396,399,401,404,407,410'),
('MATH01', 'C', 'MATH01C', '387,392,395,398,403,406,409,411,414,417'),
('MATH02', 'A', 'MATH02A', '384,387,390,393,396,399,402,405,408,411'),
('MATH02', 'B', 'MATH02B', '385,388,391,394,397,400,403,406,409,412'),
('MATH02', 'C', 'MATH02C', '386,389,392,395,398,401,404,407,410,413'),
('MATH03', 'A', 'MATH03A', '384,386,388,390,392,394,396,398,400,402'),
('MATH03', 'B', 'MATH03B', '385,387,389,391,393,395,397,399,401,403'),
('MATH03', 'C', 'MATH03C', '404,405,406,407,408,409,410,411,412,413'),
('MATH10000', 'F', 'MATH10000F', '407, 406, 391, 409, 395, 411, 385, 386, 387'),
('MATH11111', 'A', 'MATH11111A', '410, 394, 391, 413, 401, 385, 387, 386'),
('MATH11111111111', 'B', 'MATH11111111111B', '419, 398, 420, 393, 396, 401, 411, 385, 387, 386');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `logs`
--

CREATE TABLE `logs` (
  `logID` int(11) NOT NULL,
  `logContent` text NOT NULL,
  `logUserID` int(11) NOT NULL,
  `logExCode` varchar(20) NOT NULL,
  `logDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `questions`
--

CREATE TABLE `questions` (
  `qID` int(11) NOT NULL,
  `qContent` text NOT NULL COMMENT 'nội dung câu hỏi',
  `qPictures` text NOT NULL COMMENT 'url hình đính kèm',
  `qTopicID` int(11) NOT NULL,
  `qLevel` varchar(10) NOT NULL COMMENT 'easy, meidum, diff',
  `qStatus` tinyint(4) NOT NULL COMMENT '1: active; 0: hidden'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `questions`
--

INSERT INTO `questions` (`qID`, `qContent`, `qPictures`, `qTopicID`, `qLevel`, `qStatus`) VALUES
(384, 'What is 5 + 7?', '', 1, 'Easy', 1),
(385, 'Solve: 3x = 12', '', 1, 'Diff', 1),
(386, 'What is the area of a square with side 4?', '', 1, 'Diff', 1),
(387, 'What is 20% of 50?', '', 1, 'Diff', 1),
(388, 'If x² = 25, what is x?', '', 1, 'Medium', 1),
(389, 'What is 9 - 4?', '', 1, 'Dễ', 1),
(390, 'Solve: 2x + 3 = 11', '', 1, 'medium', 1),
(391, 'What is the perimeter of a rectangle with length 5 and width 3?', '', 1, 'easy', 1),
(392, 'What is 3 × 8?', '', 1, 'easy', 1),
(393, 'What is the square root of 64?', '', 1, 'medium', 1),
(394, 'What is 15 ÷ 3?', '', 1, 'easy', 1),
(395, 'Solve: x/4 = 2', '', 1, 'medium', 1),
(396, 'What is the volume of a cube with side 2?', '', 1, 'medium', 1),
(397, 'What is 7 + 9?', '', 1, 'easy', 1),
(398, 'What is 12 - 5?', '', 1, 'easy', 1),
(399, 'Solve: 5x - 10 = 15', '', 1, 'Dễ', 1),
(400, 'What is 25% of 80?', '', 1, 'easy', 1),
(401, 'What is the area of a triangle with base 6 and height 4?', '', 1, 'medium', 1),
(402, 'What is 4 × 6?', '', 1, 'easy', 1),
(403, 'What is 100 ÷ 5?', '', 1, 'easy', 1),
(404, 'Solve: 3x + 4 = 19', '', 1, 'medium', 1),
(405, 'What is the circumference of a circle with radius 3? (Use π = 3.14)', '', 1, 'medium', 1),
(406, 'What is 11 + 13?', '', 1, 'easy', 1),
(407, 'What is 18 - 9?', '', 1, 'easy', 1),
(408, 'What is 6 × 7?', '', 1, 'easy', 1),
(409, 'Solve: x - 7 = 3', '', 1, 'medium', 1),
(410, 'What is 30% of 90?', '', 1, 'easy', 1),
(411, 'What is the square root of 81?', '', 1, 'medium', 1),
(412, 'What is 14 ÷ 2?', '', 1, 'easy', 1),
(413, 'Solve: 4x = 36', '', 1, 'medium', 1),
(414, 'What is 8 + 5?', '', 1, 'easy', 1),
(415, 'What is 15 - 6?', '', 1, 'easy', 1),
(416, 'What is the area of a circle with radius 5? (Use π = 3.14)', '', 1, 'medium', 1),
(417, 'What is 9 × 3?', '', 1, 'Trung bình', 1),
(418, 'Solve: 2x - 5 = 7', '', 1, 'Khó', 1),
(419, 'What is 40 ÷ 8?', '', 1, 'easy', 1),
(420, 'What is 10% of 200?', '', 1, 'easy', 1),
(421, 'What is the perimeter of a square with side 6?', '', 1, 'Trung bình', 1),
(422, 'Solve: x/5 = 3', '', 1, 'Trung bình', 1),
(424, 'What is the past tense of \"run\"?', '', 2, 'easy', 1),
(425, 'She ___ to the park every day.', '', 2, 'easy', 1),
(426, 'What does \"sad\" mean?', '', 2, 'easy', 1),
(427, 'Which sentence is correct?', '', 2, 'medium', 1),
(428, 'What is the synonym of \"happy\"?', '', 2, 'medium', 1),
(429, 'What is the plural of \"cat\"?', '', 2, 'easy', 1),
(430, 'He ___ playing football now.', '', 2, 'medium', 1),
(431, 'What does \"big\" mean?', '', 2, 'Diff', 1),
(432, 'Choose the correct word: I ___ a student.', '', 2, 'easy', 1),
(433, 'What is the opposite of \"hot\"?', '', 2, 'medium', 1),
(434, 'What is the past tense of \"eat\"?', '', 2, 'easy', 1),
(435, 'They ___ to school yesterday.', '', 2, 'medium', 1),
(436, 'What does \"fast\" mean?', '', 2, 'easy', 1),
(437, 'Which sentence is correct?', '', 2, 'medium', 1),
(438, 'What is the synonym of \"small\"?', '', 2, 'medium', 1),
(439, 'What is the plural of \"child\"?', '', 2, 'easy', 1),
(440, 'She ___ a book now.', '', 2, 'medium', 1),
(441, 'What does \"good\" mean?', '', 2, 'easy', 1),
(442, 'Choose the correct word: We ___ happy.', '', 2, 'easy', 1),
(443, 'What is the opposite of \"tall\"?', '', 2, 'medium', 1),
(444, 'What is the past tense of \"see\"?', '', 2, 'easy', 1),
(445, 'He ___ to the store last night.', '', 2, 'medium', 1),
(446, 'What does \"slow\" mean?', '', 2, 'easy', 1),
(447, 'Which sentence is correct?', '', 2, 'medium', 1),
(448, 'What is the synonym of \"fast\"?', '', 2, 'medium', 1),
(449, 'What is the plural of \"dog\"?', '', 2, 'easy', 1),
(450, 'They ___ watching TV now.', '', 2, 'medium', 1),
(451, 'What does \"bad\" mean?', '', 2, 'easy', 1),
(452, 'Choose the correct word: She ___ my friend.', '', 2, 'easy', 1),
(453, 'What is the opposite of \"big\"?', '', 2, 'medium', 1),
(454, 'What is the past tense of \"drink\"?', '', 2, 'easy', 1),
(455, 'I ___ to school every day.', '', 2, 'medium', 1),
(456, 'What does \"tall\" mean?', '', 2, 'easy', 1),
(457, 'Which sentence is correct?', '', 2, 'medium', 1),
(458, 'What is the synonym of \"good\"?', '', 2, 'medium', 1),
(459, 'What is the plural of \"book\"?', '', 2, 'easy', 1),
(460, 'He ___ running now.', '', 2, 'medium', 1),
(461, 'What does \"small\" mean?', '', 2, 'easy', 1),
(462, 'Choose the correct word: They ___ teachers.', '', 2, 'easy', 1),
(463, 'What is the opposite of \"fast\"?', '', 2, 'medium', 1),
(464, 'Who was the first U.S. President?', '', 3, 'easy', 1),
(465, 'In which year did World War I begin?', '', 3, 'medium', 1),
(466, 'What was the capital of Ancient Egypt?', '', 3, 'medium', 1),
(467, 'Who discovered America in 1492?', '', 3, 'easy', 1),
(468, 'What event ended World War II?', '', 3, 'medium', 1),
(469, 'Who was the leader of Nazi Germany?', '', 3, 'easy', 1),
(470, 'In which year did the French Revolution begin?', '', 3, 'medium', 1),
(471, 'What was the capital of the Roman Empire?', '', 3, 'medium', 1),
(472, 'Who was the first Emperor of China?', '', 3, 'medium', 1),
(473, 'What year did the Titanic sink?', '', 3, 'easy', 1),
(474, 'Who wrote the Declaration of Independence?', '', 3, 'medium', 1),
(475, 'In which year did humans first land on the moon?', '', 3, 'easy', 1),
(476, 'What was the main cause of the American Civil War?', '', 3, 'medium', 1),
(477, 'Who was the Queen of England during World War II?', '', 3, 'medium', 1),
(478, 'What year did the Berlin Wall fall?', '', 3, 'easy', 1),
(479, 'Who was the first ruler of the Mongol Empire?', '', 3, 'medium', 1),
(480, 'In which year did the Russian Revolution begin?', '', 3, 'medium', 1),
(481, 'What ancient civilization built the Pyramids of Giza?', '', 3, 'easy', 1),
(482, 'Who was the U.S. President during the Civil War?', '', 3, 'medium', 1),
(483, 'What year did Christopher Columbus sail to America?', '', 3, 'easy', 1),
(484, 'What was the primary language of the Roman Empire?', '', 3, 'medium', 1),
(485, 'Who led the Bolsheviks in the Russian Revolution?', '', 3, 'medium', 1),
(486, 'In which year did World War II begin?', '', 3, 'easy', 1),
(487, 'What civilization built Machu Picchu?', '', 3, 'medium', 1),
(488, 'Who was the first female Prime Minister of the UK?', '', 3, 'medium', 1),
(489, 'What year did the American Revolution begin?', '', 3, 'easy', 1),
(490, 'Who was the leader of the Soviet Union during World War II?', '', 3, 'medium', 1),
(491, 'What was the name of the ship that carried the Pilgrims to America?', '', 3, 'easy', 1),
(492, 'In which year did India gain independence from Britain?', '', 3, 'medium', 1),
(493, 'Who was the first Emperor of the Roman Empire?', '', 3, 'Diff', 1),
(494, 'What year did the Spanish Armada attempt to invade England?', '', 3, 'medium', 1),
(495, 'Who invented the printing press?', '', 3, 'easy', 1),
(496, 'In which year did the Great Depression begin?', '', 3, 'medium', 1),
(497, 'What ancient civilization built the Colosseum?', '', 3, 'easy', 1),
(498, 'Who was the U.S. President during World War I?', '', 3, 'medium', 1),
(499, 'What year did Nelson Mandela become President of South Africa?', '', 3, 'medium', 1),
(500, 'Who was the leader of France during the French Revolution?', '', 3, 'medium', 1),
(501, 'In which year did the Vietnam War end?', '', 3, 'easy', 1),
(502, 'What empire ruled Spain before the Reconquista?', '', 3, 'Diff', 1),
(503, 'Who was the first person to circumnavigate the globe?', '', 3, 'medium', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `result`
--

CREATE TABLE `result` (
  `rs_num` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `exCode` varchar(20) NOT NULL,
  `rs_anwsers` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'các đáp án đã chọn' CHECK (json_valid(`rs_anwsers`)),
  `rs_mark` decimal(10,0) NOT NULL,
  `rs_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `result`
--

INSERT INTO `result` (`rs_num`, `userID`, `exCode`, `rs_anwsers`, `rs_mark`, `rs_date`) VALUES
(28, 1, 'ENG01A', '{\"answers\": []}', 0, '2025-03-14 16:00:00'),
(28, 1, 'HIST01A', '{\"answers\": []}', 0, '2025-03-14 16:00:00'),
(28, 1, 'MATH01A', '{\"answers\": []}', 0, '2025-03-14 16:00:00'),
(29, 1, 'HIST01A', '{\"answers\": []}', 0, '2025-03-14 16:00:00'),
(30, 4, 'MATH01', '{\"q1\":\"?\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\"}', 0, '2025-03-14 00:00:00'),
(31, 4, 'HIST01', '{\"q1\":\"B\",\"q2\":\"D\",\"q3\":\"D\",\"q4\":\"A\",\"q5\":\"D\",\"q6\":\"D\",\"q7\":\"D\",\"q8\":\"C\"}', 1, '2025-03-14 00:00:00'),
(32, 4, 'MATH01', '{\"q1\":\"?\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\"}', 0, '2025-03-14 00:00:00'),
(33, 4, 'MATH01', '{\"q1\":\"?\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\"}', 0, '2025-03-14 00:00:00'),
(34, 4, 'HIST01', '{\"q1\":\"?\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\"}', 0, '2025-03-14 00:00:00'),
(35, 1, 'ENG01A', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(36, 1, 'ENG01B', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(37, 1, 'ENG01C', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(38, 1, 'ENG02A', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(39, 1, 'ENG02B', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(40, 1, 'ENG02C', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(41, 1, 'ENG03A', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(42, 1, 'ENG03B', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(43, 1, 'ENG03C', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(44, 1, 'MATH01A', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(45, 1, 'MATH01B', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(46, 1, 'MATH01C', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(47, 1, 'MATH02A', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(48, 1, 'MATH02B', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(49, 1, 'MATH02C', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(50, 1, 'MATH03A', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(51, 1, 'MATH03B', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(52, 1, 'MATH03C', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(53, 1, 'HIST01A', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(54, 1, 'HIST01B', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(55, 1, 'HIST01C', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(56, 1, 'HIST02A', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(57, 1, 'HIST02B', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(58, 1, 'HIST02C', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(59, 1, 'HIST03A', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(60, 1, 'HIST03B', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(61, 1, 'HIST03C', '{\"answers\": []}', 0, '2025-03-15 00:00:00'),
(62, 5, 'ENG03', '{\"q1\":\"?\",\"q2\":\"?\",\"q10\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\",\"q9\":\"?\"}', 0, '2025-03-15 00:00:00'),
(63, 5, 'MATH01', '{\"q1\":\"A\",\"q2\":\"A\",\"q3\":\"B\",\"q4\":\"A\",\"q5\":\"B\",\"q6\":\"A\",\"q7\":\"A\",\"q8\":\"A\"}', 8, '2025-03-16 00:00:00'),
(64, 5, 'MATH01', '{\"q1\":\"?\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\"}', 0, '2025-03-16 00:00:00'),
(65, 5, 'MATH01', '{\"q1\":\"A\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\"}', 1, '2025-03-16 00:00:00'),
(66, 5, 'MATH01', '{\"q1\":\"?\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\"}', 0, '2025-03-16 00:00:00'),
(67, 5, 'MATH01', '{\"q1\":\"A\",\"q2\":\"A\",\"q3\":\"A\",\"q4\":\"A\",\"q5\":\"A\",\"q6\":\"A\",\"q7\":\"A\",\"q8\":\"A\"}', 10, '2025-03-16 00:00:00'),
(68, 5, 'MATH01', '{\"q1\":\"A\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\"}', 1, '2025-03-16 00:00:00'),
(69, 5, 'MATH02', '{\"q1\":\"?\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\"}', 0, '2025-03-16 00:00:00'),
(70, 5, 'MATH01', '{\"q1\":\"?\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"D\",\"q5\":\"C\",\"q6\":\"D\",\"q7\":\"D\",\"q8\":\"D\"}', 0, '2025-03-16 00:00:00'),
(71, 5, 'MATH01', '{\"q1\":\"?\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\"}', 0, '2025-03-16 00:00:00'),
(72, 5, 'MATH01', '{\"q1\":\"C\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\"}', 0, '2025-03-16 00:00:00'),
(73, 5, 'MATH02', '{\"q1\":\"A\",\"q2\":\"A\",\"q3\":\"A\",\"q4\":\"B\",\"q5\":\"A\",\"q6\":\"B\",\"q7\":\"A\",\"q8\":\"A\"}', 8, '2025-03-16 00:00:00'),
(74, 5, 'HIST02', '{\"q1\":\"?\",\"q2\":\"?\",\"q10\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\",\"q6\":\"?\",\"q7\":\"?\",\"q8\":\"?\",\"q9\":\"?\"}', 0, '2025-03-16 00:00:00'),
(75, 6, 'MATH01', '{\"q1\":\"B\",\"q2\":\"B\",\"q3\":\"A\",\"q4\":\"A\",\"q5\":\"A\",\"q6\":\"A\",\"q7\":\"A\",\"q8\":\"D\"}', 6, '2025-03-16 00:00:00'),
(76, 6, 'MATH19', '{\"q1\":\"B\",\"q2\":\"?\",\"q3\":\"?\"}', 0, '2025-03-17 00:00:00'),
(77, 6, 'ENG010', '{\"q1\":\"?\",\"q2\":\"?\",\"q3\":\"?\",\"q4\":\"?\",\"q5\":\"?\"}', 0, '2025-03-17 00:00:00'),
(78, 6, 'MATH100', '{\"q1\":\"A\",\"q2\":\"A\",\"q3\":\"A\",\"q4\":\"A\",\"q5\":\"B\",\"q6\":\"A\",\"q7\":\"A\"}', 9, '2025-03-17 00:00:00'),
(79, 6, 'MATH012', '{\"q1\":\"A\",\"q2\":\"B\",\"q3\":\"C\",\"q4\":\"C\",\"q5\":\"B\",\"q6\":\"C\",\"q7\":\"D\",\"q8\":\"C\"}', 1, '2025-03-17 00:00:00'),
(80, 6, 'MATH1001', '{\"q1\":\"A\",\"q2\":\"B\",\"q3\":\"B\",\"q4\":\"A\",\"q5\":\"B\",\"q6\":\"B\",\"q7\":\"C\",\"q8\":\"C\",\"q9\":\"C\"}', 2, '2025-03-17 00:00:00'),
(81, 6, 'MATH111', '{\"q1\":\"B\",\"q2\":\"B\",\"q3\":\"B\",\"q4\":\"B\",\"q5\":\"B\",\"q6\":\"C\",\"q7\":\"C\",\"q8\":\"B\",\"q9\":\"B\"}', 0, '2025-03-17 00:00:00'),
(82, 6, 'MATH111', '{\"q1\":\"A\",\"q2\":\"A\",\"q3\":\"A\",\"q4\":\"A\",\"q5\":\"A\",\"q6\":\"B\",\"q7\":\"B\",\"q8\":\"B\",\"q9\":\"C\"}', 6, '2025-03-17 00:00:00'),
(83, 6, 'MATH10000', '{\"q1\":\"B\",\"q2\":\"B\",\"q3\":\"B\",\"q4\":\"B\",\"q5\":\"C\",\"q6\":\"C\"}', 0, '2025-03-17 00:00:00'),
(84, 6, 'MATH10000', '{\"q1\":\"C\",\"q2\":\"C\",\"q3\":\"C\",\"q4\":\"B\",\"q5\":\"?\",\"q6\":\"?\"}', 0, '2025-03-17 00:00:00'),
(85, 6, 'MATH1221', '{\"q1\":\"B\",\"q2\":\"C\",\"q3\":\"B\",\"q4\":\"B\",\"q5\":\"C\",\"q6\":\"C\",\"q7\":\"C\",\"q8\":\"C\",\"q9\":\"C\"}', 0, '2025-03-17 00:00:00'),
(86, 6, 'MATH1122', '{\"q1\":\"D\",\"q2\":\"B\",\"q3\":\"B\",\"q4\":\"B\",\"q5\":\"C\",\"q6\":\"C\",\"q7\":\"B\",\"q8\":\"B\",\"q9\":\"C\"}', 0, '2025-03-17 00:00:00'),
(87, 6, 'MATH10000', '{\"q1\":\"B\",\"q2\":\"B\",\"q3\":\"C\",\"q4\":\"B\",\"q5\":\"C\",\"q6\":\"C\",\"q7\":\"B\",\"q8\":\"C\",\"q9\":\"B\"}', 0, '2025-03-17 00:00:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test`
--

CREATE TABLE `test` (
  `testID` int(11) NOT NULL,
  `testCode` varchar(255) DEFAULT NULL,
  `testTitle` text NOT NULL,
  `testTime` int(11) NOT NULL COMMENT 'thời gian làm bài (phút)',
  `testLimit` tinyint(4) NOT NULL COMMENT 'số lần thi',
  `testDate` date NOT NULL,
  `testStatus` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `test`
--

INSERT INTO `test` (`testID`, `testCode`, `testTitle`, `testTime`, `testLimit`, `testDate`, `testStatus`) VALUES
(5, 'ENG01', 'English Practice Test 1', 15, 3, '2025-03-15', 1),
(6, 'ENG02', 'English Practice Test 2', 15, 1, '2025-03-15', 1),
(7, 'ENG03', 'English Practice Test 3', 15, 1, '2025-03-15', 1),
(8, 'HIST01', 'History Practice Test 1', 15, 1, '2025-03-15', 1),
(9, 'HIST02', 'History Practice Test 2', 15, 1, '2025-03-15', 1),
(10, 'HIST03', 'History Practice Test 3', 15, 1, '2025-03-15', 1),
(11, 'MATH01', 'Math Practice Test 1', 15, 1, '2025-03-15', 1),
(12, 'MATH02', 'Math Practice Test 2', 15, 1, '2025-03-15', 1),
(13, 'MATH03', 'Math Practice Test 3', 15, 1, '2025-03-15', 1),
(28, 'MATH10000', 'PAA', 10, 1, '1000-10-10', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test_structure`
--

CREATE TABLE `test_structure` (
  `testCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `tpID` int(11) NOT NULL,
  `numberEasy` int(11) NOT NULL,
  `numberMedium` int(11) NOT NULL,
  `numberDiff` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `test_structure`
--

INSERT INTO `test_structure` (`testCode`, `tpID`, `numberEasy`, `numberMedium`, `numberDiff`) VALUES
('ENG010', 2, 5, 0, 0),
('ENG02', 2, 5, 5, 0),
('ENG03', 2, 5, 5, 0),
('HIST02', 3, 5, 5, 0),
('HIST03', 3, 5, 5, 0),
('HIST10', 3, 1, 1, 1),
('MATH01', 1, 5, 3, 2),
('MATH012', 1, 4, 4, 4),
('MATH02', 1, 5, 3, 2),
('MATH03', 1, 5, 3, 2),
('MATH10000', 1, 3, 3, 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `topics`
--

CREATE TABLE `topics` (
  `tpID` int(11) NOT NULL,
  `tpTitle` text NOT NULL COMMENT 'tên topic',
  `tpParent` int(11) NOT NULL COMMENT 'id của topic cha',
  `tpStatus` tinyint(4) NOT NULL COMMENT '1: active; 0: hidden'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `topics`
--

INSERT INTO `topics` (`tpID`, `tpTitle`, `tpParent`, `tpStatus`) VALUES
(1, 'Toán', 0, 1),
(2, 'Tiếng anh ', 0, 1),
(3, 'Lịch sử ', 0, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `userName` varchar(40) NOT NULL COMMENT 'login = userName',
  `userEmail` varchar(20) NOT NULL,
  `userPassword` varchar(40) NOT NULL COMMENT 'mã hóa dùng md5',
  `userFullName` varchar(40) NOT NULL,
  `isAdmin` tinyint(4) NOT NULL COMMENT '1: admin; 0: user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`userID`, `userName`, `userEmail`, `userPassword`, `userFullName`, `isAdmin`) VALUES
(1, 'nam', 'nam@gmail.com', '123456', 'nam123', 1),
(2, 'tay nguyen', 'tay123@gmail.com', 'T123', '1213123', 1),
(3, 'tay52636', 'tay52636@gmail.com', '52636@', 'Nguyen Tay', 0),
(4, 'Thanh Dev', 'thanh@gmail.com', '123123', 'CHi Thanh', 0),
(5, 'Ngoc Trinh', 'trinh@gmail.com', '24042003', 'Pham thi Ngọc Trinh', 0),
(6, 'TayNguyen52636', 'test16@gmail.com', '05052003', 'Wesless', 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `answers`
--
ALTER TABLE `answers`
  ADD PRIMARY KEY (`awID`),
  ADD KEY `qID` (`qID`);

--
-- Chỉ mục cho bảng `exams`
--
ALTER TABLE `exams`
  ADD PRIMARY KEY (`testCode`,`exOrder`),
  ADD KEY `testCode` (`testCode`),
  ADD KEY `exCode` (`exCode`);

--
-- Chỉ mục cho bảng `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`logID`),
  ADD KEY `logUserID` (`logUserID`);

--
-- Chỉ mục cho bảng `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`qID`),
  ADD KEY `qTopicID` (`qTopicID`);

--
-- Chỉ mục cho bảng `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`rs_num`,`userID`,`exCode`),
  ADD KEY `userID` (`userID`),
  ADD KEY `exID` (`exCode`),
  ADD KEY `exCode` (`exCode`);

--
-- Chỉ mục cho bảng `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`testID`);

--
-- Chỉ mục cho bảng `test_structure`
--
ALTER TABLE `test_structure`
  ADD PRIMARY KEY (`testCode`),
  ADD KEY `tpID` (`tpID`);

--
-- Chỉ mục cho bảng `topics`
--
ALTER TABLE `topics`
  ADD PRIMARY KEY (`tpID`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `answers`
--
ALTER TABLE `answers`
  MODIFY `awID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=481;

--
-- AUTO_INCREMENT cho bảng `logs`
--
ALTER TABLE `logs`
  MODIFY `logID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `questions`
--
ALTER TABLE `questions`
  MODIFY `qID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=504;

--
-- AUTO_INCREMENT cho bảng `result`
--
ALTER TABLE `result`
  MODIFY `rs_num` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=88;

--
-- AUTO_INCREMENT cho bảng `test`
--
ALTER TABLE `test`
  MODIFY `testID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT cho bảng `topics`
--
ALTER TABLE `topics`
  MODIFY `tpID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `answers`
--
ALTER TABLE `answers`
  ADD CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`qID`) REFERENCES `questions` (`qID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `logs_ibfk_1` FOREIGN KEY (`logUserID`) REFERENCES `users` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`qTopicID`) REFERENCES `topics` (`tpID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `result`
--
ALTER TABLE `result`
  ADD CONSTRAINT `result_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `test_structure`
--
ALTER TABLE `test_structure`
  ADD CONSTRAINT `test_structure_ibfk_1` FOREIGN KEY (`tpID`) REFERENCES `topics` (`tpID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
