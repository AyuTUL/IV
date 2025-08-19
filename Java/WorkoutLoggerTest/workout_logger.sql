CREATE DATABASE IF NOT EXISTS workout_logger;
USE workout_logger;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('TRAINER','TRAINEE') NOT NULL,
    age INT,
    height DECIMAL(5,2),
    weight DECIMAL(5,2)
);

CREATE TABLE workouts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    trainee_id INT NOT NULL,
    exercise_name VARCHAR(100) NOT NULL,
    sets INT,
    reps INT,
    weight DECIMAL(6,2),
    workout_date DATE NOT NULL,
    notes TEXT,
    FOREIGN KEY (trainee_id) REFERENCES users(id)
);

INSERT INTO users (username, password, role, age, height, weight) VALUES
('trainer1', 'trainerpass', 'TRAINER', NULL, NULL, NULL),
('trainee1', 'traineepass', 'TRAINEE', 22, 175.0, 68.5);

INSERT INTO workouts (trainee_id, exercise_name, sets, reps, weight, workout_date, notes) VALUES
(2, 'Bench Press', 4, 10, 60.0, '2025-08-15', 'Chest day workout'),
(2, 'Squats', 3, 12, 80.0, '2025-08-16', 'Leg workout, good form'),
(2, 'Deadlift', 3, 8, 100.0, '2025-08-17', 'Felt strong');
