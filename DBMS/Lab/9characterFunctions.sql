/*Lab 9: Character functions
Create table 'student' with suitable attributes and insert 10-15 records and perform the matching operation using LIKE keyword and use all
the given char functions (substring, concatenate, length, upper, lower, trim, ltrim, rtrim, char, ascii, charindex/locate)
*/

CREATE DATABASE lafc;
USE lafc;

CREATE TABLE student (
	roll INT PRIMARY KEY,
	sname VARCHAR(30),
	phone VARCHAR(20),
	address VARCHAR(15),
	gpa DECIMAL(3,2)
	);
	
INSERT INTO student (roll, sname, phone, address, gpa) VALUES
(10, 'Kevin DeBruyne',    '  +32 9876543213  ', '  Brussels ', 3.92),
(16, 'Rodri Sanchez',     ' +34 9876543215 ', 'Madrid', 3.76),
(3,  'Ruben Dias',        ' +351 9876543217 ', 'Amadora', 3.74),
(23, 'Harry Kane',        ' +44 9876543221 ', 'London', 3.77),
(7,  'James Mbappe',      '  +33 9876543212  ', 'Paris', 3.88),
(22, 'Jason Belling',     ' +44 9876543214 ', 'Stourbridge', 3.80),
(11, 'Brian Saka',        ' +44 9876543223 ', 'Ealing', 3.78),
(14, 'Lukas Modric',      ' +385 9876543224 ', 'Zadar', 3.90),
(4,  'Marco vanDijk',     ' +31 9876543216 ', 'Breda', 3.68),
(2,  'Oscar Hakimi',      ' +212 9876543218 ', 'Madrid', 3.65),
(19, 'James Davies',      ' +1 9876543219 ', ' Edmonton', 3.82),
(1,  'David Martinez',    ' +54 9876543220 ', 'Mar del Plata', 3.60),
(9,  'Simon Haaland',     ' +44 9876543211 ', 'Leeds', 3.70),
(20, 'Lucas Junior',      ' +55 9876543222 ', ' Rio ', 3.85),
(15, 'Felix Messi',       '  +34 9876543210  ', '  Rosario ', 3.95);

SELECT roll, SUBSTRING(sname,1,5) AS First_Name FROM student WHERE roll<10;

SELECT CONCAT(roll,gpa) FROM student WHERE sname LIKE '%z';

SELECT roll, sname, LENGTH(sname) AS Full_Name_Length FROM student WHERE sname LIKE 'J%';

SELECT sname, UPPER(address),gpa FROM student WHERE gpa>3.80;

SELECT roll, LOWER(sname), address AS Lowercase_Name FROM student WHERE address LIKE '%i_';

SELECT roll, sname, TRIM(phone) FROM student WHERE roll>15;

SELECT LTRIM(phone) FROM student WHERE gpa<3.70;

SELECT RTRIM(address) FROM student WHERE sname LIKE '%a%e%';

SELECT CHAR(SUBSTRING(LTRIM(phone),2,3)), ASCII(sname), LOCATE('+',phone) FROM student WHERE roll>14;



