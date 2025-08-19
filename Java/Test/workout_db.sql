-- Workout Logger database schema
-- Create database
CREATE DATABASE IF NOT EXISTS workout_db;
USE workout_db;

-- Users
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role ENUM('admin','trainer','trainee') NOT NULL
);

-- Exercises
CREATE TABLE IF NOT EXISTS exercises (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  muscle_group VARCHAR(50) NOT NULL
);

-- Sessions
CREATE TABLE IF NOT EXISTS sessions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  date DATE NOT NULL,
  notes TEXT,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Workout Sets
CREATE TABLE IF NOT EXISTS workout_sets (
  id INT AUTO_INCREMENT PRIMARY KEY,
  session_id INT NOT NULL,
  exercise_id INT NOT NULL,
  reps INT NOT NULL,
  weight_kg DECIMAL(6,2) NOT NULL,
  rpe DECIMAL(3,1) NULL,
  FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE,
  FOREIGN KEY (exercise_id) REFERENCES exercises(id) ON DELETE CASCADE
);

-- Body Metrics
CREATE TABLE IF NOT EXISTS body_metrics (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  date DATE NOT NULL,
  weight_kg DECIMAL(6,2) NOT NULL,
  body_fat DECIMAL(5,2) NULL,
  notes TEXT,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Seed users (passwords in plain for simplicity; you can hash in app if desired)
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'admin'),
('trainer1', 'trainer123', 'trainer'),
('trainee1', 'trainee123', 'trainee')
ON DUPLICATE KEY UPDATE password=VALUES(password), role=VALUES(role);

-- Seed exercises
INSERT INTO exercises (name, muscle_group) VALUES
('Squat','Legs'), ('Bench Press','Chest'), ('Deadlift','Back'),
('Overhead Press','Shoulders'), ('Barbell Row','Back')
;

-- Sample session for trainee1 on today's date
SET @uid := (SELECT id FROM users WHERE username='trainee1' LIMIT 1);
INSERT INTO sessions (user_id, date, notes) VALUES (@uid, CURRENT_DATE(), 'Sample session');
SET @sid := LAST_INSERT_ID();
SET @ex1 := (SELECT id FROM exercises WHERE name='Squat' LIMIT 1);
SET @ex2 := (SELECT id FROM exercises WHERE name='Bench Press' LIMIT 1);

INSERT INTO workout_sets (session_id, exercise_id, reps, weight_kg, rpe) VALUES
(@sid, @ex1, 5, 80.0, 7.5),
(@sid, @ex1, 5, 80.0, 7.5),
(@sid, @ex2, 5, 60.0, 7.0);

-- Body metrics sample
INSERT INTO body_metrics (user_id, date, weight_kg, body_fat, notes) VALUES
(@uid, CURRENT_DATE(), 72.5, NULL, 'Feeling good');
