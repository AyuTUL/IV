/*Lab 12: View
i) Create view from Employee_details table
ii) List data from view
iii) List data items using different operator
iv) Create view from multiple table and display data
*/

CREATE DATABASE Crazy_Raccoons;
USE Crazy_Raccoons;

CREATE TABLE Employee_details(
    Emp_id INT PRIMARY KEY AUTO_INCREMENT,
    Emp_name VARCHAR(30),
    Department VARCHAR(20),
    Salary DECIMAL(10,2),
    Age INT
);

INSERT INTO Employee_details (emp_name, department, salary, age) VALUES
	('HeeSang', 'HR', 45000, 28),
	('Junbin', 'IT', 60000, 32),
	('MAX', 'Finance', 55000, 40),
	('CH0R0NG', 'IT', 75000, 29),
	('Shu', 'Developer', 750000, 20),
	('LIP', 'HR', 50000, 35);

CREATE VIEW Emp_View AS
SELECT Emp_id, Emp_name, Department, Salary
FROM Employee_details;

SELECT * FROM Emp_View;

SELECT * FROM Emp_View WHERE Salary BETWEEN 45000 AND 60000;

CREATE TABLE Department_details(
    Dept_id INT PRIMARY KEY AUTO_INCREMENT,
    Department VARCHAR(10),
    Location VARCHAR(20)
);

INSERT INTO Department_details (department, location) VALUES
('HR', 'New York'),
('IT', 'San Francisco'),
('Finance', 'Chicago');

CREATE VIEW Emp_Dept_View AS
SELECT e.Emp_id, e.Emp_name, e.Salary, d.Department, d.Location
FROM Employee_details AS e
JOIN Department_details AS d
ON e.Department = d.Department;

SELECT * FROM Emp_Dept_View;
