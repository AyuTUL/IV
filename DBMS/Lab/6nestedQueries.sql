/*Lab 6: Nested Query
1. Create table CUSTOMER.
2. Display data whose Address is Ayush Address.
3. Display information whose Buying amount is Gaurav Buying amount.
4. Display all distinct phone number.
*/

USE Tottenham_Hotspurs;

CREATE TABLE CUSTOMER (
    Fname VARCHAR(30),
    Address VARCHAR(15),
    Phone NUMERIC(10),
    Buying_amount FLOAT(10,2)
);

INSERT INTO CUSTOMER VALUES
('Abhilekh Subedi', 'Raniban', 9801234567, 2500.50),
('Alisha Manandhar', 'Chhetrapati', 9812345678, 15000.75),
('Ansu Hada', 'Dillibazaar', 9812345678, 3200.40),
('Anush Shrestha', 'Dharan', 9809876543, 1800.00),
('Ashlesha Shrestha', 'Bhagwanpau', 9845678901, 2100.99),
('Ayush Shah Rauniyar', 'Bangemuda', 9801234567, 5400.60),
('Ayush Tuladhar', 'Chagal', 9801122334, 6500.30),
('Barsha Pandey', 'Chagal', 9801122334, 2000.75),
('Diya Gartaula', 'Dharan', 9812345678, 4300.10),
('Gaurav Thapa', 'Kapan', 9804433221, 2000.75),
('Hrikesh Aran', 'Birgunj', 9801234567, 9500.25),
('Kamana Shrestha', 'Bangemuda', 9807766554, 2000.75),
('Krish Ghale', 'Dhangadhi', 9822334455, 7000.40),
('Krishma Maharjan', 'Janakpur', 9801234567, 3000.90),
('Manila Aryal', 'Itahari', 9806677889, 4100.70),
('Sangam Adhikari', 'Dharan', 9804433221, 3800.55),
('Shrisha Tuladhar', 'Damakpur', 9801234567, 4800.60),
('Sudip Khadka', 'Dharan', 9844556677, 5000.20),
('Upendra Raj Panta', 'Palpapur', 9812345678, 5600.80);

SELECT * FROM CUSTOMER WHERE Address IN  (SELECT Address FROM CUSTOMER WHERE Fname LIKE 'Ayush%');

SELECT * FROM CUSTOMER WHERE Buying_amount=(SELECT Buying_amount FROM CUSTOMER WHERE Fname='Gaurav Thapa');

SELECT DISTINCT Phone FROM CUSTOMER;
