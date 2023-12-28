CREATE TABLE `users` (
  `birth_date` date DEFAULT NULL,
  `height` double DEFAULT NULL,
  `is_active` bit(1) DEFAULT 1,
  `weight` double DEFAULT NULL,
  `id` varchar(36) NOT NULL,
  `confef` varchar(255) DEFAULT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `observations` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','INSTRUCTOR','CLIENT') NOT NULL,
  PRIMARY KEY (`id`)
);