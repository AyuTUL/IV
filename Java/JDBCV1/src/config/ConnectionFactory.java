/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import model.Book;

/**
 *
 * @author nischalshaky
 */
public class ConnectionFactory {

    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/books_demo",
                "root",
                ""
        );
    }

    // insert, update, delete and select
    public static void insert(Book book) {
        // query -> insert into books (name, author, publication, price) values ('', '', '', 90.0);
        String query = "insert into books (name, author, publication, price) values (?, ?, ?, ?)";
        try {
            // connection established
            Connection connection = getConnection();

            // query prepare, value binding
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getPublication());
            ps.setDouble(4, book.getPrice());

            // execute
            int row = ps.executeUpdate();
            System.out.println(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void findAll(){
        String query = "select *from books";
        try {
            // connection established
            Connection connection = getConnection();

            // query prepare, value binding
            PreparedStatement ps = connection.prepareStatement(query);
            
           

            // execute
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                String publication = rs.getString("publication");
                Double price = rs.getDouble("price");
                
                Book book = new Book(id, name, author, publication, price);
                System.out.println(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insert(new Book("ABC", "XYZ", "123abc", 90.0));
findAll();
    }

}
