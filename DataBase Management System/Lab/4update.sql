/*Lab 4:
1. Write SQL query to update data whsoe id is 1.
2. Write SQL code to change address into 'London' whose name is 'Dilip'.
3. Write SQL code to modify name whose id is 7.
4. Write SQL code to display records from Employee table using aggregation formula (SUM,MIN,MAX,AVG,COUNT).
*/

USE Swarnim;

UPDATE Employee SET Fname='Dilip' , Esalary=6969.69 WHERE Eid=1;

UPDATE Employee SET Eaddress='London' WHERE Fname='Dilip';

UPDATE Employee SET Fname='Heung-Min Son' WHERE Eid=7;

SELECT * FROM Employee;

SELECT 
	COUNT(*) AS TotalEmployees,
	SUM(Esalary) AS TotalSalary,
	AVG(Esalary) AS AverageSalary,
	MIN(Esalary) AS MinimumSalary,
	MAX(Esalary) AS MaximumSalary
FROM Employee;
