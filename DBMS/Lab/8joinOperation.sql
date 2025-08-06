/*Lab 8: JOIN operation
1. Write SQL code to display records using CROSS, EQUI & SELF JOIN
*/

USE Clubs;

SELECT * FROM Arsenal AS a 
CROSS JOIN Dortmund AS d ON a.jersey=d.jersey;

SELECT a.Jersey, a.Pname AS Arsenal, d.Pname AS Dortmund,d.Pposition FROM Arsenal AS a 
JOIN Dortmund AS d ON a.jersey=d.jersey AND a.Pposition=d.Pposition;

SELECT a.* AS ARSFC,d.* AS BVB FROM Arsenal AS a ,Dortmund AS d WHERE a.jersey=d.jersey;

