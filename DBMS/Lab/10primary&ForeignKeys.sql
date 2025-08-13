/*Lab 10: Primary and foreign key
Create table Sales and Products using key constraints(primary & foreign key), insert suitable data and perform DML operations using
SELECT, WHERE and aggregation functions
*/

CREATE DATABASE Manchester;
USE Manchester;

CREATE TABLE Products (
    product_id INT PRIMARY KEY NOT NULL,
    product_name VARCHAR(30),
    price DECIMAL(10, 2),
    stock_quantity INT
);

CREATE TABLE Sales (
    sale_id INT PRIMARY KEY NOT NULL,
    product_id INT,
    quantity_sold INT,
    sale_date DATE,
    total_sale DECIMAL(10, 2),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

INSERT INTO Products VALUES
(1, 'Laptop', 1000.00, 50),
(2, 'Smartphone', 500.00, 100),
(3, 'Headphones', 150.00, 200),
(4, 'Keyboard', 80.00, 150),
(5, 'Mouse', 40.00, 300);


INSERT INTO Sales VALUES
(1, 1, 3, '2025-08-01', 3000.00),
(2, 2, 10, '2025-08-02', 5000.00),
(3, 3, 15, '2025-08-03', 2250.00),
(4, 4, 8, '2025-08-04', 640.00),
(5, 5, 20, '2025-08-05', 800.00),
(6, 2, 5, '2025-08-06', 2500.00);

SELECT s.sale_id,p.product_name,s.quantity_sold,s.sale_date,s.total_sale
FROM Sales AS s
JOIN Products AS p 
ON s.product_id=p.product_id;

SELECT s.sale_id,p.product_name,s.quantity_sold,s.total_sale
FROM Sales AS s
JOIN Products AS p
ON s.product_id=p.product_id
WHERE s.quantity_sold>=10;

SELECT p.product_name,
       SUM(s.total_sale) AS total_sales,
       AVG(s.total_sale) AS avg_sales,
       COUNT(s.sale_id) AS no_of_sales,
       MIN(s.total_sale) AS min_sales,
       MAX(s.total_sale) AS max_sales
FROM Sales AS s
JOIN Products AS p
ON s.product_id=p.product_id
GROUP BY p.product_name;



	