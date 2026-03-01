CREATE DATABASE Bank;

USE Bank;

CREATE TABLE Customer (
	CustomerID INT(5) PRIMARY KEY,
	CustomerName VARCHAR(30),
	Address VARCHAR(15),
	Phone NUMERIC(10),
	Email TEXT
);

CREATE TABLE Loan (
	LoanNumber INT(5) PRIMARY KEY,
	LoanType VARCHAR(10),
	Amount FLOAT(10,2)
);

CREATE TABLE Borrows (
    CustomerID INT(5),
    LoanNumber INT(5),
    PRIMARY KEY (CustomerID, LoanNumber),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (LoanNumber) REFERENCES Loan(LoanNumber)
);


INSERT INTO Customer VALUES
(10001, 'John Doe', 'Lalitpur', 5551234567, 'johndoe@example.com'),
(10002, 'Jane Smith', 'Lalitpur', 5552345678, 'janesmith@example.com'),
(10003, 'Mark Johnson', '789 Pine Rd', 5553456789, 'markj@example.com'),
(10004, 'Emily Davis', '101 Maple Dr', 5554567890, 'emilydavis@example.com'),
(10005, 'David Lee', 'Lalitpur', 5555678901, 'davidlee@example.com');

INSERT INTO Loan VALUES
(20001, 'Personal', 500000.00),
(20002, 'Home', 1500000.00),
(20003, 'Car', 1200000.00),
(20004, 'Personal', 3050000.00),
(20005, 'Student', 80000.00),
(20006, 'Home', 2000000.00);

INSERT INTO Borrows VALUES
(10001, 20001),
(10002, 20002),
(10002, 20003),
(10003, 20004),
(10004, 20005),
(10005, 20006);

DELETE FROM Customer;
DELETE FROM Loan;
DELETE FROM Borrows;

SELECT CustomerName FROM Customer WHERE Address='Lalitpur' ORDER BY CustomerName ASC; 

SELECT COUNT(DISTINCT CustomerID) AS TotalCustomersWithLoans
FROM Borrows;

SELECT DISTINCT c.CustomerName
FROM Customer AS c
JOIN Borrows AS b ON c.CustomerID = b.CustomerID
JOIN Loan AS l ON b.LoanNumber = l.LoanNumber
WHERE l.Amount >= 500000;

SELECT LoanType, AVG(Amount) AS AvgLoanAmount
FROM Loan
GROUP BY LoanType;


DROP DATABASE Bank;
