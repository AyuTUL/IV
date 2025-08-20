-- Sample data for quick start
INSERT INTO users (username, password, role) VALUES
  ('admin', 'admin', 'ADMIN')
ON DUPLICATE KEY UPDATE password=VALUES(password), role=VALUES(role);

INSERT INTO exercises (name, muscle_group) VALUES
  ('Bench Press', 'Chest'),
  ('Squat', 'Legs'),
  ('Deadlift', 'Back')
ON DUPLICATE KEY UPDATE muscle_group=VALUES(muscle_group);

-- create one session today for admin
INSERT INTO sessions (user_id, session_date, notes)
SELECT id, CURDATE(), 'Sample session' FROM users WHERE username='admin'
ON DUPLICATE KEY UPDATE notes='Sample session';

-- connect a few workouts to latest session
INSERT INTO workouts (session_id, exercise_id, sets, reps, weight)
SELECT s.id, e.id, 3, 8, 60
FROM sessions s
JOIN users u ON s.user_id = u.id
JOIN exercises e ON e.name='Bench Press'
WHERE u.username='admin'
LIMIT 1;
