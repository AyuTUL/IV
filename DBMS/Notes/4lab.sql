/*1. Write SQL query to update data whsoe id is 1.
2. Write SQL code to change address into 'London' whose name is 'Dilip'.
3. Write SQL code to modify name whose id is 7.
4. Write SQL code to display records from Employee table using aggregation formula (SUM,MIN,MAX,AVG,COUNT).
*/

UPDATE Employee SET Fname='Dilip' , Esalary=6969.69 WHERE Eid=1;

UPDATE Employee SET Eaddress='London' WHERE Fname='Dilip';

UPDATE Employee SET Fname='Heung-Min Son' WHERE Eid=7;

SELECT SUM(Esalary) AS Total_salary FROM Employee;
SELECT MIN(Esalary) AS Minimum_salary FROM Employee;
SELECT MAX(Esalary) AS Maximum_salary FROM Employee;
SELECT AVG(Esalary) AS Average_salary FROM Employee;
SELECT COUNT(Esalary) AS Total_employees FROM Employee;
