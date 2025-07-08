/*Lab 3:
1. Create table Student in SQL.
2. Add one column in existing table.
3. Delete column from existing table
4. Change datatype of existing table.
5. Display student records who read in grade '12' & marks above 90.
*/

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
	(11,'Gareth Bale',12,99),
	(9,'Karim Benzema',15,15),
	(7,'Cristiano Ronaldo',5,100),
	(1,'Iker Casillas',12,13),
	(5,'Sergio Ramos',12,92)
;

SELECT * FROM student WHERE Grade=12 AND Marks>=90;
