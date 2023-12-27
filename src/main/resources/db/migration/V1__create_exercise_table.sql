CREATE TABLE `exercises` (
  `id` binary(16) NOT NULL,
  `description` text DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `instructions` text DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `video_url` varchar(255) DEFAULT NULL,
  `muscle_group` enum('DELTOIDS','BICEPS','OBLIQUES','QUADRICEPS','CHEST','FOREARM','ABS','ANKLE_FLEXORS','TRAPEZIUS','MIDDLE_BACK','LOWER_BACK','TRICEPS','BACK_OF_FOREARM','GLUTES','HAMSTRINGS','ANKLE_EXTENSORS') NOT NULL,
  PRIMARY KEY (`id`)
);