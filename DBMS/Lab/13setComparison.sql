/*Lab 13: Set comparison
i) Illustrate set comparison operation with sub-queries
ii) Use EXISTS keyword in SELECT statement to test for empty relations
*/

USE Crazy_Raccoons;

INSERT INTO Employee_details (Emp_name, Department, Salary, Age) VALUES
	('SP1NT', 'NULL', 51000, 15);

INSERT INTO Department_details (Department, Location) VALUES
	('UI', 'Illinois');

SELECT Emp_name, Salary FROM Employee_details
WHERE Salary > ANY (SELECT Salary FROM Employee_details WHERE Department = 'HR');

SELECT Emp_name, Salary FROM Employee_details
WHERE Salary > ALL (SELECT Salary FROM Employee_details WHERE Department = 'HR');

SELECT Emp_name, Department FROM Employee_details
WHERE Department IN (SELECT Department FROM Employee_details WHERE Emp_name = 'Junbin');

SELECT Emp_name, Department FROM Employee_details AS e
WHERE EXISTS (SELECT 1 FROM Department_details AS d WHERE d.Department = 'Finance');

SELECT Department FROM Department_details AS d
WHERE EXISTS (SELECT 1 FROM Employee_details AS e WHERE e.Department = d.Department);

SELECT Emp_name, Department FROM Employee_details
WHERE department NOT IN (SELECT Department FROM Department_details WHERE Department = 'IT');

SELECT department FROM Department_details AS d
WHERE NOT EXISTS (SELECT 1 FROM Employee_details AS e WHERE e.Department = d.Department);