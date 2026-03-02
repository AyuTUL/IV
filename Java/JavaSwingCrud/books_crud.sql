/*Lab 1:
1. Write SQL code to create table Student.
2. Write SQL code to create Employee table having 
attribute Eid, Fname, Eaddress, Ephone, Ecity, Eage, Edetails, Esalary.
3. Insert 19 records in Employee table
4. Display records from table using WHERE clause.
*/

CREATE DATABASE Swarnim;

USE Swarnim;

CREATE TABLE Student (
	Roll INT,
	Fname VARCHAR(30),
	Address VARCHAR(15),
	GPA DECIMAL(3,2),
	Phone VARCHAR(10)
);

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
(1,'Abhilekh Subedi','Raniban','9801234567','Kathmandu',28,'Software Engineer',5000.69),
(2,'Alisha Manandhar','Chhetrapati','9812345678','Kathmandu',32,'Manager',65000.00),
(3,'Ansu Hada','Dillibazaar','9823456789','Kathmadu',30,'Accountant',4000.00),
(4,'Anush Shrestha','Dharan','9809876543','Dhulikhel',27,'Marketing Executive',2000.00),
(5,'Ashlesha Shrestha','Bhagwanpau','9845678901','Kathmadu',35,'Project Manager',7500.00),
(6,'Ayush Shah Rauniyar','Bangemuda','9812345609','Kathmadu',29,'Graphic Designer',4600.00),
(7,'Ayush Tuladhar','Chagal','9801122334','Hetauda',31,'Data Analyst',6000.00),
(8,'Barsha Pandey','Kanpur','9856789012','Biratnagar',26,'Manager',42000.00),
(9,'Diya Gartaula','Dharan','9819988776','Sunsari',33,'Network Engineer',7000.00),
(10,'Gaurav Thapa','Kapan','9804433221','Kathmandu',24,'Customer Support',4000.00),
(11,'Hrikesh Aran','Birgunj','9844556677','Birgunj',37,'Sales Manager',7000.00),
(12,'Kamana Shrestha','Kirtipur','9807766554','Kathmandu',28,'Accountant',4800.00),
(13,'Krish Ghale','Dhangadhi','9822334455','Dhangadhi',29,'Developer',53000.00),
(14,'Krishma Maharjan','Janakpur','9855443322','Janakpur',27,'Teacher',4000.00),
(15,'Manila Aryal','Itahari','9806677889','Itahari',35,'Project Coordinator',6000.00),
(16,'Sangam Adhikari','Dharan','9811223344','Dhankuta',31,'Designer',4500.00),
(17,'Shrisha Tuladhar','Damakpur','9844112233','Damak',30,'System Admin',6000.00),
(18,'Sudip Khadka','Dharan','9809988776','Tansen',26,'Manager',4700.00),
(19,'Upendra Raj Panta','Palpapur','9812345670','Palpa',34,'Finance Officer',6900.00);

SELECT * FROM Employee WHERE Eaddress=Ecity;