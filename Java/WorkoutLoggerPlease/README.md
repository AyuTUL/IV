# WorkoutLogger (DB-only)

## Setup
1. Create a MySQL schema (e.g., `workout_logger`).
2. Run `schema.sql` then `sample_data.sql` against that schema.
3. Set DB connection in `com.ayush.workoutlogger.connection.ConnectionFactory` (URL, user, password).

Default login: **admin / admin**.

## Modules
- Login → Dashboard
- Exercises (DAO-backed)
- Log Workout (uses Exercises + Workouts tables)
- Body Metrics (full CRUD)
- Sessions (list/refresh; extend CRUD as needed)
- Reports (placeholder)

