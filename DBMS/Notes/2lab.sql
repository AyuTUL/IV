/*1. Find the employee records whose salary is greater than 30,000
2. Find the employee records whose salary is les than 25,000 and address is 'Dharan'
3. Find the employee info using IN/NOT IN operator
4. Find the empid, empname, salary & post from table who is 'Manager'
*/

USE Swarnim;

SELECT * FROM employee WHERE Esalary>30000;

SELECT * FROM employee WHERE Esalary<25000 AND Eaddress='Dharan';

SELECT * FROM employee WHERE Esalary IN(7000.0,4000.0,6000.0);
SELECT * FROM employee WHERE Esalary NOT IN(7000.,4000.0,6000.0,4500.0,6900.0,53000.0,65000.0);

SELECT Eid,Fname,Esalary,Edetails FROM employee WHERE Edetails='Manager';