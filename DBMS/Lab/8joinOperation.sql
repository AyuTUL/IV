/*Lab 8: JOIN operation
1. Write SQL code to display records using CROSS, EQUI & SELF JOIN
*/

USE Clubs;

SELECT * FROM Arsenal
CROSS JOIN Dortmund;

SELECT a.Pposition AS POSITION, a.Jersey, a.Pname AS Arsenal, d.Jersey, d.Pname AS Dortmund FROM Arsenal AS a 
JOIN Dortmund AS d 
ON a.Pposition=d.Pposition;

SELECT a.Jersey, a.pPosition AS POSITION, b.Pname AS Player, b.MarketValue FROM Arsenal AS a
JOIN Arsenal AS b
ON a.Jersey=b.Jersey;


