/*1. Find the employee records whose salary is greater than 30,000
2. Find the employee records whose salary is les than 25,000 and address is 'Dharan'
3. Find the employee info using IN/NOT IN operator
4. Find the empid, empname, salary & post from table who is 'Manager'
*/

USE Swarnim;

SELECT * FROM employee WHERE Esalary>30000;

SELECT * FROM employee WHERE Esalary<25000 AND Eaddress='Dharan';

SELECT Eid,Fname,Esalary,Edetails FROM employee WHERE Edetails='Manager';