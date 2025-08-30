/*Lab 11: Alias
i) Create alias name of existing attribute
ii) Create table Teacher with suitable fields:
	a) Insert 7 records
	b) Give increment of 30% of salary of Computer Dept
	c) Give increment of 50% of salary who works more than 10 years
	d) Find the highest paying and lowest paying Teacher from Math Dept
*/

CREATE DATABASE Hangzhou_Spark;
USE Hangzhou_Spark;

CREATE TABLE Teacher (
	tid INT,
	tname VARCHAR(30),
	salary DECIMAL(10,2),
	department VARCHAR(10),
	experience INT
);

INSERT INTO Teacher VALUES
(1, 'Guxue', 50000.35, 'Computer', 5),
(12, 'Shy', 60000.77, 'Math', 12),
(3, 'Leave', 70000.6, 'Computer', 8),
(4, 'Mmonk', 55000.81, 'Math', 3),
(5, 'Lengsa', 65000.12, 'Computer', 15),
(6, 'Pineapple', 72000.00, 'Math', 10),
(19, 'Baconjack', 48000.93, 'Math', 2);

SELECT 
	tid AS ID,
	tname AS TeacherName, 
	salary AS TeacherSalary,
	department AS "Dept.",
	experience AS ExperienceInYears    
FROM Teacher;

UPDATE Teacher SET salary=salary+0.3*salary WHERE department='Computer';

UPDATE Teacher SET salary=salary*1.5 WHERE experience>10;

SELECT tid,tname AS HighestPaidTeacher,salary,experience,department
FROM Teacher WHERE salary=
(SELECT MAX(salary) FROM Teacher WHERE department='Math');

SELECT tid,tname AS LowestPaidTeacher,salary,experience,department
FROM Teacher WHERE salary=
(SELECT MIN(salary) FROM Teacher WHERE department='Math');


	