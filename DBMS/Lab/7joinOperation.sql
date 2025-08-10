/*Lab 7: JOIN operation
1. Write SQL code to display records using INNER JOIN, JOIN and OUTER JOIN (LEFT, RIGHT, FULL).
*/

CREATE DATABASE Clubs;
USE Clubs;

CREATE TABLE Dortmund (
	Jersey INT PRIMARY KEY,
	Pname VARCHAR(30),
	Paddress VARCHAR(20),
	Pposition VARCHAR (10),
	Psalary FLOAT(8,2)
);

CREATE TABLE Arsenal (
	Jersey INT PRIMARY KEY,
	Pname VARCHAR(30),
	Pposition VARCHAR (10),
	MarketValue FLOAT(10,2)
);

INSERT INTO Arsenal VALUES
	(41,'Declan Rice','CDM',1100000.00),
	(7,'Bukayo Saka','RW',12000000.50),
	(6,'Gabriel Magalhaes','CB',6500000.19),
	(8,'Martin Odegaard','CAM',10000000.69),
	(9,'Viktor Gyokores','ST',690000.69),
	(1,'David Raya','GK',4000000.14),
	(11,'Gabriel Martinelli','LW',563632.11);
	
INSERT INTO Dortmund VALUES
	(1,'Gregor Kobel','Germany','GK',11000.00),
	(4,'Nico Schlotterbeck','Germany,','CB',10000.50),
	(7,'Gio Reyna','USA','CAM',80000.19),
	(27,'Karim Adeyemi','Germany','CF',100000.69),
	(9,'Sebastian Haller','Germany','ST',6900.69),
	(19,'Julian Brandt','Germany','RW',4000.14),
	(11,'Marco Reus','Germany','LW',5632.11);

SELECT a.Jersey, d.pPosition AS POSITION, a.Pname AS Arsenal, d.Pname AS Dortmund FROM Arsenal AS a 
INNER JOIN Dortmund AS d ON 
a.jersey<10 AND a.Pposition=d.Pposition;

SELECT * FROM Arsenal AS a 
LEFT OUTER JOIN Dortmund AS d 
ON a.jersey=d.jersey;

SELECT * FROM Arsenal AS a 
RIGHT OUTER JOIN Dortmund AS d 
ON a.Pposition=d.Pposition;
