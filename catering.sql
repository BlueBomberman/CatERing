-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Sep 29, 2020 at 02:50 PM
-- Server version: 5.7.26
-- PHP Version: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `catering`
--

-- --------------------------------------------------------

--
-- Table structure for table `assignedshifts`
--

CREATE TABLE `assignedshifts` (
  `id_user` int(11) NOT NULL,
  `id_shift` int(11) NOT NULL,
  `role` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assignedshifts`
--

INSERT INTO `assignedshifts` (`id_user`, `id_shift`, `role`) VALUES
(4, 4, ''),
(4, 5, ''),
(4, 6, ''),
(4, 7, ''),
(5, 10, ''),
(5, 11, ''),
(5, 12, ''),
(5, 13, ''),
(6, 6, ''),
(6, 7, ''),
(6, 8, ''),
(6, 9, ''),
(7, 5, ''),
(7, 6, ''),
(7, 7, '');

-- --------------------------------------------------------

--
-- Table structure for table `assignments`
--

CREATE TABLE `assignments` (
  `id` int(11) NOT NULL,
  `ready` tinyint(1) NOT NULL,
  `estTime` varchar(128) DEFAULT NULL,
  `quantity` varchar(128) DEFAULT NULL,
  `id_cook` int(11) DEFAULT NULL,
  `id_shift` int(11) DEFAULT NULL,
  `id_duty` int(11) NOT NULL,
  `id_sheet` int(11) NOT NULL,
  `position` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assignments`
--

INSERT INTO `assignments` (`id`, `ready`, `estTime`, `quantity`, `id_cook`, `id_shift`, `id_duty`, `id_sheet`, `position`) VALUES
(58, 0, NULL, NULL, NULL, NULL, 9, 11, 1),
(59, 0, NULL, NULL, NULL, NULL, 10, 11, 2),
(60, 0, NULL, NULL, NULL, NULL, 11, 11, 3),
(63, 0, NULL, NULL, NULL, NULL, 12, 11, 6),
(64, 0, NULL, NULL, NULL, NULL, 1, 12, 0),
(65, 0, NULL, NULL, NULL, NULL, 2, 12, 1),
(66, 0, NULL, NULL, NULL, NULL, 3, 12, 2),
(67, 0, NULL, NULL, NULL, NULL, 5, 12, 3),
(68, 0, NULL, NULL, NULL, NULL, 20, 12, 4),
(69, 0, NULL, NULL, NULL, NULL, 8, 12, 5),
(70, 0, NULL, NULL, NULL, NULL, 18, 12, 6),
(71, 0, NULL, NULL, NULL, NULL, 19, 12, 7);

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `date_start` date DEFAULT NULL,
  `date_end` date DEFAULT NULL,
  `expected_participants` int(11) DEFAULT NULL,
  `organizer_id` int(11) NOT NULL,
  `chef_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`id`, `name`, `date_start`, `date_end`, `expected_participants`, `organizer_id`, `chef_id`) VALUES
(1, 'Convegno Agile Community', '2020-09-25', '2020-09-25', 100, 2, 2),
(2, 'Compleanno di Sara', '2020-08-13', '2020-08-13', 25, 2, 2),
(3, 'Fiera del Sedano Rapa', '2020-10-02', '2020-10-04', 400, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `menufeatures`
--

CREATE TABLE `menufeatures` (
  `menu_id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL DEFAULT '',
  `value` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `menufeatures`
--

INSERT INTO `menufeatures` (`menu_id`, `name`, `value`) VALUES
(80, 'Richiede cuoco', 0),
(80, 'Buffet', 0),
(80, 'Richiede cucina', 0),
(80, 'Finger food', 0),
(80, 'Piatti caldi', 0),
(82, 'Richiede cuoco', 0),
(82, 'Buffet', 0),
(82, 'Richiede cucina', 0),
(82, 'Finger food', 0),
(82, 'Piatti caldi', 0),
(86, 'Richiede cuoco', 0),
(86, 'Buffet', 0),
(86, 'Richiede cucina', 0),
(86, 'Finger food', 0),
(86, 'Piatti caldi', 0),
(88, 'Richiede cuoco', 0),
(88, 'Buffet', 0),
(88, 'Richiede cucina', 0),
(88, 'Finger food', 0),
(88, 'Piatti caldi', 0),
(91, 'Richiede cuoco', 0),
(91, 'Buffet', 0),
(91, 'Richiede cucina', 0),
(91, 'Finger food', 0),
(91, 'Piatti caldi', 0),
(92, 'Richiede cuoco', 0),
(92, 'Buffet', 0),
(92, 'Richiede cucina', 0),
(92, 'Finger food', 0),
(92, 'Piatti caldi', 0),
(93, 'Richiede cuoco', 0),
(93, 'Buffet', 0),
(93, 'Richiede cucina', 0),
(93, 'Finger food', 0),
(93, 'Piatti caldi', 0),
(94, 'Richiede cuoco', 0),
(94, 'Buffet', 0),
(94, 'Richiede cucina', 0),
(94, 'Finger food', 0),
(94, 'Piatti caldi', 0);

-- --------------------------------------------------------

--
-- Table structure for table `menuitems`
--

CREATE TABLE `menuitems` (
  `id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `section_id` int(11) DEFAULT NULL,
  `description` tinytext,
  `recipe_id` int(11) NOT NULL,
  `position` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `menuitems`
--

INSERT INTO `menuitems` (`id`, `menu_id`, `section_id`, `description`, `recipe_id`, `position`) VALUES
(96, 80, 0, 'Croissant vuoti', 9, 0),
(97, 80, 0, 'Croissant alla marmellata', 9, 1),
(98, 80, 0, 'Pane al cioccolato mignon', 10, 2),
(99, 80, 0, 'Panini al latte con prosciutto crudo', 12, 4),
(100, 80, 0, 'Panini al latte con prosciutto cotto', 12, 5),
(101, 80, 0, 'Panini al latte con formaggio spalmabile alle erbe', 12, 6),
(102, 80, 0, 'Girelle all\'uvetta mignon', 11, 3),
(103, 82, 0, 'Biscotti', 13, 1),
(104, 82, 0, 'Lingue di gatto', 14, 2),
(105, 82, 0, 'Bigné alla crema', 15, 3),
(106, 82, 0, 'Bigné al caffè', 15, 4),
(107, 82, 0, 'Pizzette', 16, 5),
(108, 82, 0, 'Croissant al prosciutto crudo mignon', 9, 6),
(109, 82, 0, 'Tramezzini tonno e carciofini mignon', 17, 7),
(112, 86, 41, 'Vitello tonnato', 1, 0),
(113, 86, 41, 'Carpaccio di spada', 2, 1),
(114, 86, 41, 'Alici marinate', 3, 2),
(115, 86, 42, 'Penne alla messinese', 5, 0),
(116, 86, 42, 'Risotto alla zucca', 20, 1),
(117, 86, 43, 'Salmone al forno', 8, 0),
(118, 86, 44, 'Sorbetto al limone', 18, 0),
(119, 86, 44, 'Torta Saint Honoré', 19, 1),
(121, 91, 45, 'Insalata di riso', 4, 0),
(122, 91, 45, 'Hamburger con bacon e cipolla caramellata', 7, 1),
(123, 92, 0, 'Biscotti', 13, 0),
(124, 92, 0, 'Lingue di gatto', 14, 1),
(125, 92, 0, 'Bigné alla crema', 15, 2),
(126, 92, 0, 'Bigné al caffè', 15, 3),
(127, 92, 0, 'Pizzette', 16, 4),
(128, 92, 0, 'Croissant al prosciutto crudo mignon', 9, 5),
(129, 92, 0, 'Tramezzini tonno e carciofini mignon', 17, 6),
(130, 94, 47, 'Papapapa', 6, 0);

-- --------------------------------------------------------

--
-- Table structure for table `menus`
--

CREATE TABLE `menus` (
  `id` int(11) NOT NULL,
  `title` tinytext,
  `owner_id` int(11) DEFAULT NULL,
  `published` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `menus`
--

INSERT INTO `menus` (`id`, `title`, `owner_id`, `published`) VALUES
(80, 'Coffee break mattutino', 2, 1),
(82, 'Coffee break pomeridiano', 2, 1),
(86, 'Cena di compleanno pesce', 3, 1),
(88, 'CIccio', 2, 0),
(91, 'Criste', 2, 0),
(92, 'Coffee break pomeridiano', 2, 0),
(93, 'Menu', 2, 0),
(94, ' Menu1', 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `menusections`
--

CREATE TABLE `menusections` (
  `id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `name` tinytext,
  `position` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `menusections`
--

INSERT INTO `menusections` (`id`, `menu_id`, `name`, `position`) VALUES
(41, 86, 'Antipasti', 0),
(42, 86, 'Primi', 1),
(43, 86, 'Secondi', 2),
(44, 86, 'Dessert', 3),
(45, 91, 'asduf has', 0),
(46, 91, 'asd fjasdpof j', 1),
(47, 94, 'Sezione1', 0);

-- --------------------------------------------------------

--
-- Table structure for table `recipes`
--

CREATE TABLE `recipes` (
  `id` int(11) NOT NULL,
  `name` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recipes`
--

INSERT INTO `recipes` (`id`, `name`) VALUES
(1, 'Vitello tonnato'),
(2, 'Carpaccio di spada'),
(3, 'Alici marinate'),
(4, 'Insalata di riso'),
(5, 'Penne al sugo di baccalà'),
(6, 'Pappa al pomodoro'),
(7, 'Hamburger con bacon e cipolla caramellata'),
(8, 'Salmone al forno'),
(9, 'Croissant'),
(10, 'Pane al cioccolato'),
(11, 'Girelle all\'uvetta'),
(12, 'Panini al latte'),
(13, 'Biscotti di pasta frolla'),
(14, 'Lingue di gatto'),
(15, 'Bigné farciti'),
(16, 'Pizzette'),
(17, 'Tramezzini'),
(18, 'Sorbetto al limone'),
(19, 'Torta Saint Honoré'),
(20, 'Risotto alla zucca');

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` char(1) NOT NULL,
  `role` varchar(128) NOT NULL DEFAULT 'servizio'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `role`) VALUES
('c', 'cuoco'),
('h', 'chef'),
('o', 'organizzatore'),
('s', 'servizio');

-- --------------------------------------------------------

--
-- Table structure for table `services`
--

CREATE TABLE `services` (
  `id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `proposed_menu_id` int(11) NOT NULL DEFAULT '0',
  `approved_menu_id` int(11) DEFAULT '0',
  `service_date` date DEFAULT NULL,
  `time_start` time DEFAULT NULL,
  `time_end` time DEFAULT NULL,
  `expected_participants` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `services`
--

INSERT INTO `services` (`id`, `event_id`, `name`, `proposed_menu_id`, `approved_menu_id`, `service_date`, `time_start`, `time_end`, `expected_participants`) VALUES
(1, 2, 'Cena', 86, 86, '2020-08-13', '20:00:00', '23:30:00', 25),
(2, 1, 'Coffee break mattino', 0, 80, '2020-09-25', '10:30:00', '11:30:00', 100),
(3, 1, 'Colazione di lavoro', 0, 80, '2020-09-25', '13:00:00', '14:00:00', 80),
(4, 1, 'Coffee break pomeriggio', 0, 82, '2020-09-25', '16:00:00', '16:30:00', 100),
(5, 1, 'Cena sociale', 0, 0, '2020-09-25', '20:00:00', '22:30:00', 40),
(6, 3, 'Pranzo giorno 1', 0, 0, '2020-10-02', '12:00:00', '15:00:00', 200),
(7, 3, 'Pranzo giorno 2', 0, 0, '2020-10-03', '12:00:00', '15:00:00', 300),
(8, 3, 'Pranzo giorno 3', 0, 0, '2020-10-04', '12:00:00', '15:00:00', 400);

-- --------------------------------------------------------

--
-- Table structure for table `shifts`
--

CREATE TABLE `shifts` (
  `id` int(11) NOT NULL,
  `id_service` int(11) NOT NULL,
  `startTime` timestamp NULL DEFAULT NULL,
  `endTime` timestamp NULL DEFAULT NULL,
  `closed` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shifts`
--

INSERT INTO `shifts` (`id`, `id_service`, `startTime`, `endTime`, `closed`) VALUES
(1, 2, '2020-09-27 02:00:00', '2020-09-27 11:00:00', 0),
(2, 2, '2020-09-27 10:00:00', '2020-09-27 12:00:00', 0),
(3, 2, '2020-09-27 10:00:00', '2020-09-27 12:00:00', 0),
(4, 3, '2020-09-10 05:00:00', '2020-09-27 13:00:00', 0),
(5, 1, '2020-09-11 14:00:00', '2020-09-19 21:00:00', 0),
(6, 1, '2020-09-18 02:00:00', '2020-09-18 13:00:00', 0),
(7, 4, '2020-09-24 04:00:00', '2020-09-24 10:00:00', 0),
(8, 4, '2020-09-10 04:00:00', '2020-09-10 12:00:00', 0),
(9, 5, '2020-09-30 08:00:00', '2020-09-30 16:00:00', 0),
(10, 5, '2020-10-15 22:00:00', '2020-10-16 09:00:00', 0),
(11, 4, '2020-09-10 04:00:00', '2020-09-10 12:00:00', 0),
(12, 3, '2020-09-30 08:00:00', '2020-09-30 16:00:00', 0),
(13, 1, '2020-10-15 22:00:00', '2020-10-16 09:00:00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `summarysheet`
--

CREATE TABLE `summarysheet` (
  `id` int(11) NOT NULL,
  `id_service` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `summarysheet`
--

INSERT INTO `summarysheet` (`id`, `id_service`) VALUES
(11, 2),
(12, 1);

-- --------------------------------------------------------

--
-- Table structure for table `userroles`
--

CREATE TABLE `userroles` (
  `user_id` int(11) NOT NULL,
  `role_id` char(1) NOT NULL DEFAULT 's'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `userroles`
--

INSERT INTO `userroles` (`user_id`, `role_id`) VALUES
(1, 'o'),
(2, 'o'),
(2, 'h'),
(3, 'h'),
(4, 'h'),
(4, 'c'),
(5, 'c'),
(6, 'c'),
(7, 'c'),
(8, 's'),
(9, 's'),
(10, 's'),
(7, 's');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(128) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`) VALUES
(1, 'Carlin'),
(2, 'Lidia'),
(3, 'Tony'),
(4, 'Marinella'),
(5, 'Guido'),
(6, 'Antonietta'),
(7, 'Paola'),
(8, 'Silvia'),
(9, 'Marco'),
(10, 'Piergiorgio');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assignedshifts`
--
ALTER TABLE `assignedshifts`
  ADD PRIMARY KEY (`id_user`,`id_shift`);

--
-- Indexes for table `assignments`
--
ALTER TABLE `assignments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `menuitems`
--
ALTER TABLE `menuitems`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `menus`
--
ALTER TABLE `menus`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `menusections`
--
ALTER TABLE `menusections`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `recipes`
--
ALTER TABLE `recipes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `services`
--
ALTER TABLE `services`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `shifts`
--
ALTER TABLE `shifts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `summarysheet`
--
ALTER TABLE `summarysheet`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assignments`
--
ALTER TABLE `assignments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;

--
-- AUTO_INCREMENT for table `events`
--
ALTER TABLE `events`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `menuitems`
--
ALTER TABLE `menuitems`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=131;

--
-- AUTO_INCREMENT for table `menus`
--
ALTER TABLE `menus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=95;

--
-- AUTO_INCREMENT for table `menusections`
--
ALTER TABLE `menusections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT for table `recipes`
--
ALTER TABLE `recipes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `services`
--
ALTER TABLE `services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `shifts`
--
ALTER TABLE `shifts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `summarysheet`
--
ALTER TABLE `summarysheet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
