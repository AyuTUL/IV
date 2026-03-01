CREATE DATABASE company;

USE company;

CREATE TABLE employee (
    id INT PRIMARY KEY,
    ename VARCHAR(30),
    address VARCHAR(50),
    salary FLOAT,
    company_name VARCHAR(30),
    job_title VARCHAR(30)
);

INSERT INTO employee VALUES
(1, 'Ram', 'Kathmandu', 30000, 'ABC Corp', 'Manager'),
(2, 'Shyam', 'Lalitpur', 25000, 'XYZ Ltd', 'Marketing Executive'),
(3, 'Hari', 'Bhaktapur', 28000, 'ABC Corp', 'Mechanic'),
(4, 'Sita', 'Pokhara', 32000, 'XYZ Ltd', 'Manager'),
(5, 'Gita', 'Biratnagar', 22000, 'ABC Corp', 'Mason');

SELECT ename, salary FROM employee AS e WHERE salary > 
	(SELECT MIN(salary) FROM employee WHERE company_name = e.company_name)
AND job_title LIKE 'M%';

SELECT * FROM employee WHERE job_title = 
	(SELECT job_title FROM employee WHERE ename = 'Ram');
