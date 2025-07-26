CREATE DATABASE university;

USE university;

CREATE TABLE faculty (
    id INT PRIMARY KEY,
    fname VARCHAR(30),
    address VARCHAR(50),
    fsubject VARCHAR(30),
    salary FLOAT
);

INSERT INTO faculty VALUES
(1, 'Arjun', 'Kathmandu', 'Math', 30000),
(2, 'Bijay', 'Lalitpur', 'Science', 25000),
(3, 'Chetana', 'Pokhara', 'English', 32000),
(4, 'Dipak', 'Bhaktapur', 'Computer', 28000),
(5, 'Elisha', 'Chitwan', 'Nepali', 27000);

SELECT 
    COUNT(*) AS total_rows,
    SUM(salary) AS total_salary,
    AVG(salary) AS avg_salary,
    MAX(salary) AS max_salary,
    MIN(salary) AS min_salary
FROM faculty;

SELECT fname, salary
FROM faculty
WHERE salary > (
    SELECT AVG(salary) FROM faculty
);
