CREATE DATABASE college;

USE college;

CREATE TABLE ssubject (
    subject_id INT PRIMARY KEY,
    subject_name VARCHAR(30)
);

CREATE TABLE teacher (
    id INT PRIMARY KEY,
    tname VARCHAR(30),
    address VARCHAR(50),
    salary FLOAT
);

INSERT INTO ssubject VALUES
(1, 'Math'), (2, 'Science'), (3, 'English'), (4, 'Computer'), (5, 'Nepali');

INSERT INTO teacher VALUES
(1, 'Anil', 'Kathmandu', 25000),
(2, 'Bimala', 'Lalitpur', 27000),
(3, 'Chiran', 'Pokhara', 23000),
(4, 'Dikshya', 'Bhaktapur', 26000),
(5, 'Erika', 'Chitwan', 28000);

ALTER TABLE teacher ADD subject_id INT;

UPDATE teacher SET subject_id = 1 WHERE id = 1;
UPDATE teacher SET subject_id = 2 WHERE id = 2;
UPDATE teacher SET subject_id = 3 WHERE id = 3;
UPDATE teacher SET subject_id = 4 WHERE id = 4;
UPDATE teacher SET subject_id = 5 WHERE id = 5;

CREATE VIEW teacher_subject_view AS
SELECT t.tname, s.subject_name
FROM teacher AS t
JOIN ssubject AS  s ON t.subject_id = s.subject_id;
