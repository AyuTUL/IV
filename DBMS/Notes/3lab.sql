CREATE DATABASE School;

USE School;

CREATE TABLE Student(
	Roll INT PRIMARY KEY,
	Fname VARCHAR(30),
	Address VARCHAR(15),
	Grade INT
);

ALTER TABLE Student ADD Marks FLOAT(4);

ALTER TABLE Student DROP COLUMN Address;

ALTER TABLE Student MODIFY Marks INT;

INSERT INTO Student VALUES
	(11,'Gareth Bale',12,92),
	(9,'Karim Benzema',15,15),
	(7,'Cristiano Ronaldo',5,100),
	(1,'Iker Casillas',12,13),
	(5,'Sergio Ramos',12,92)
;

SELECT * FROM student WHERE Grade=12 AND Marks>=90;
