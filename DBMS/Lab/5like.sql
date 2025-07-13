/*Lab 5:
1. Write SQL code to display Employee records whose name begin with 'P'.
2. Display records whose name ends with 'e'.
3. Display records whose name starts with 'K' and end with 'l'.
4. Display records whose address contains substring 'pur'.
5. Disaply records whose name starts with 'S' and ends by 'a' and address is 'Kantipur'.
*/

CREATE DATABASE Tottenham_Hotspurs;

USE Tottenham_Hotspurs;

CREATE TABLE Employee (
	Eid INT,
	Fname VARCHAR(30),
	Eaddress VARCHAR(15),
	Ephone VARCHAR(10),
	Ecity VARCHAR(15),
	Eage INT,
	Edetails TEXT,
	Esalary DECIMAL(10,2)
);

INSERT INTO Employee VALUES
	(1, 'Guglielmo Vicario', 'Italypur', '0712345678', 'Castelfranco Veneto', 28, 'Goalkeeper', 120000.00),
	(7, 'Son Heung-min', 'South Koreapur', '0798765432', 'Chuncheon', 32, 'Left wing', 185000.00),
	(23, 'Pedro Porro', 'Lisbon', '0712340001', 'Ciudad Real', 26, 'Right wing-back', 115000.00),
	(17, 'Cristian Romero', 'Argentina', '0733445566', 'Córdoba', 27, 'Centre-back', 160000.00),
	(9, 'Richarlison', 'Kantipur', '0788990011', 'Nova Venécia', 27, 'Striker', 140000.00),
	(66, 'Michael Keane', 'Manchester', '0712340022', 'Manchester', 30, 'Centre-back', 140000.00),
	(29, 'Pape Matar Sarr', 'Dakar', '0712340002', 'Thiès', 21, 'Central Midfield', 90000.00),
	(59, 'Paul Pogba', 'Kantipur', '0712340015', 'Lagny-sur-Marne', 30, 'Midfielder', 180000.00),
	(21, 'Dejan Kulusevski', 'Sweden', '0755332211', 'Stockholm', 27, 'Right wing', 145000.00),
	(72, 'Joe Cole', 'London', '0712340028', 'London', 42, 'Midfielder', 0.00),
	(82, 'Kunal Kaul', 'Kantipur', '0712340032', 'Mumbai', 27, 'Defender', 90000.00),
	(81, 'Kiran Kul', 'Delhi', '0712340031', 'Delhi', 25, 'Forward', 80000.00),
	(80, 'Kamal Karel', 'Lahore', '0712340030', 'Lahore', 28, 'Midfielder', 85000.00),
	(93, 'Silvia Petta', 'Rome', '0712340044', 'Rome', 27, 'Goalkeeper', 90000.00),
	(92, 'Sonia Batra', 'Kantipur', '0712340043', 'Mumbai', 28, 'Midfielder', 78000.00),
	(90, 'Sasha Zena', 'Kantipur', '0712340041', 'Belgrade', 26, 'Forward', 85000.00);

SELECT * FROM Employee WHERE Fname LIKE 'P%';

SELECT * FROM Employee WHERE Fname LIKE '%e';

SELECT * FROM Employee WHERE Fname LIKE 'K%l';

SELECT * FROM Employee WHERE Eaddress LIKE '%pur%';

SELECT * FROM Employee WHERE Fname LIKE 'S%a' AND Eaddress='Kantipur';

DROP DATABASE Tottenham_Hotspurs;