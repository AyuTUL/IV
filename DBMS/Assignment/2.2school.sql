CREATE DATABASE school;

USE school;

CREATE TABLE ssubject (
    subject_id INT PRIMARY KEY,
    ssubject VARCHAR(30)
);

CREATE TABLE student (
    id INT PRIMARY KEY,
    sname VARCHAR(30),
    course VARCHAR(30),
    subject_id INT,
    mark INT,
    FOREIGN KEY (subject_id) REFERENCES ssubject(subject_id)
);

INSERT INTO ssubject VALUES
(1, 'Math'),
(2, 'Science'),
(3, 'English'),
(4, 'Computer'),
(5, 'Nepali');

INSERT INTO student VALUES
(101, 'Amit', 'BSc', 1, 85),
(102, 'Bina', 'BSc', 2, 75),
(103, 'Chetan', 'BBA', 3, 65),
(104, 'Diya', 'BBA', 4, 80),
(105, 'Elina', 'BSc', 5, 90);

SELECT s.id, s.sname, s.course, sub.ssubject, s.mark
FROM student AS s
JOIN ssubject AS sub 
ON s.subject_id = sub.subject_id;

SELECT course, COUNT(*) AS total_students
FROM student
GROUP BY course;
