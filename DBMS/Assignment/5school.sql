CREATE DATABASE SCHOOL_INFO;

USE SCHOOL_INFO;

CREATE TABLE TEACHER (
	TID INT(4) PRIMARY KEY,
	TName VARCHAR(30),
	TAddress VARCHAR(10),
	TQualification VARCHAR(10)
);

CREATE TABLE SCHOOL (
	SID INT(4) PRIMARY KEY,
	SName VARCHAR(30),
	SAddress VARCHAR(10),
	SPhone NUMERIC(10)
);

CREATE TABLE SCHOOL_TEACHER (
	SID INT(4),
	TID INT(4),
	No_of_Period INT(2)
);

INSERT INTO TEACHER VALUES
(1001, 'John Smith', 'New York', 'M.Sc.'),
(1002, 'Alice Brown', 'California', 'PhD'),
(1003, 'Sarah Lee', 'Texas', 'M.Ed.'),
(1004, 'David Green', 'Florida', 'B.Sc.'),
(1005, 'Emma White', 'Oregon', 'M.A.');

INSERT INTO SCHOOL VALUES
(2001, 'ABC', '101 Maple St', 5551234567),
(2002, 'Blue Ridge', '203 Oak Ave', 5552345678),
(2003, 'Riverdale', '304 Pine Rd', 5553456789),
(2004, 'Horizon High', '505 Birch Blvd', 5554567890),
(2005, 'Sunset Academy', '607 Cedar Dr', 5555678901);

INSERT INTO SCHOOL_TEACHER VALUES
(2001, 1001, 5),
(2001, 1002, 3),
(2002, 1003, 6),
(2003, 1004, 4),
(2003, 1005, 2),
(2004, 1002, 4),
(2004, 1003, 3),
(2005, 1005, 5),
(2005, 1001, 2);

SELECT T.TName,S.SName,S.SPhone FROM TEACHER T
JOIN SCHOOL_TEACHER ST ON T.TID=ST.TID
JOIN SCHOOL S ON ST.SID=S.SID
WHERE S.Sname='ABC';

DROP DATABASE SCHOOL_INFO;
