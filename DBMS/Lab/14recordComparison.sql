/*Lab 14: Record Comparison
i) Display records from one table that is not present in another table.
ii) Determine 3rd highest marks without using TOP/LIMIT keyword.
*/

CREATE DATABASE T1;
USE T1;

CREATE TABLE student (
    roll INT PRIMARY KEY,
    sname VARCHAR(20),
    marks DECIMAL(5,2),
    major VARCHAR(10)
);

CREATE TABLE teacher (
    tid INT PRIMARY KEY,
    tname VARCHAR(20),
    salary DECIMAL(10,2),
    department VARCHAR(10)
);

INSERT INTO student VALUES
(1, 'D0NGHAK', 85.50, 'Zoology'),
(2, 'Proud', 92.00, 'Physics'),
(3, 'skewed', 78.00, 'English'),
(4, 'Viper', 88.50, 'Zoology'),
(5, 'Zest', 92.00, 'Math'),
(6, 'vigilante', 75.00, 'History');

INSERT INTO teacher VALUES
(1, 'Fleta', 50000.00, 'Math'),
(2, 'Rush', 55000.00, 'Physics'),
(3, 'Avast', 48000.00, 'Chemistry');

SELECT * FROM student AS s
WHERE NOT EXISTS (
    SELECT 1 FROM teacher AS t WHERE t.department = s.major
);

SELECT DISTINCT roll, sname, marks AS 3rd_Highest_Marks, major FROM student s1
WHERE 3 = (
    SELECT COUNT(DISTINCT marks) FROM student s2
    WHERE s2.marks >= s1.marks
);
