ALTER TABLE exercises
DROP COLUMN muscle_group;

CREATE TABLE `exercise_muscle_groups` (
  `exercise_id` binary(16) NOT NULL,
  `muscle_groups` enum('DELTOIDS','BICEPS','OBLIQUES','QUADRICEPS','CHEST','FOREARM','ABS','ANKLE_FLEXORS','TRAPEZIUS','MIDDLE_BACK','LOWER_BACK','TRICEPS','BACK_OF_FOREARM','GLUTES','HAMSTRINGS','ANKLE_EXTENSORS') NOT NULL,
  KEY `FK1m7k8dr25xe4dhaq0xlbjmvll` (`exercise_id`),
  CONSTRAINT `FK1m7k8dr25xe4dhaq0xlbjmvll` FOREIGN KEY (`exercise_id`) REFERENCES `exercises` (`id`)
);