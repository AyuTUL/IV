-- Workout Logger DB schema (MySQL-compatible)
DROP DATABASE workout_logger;
CREATE DATABASE workout_logger;
USE workout_logger;
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  PASSWORD VARCHAR(255) NOT NULL,
  ROLE VARCHAR(20) NOT NULL DEFAULT 'USER'
);

CREATE TABLE IF NOT EXISTS exercises (
  id INT AUTO_INCREMENT PRIMARY KEY,
  NAME VARCHAR(100) NOT NULL,
  muscle_group VARCHAR(100),
  UNIQUE KEY uq_ex_name (NAME)
);

CREATE TABLE IF NOT EXISTS sessions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  session_date DATE NOT NULL,
  notes VARCHAR(255),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS workouts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  session_id INT NOT NULL,
  exercise_id INT NOT NULL,
  sets INT NOT NULL,
  reps INT NOT NULL,
  weight DOUBLE,
  FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE,
  FOREIGN KEY (exercise_id) REFERENCES exercises(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS body_metrics (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  metric_date DATE NOT NULL,
  weight_kg DOUBLE NOT NULL,
  body_fat DOUBLE NULL,
  notes VARCHAR(255),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Sample data for quick start
INSERT INTO users (username, PASSWORD, ROLE) VALUES
('trainee', 'trainee', 'TRAINEE'),
  ('trainer', 'trainer', 'TRAINER')
ON DUPLICATE KEY UPDATE PASSWORD=VALUES(PASSWORD), ROLE=VALUES(ROLE);

INSERT INTO exercises (NAME, muscle_group) VALUES
  ('Bench Press', 'Chest'),
  ('Squat', 'Legs'),
  ('Deadlift', 'Back')
ON DUPLICATE KEY UPDATE muscle_group=VALUES(muscle_group);

-- create one session today for 
INSERT INTO sessions (user_id, session_date, notes)
SELECT id, CURDATE(), 'Sample session' FROM users WHERE username='trainer'
ON DUPLICATE KEY UPDATE notes='Sample session';

INSERT INTO sessions (user_id, session_date, notes)
SELECT id, CURDATE(), 'Sample session' FROM users WHERE username='trainee'
ON DUPLICATE KEY UPDATE notes='Sample session';

-- connect a few workouts to latest session
INSERT INTO workouts (session_id, exercise_id, sets, reps, weight)
SELECT s.id, e.id, 3, 8, 60
FROM sessions s
JOIN users u ON s.user_id = u.id
JOIN exercises e ON e.name='Bench Press'
WHERE u.username='admin'
LIMIT 1;
