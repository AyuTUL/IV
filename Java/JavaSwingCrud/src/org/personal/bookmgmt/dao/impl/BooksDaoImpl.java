package org.personal.bookmgmt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.personal.bookmgmt.dao.BooksDao;
import org.personal.bookmgmt.model.Books;

import static org.personal.bookmgmt.connection.ConnectionFactory.getConnection;

public class BooksDaoImpl implements BooksDao {

    @Override
    public int save(Books book) throws SQLException, ClassNotFoundException {
        String insertSQL = "insert into books (name, author, publication, price) values(?,?,?,?)";
        PreparedStatement prepareStatement = getConnection().prepareStatement(insertSQL);
        prepareStatement.setString(1, book.getName());
        prepareStatement.setString(2, book.getAuthor());
        prepareStatement.setString(3, book.getPublication());
        prepareStatement.setDouble(4, book.getPrice());
        return prepareStatement.executeUpdate();
    }

    @Override
    public int update(Books book, int id) throws SQLException, ClassNotFoundException {
        String updateSQL = "update books set name = ?, author = ?,"
                + " publication = ?, price = ? where id = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(updateSQL);
        preparedStatement.setString(1, book.getName());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getPublication());
        preparedStatement.setDouble(4, book.getPrice());
        preparedStatement.setInt(5, book.getId());
        return preparedStatement.executeUpdate();
    }

    @Override
    public int remove(int id) throws SQLException, ClassNotFoundException {
        String deleteSQL = "delete from books where id = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(deleteSQL);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate();
    }

    @Override
    public Books findOne(int id) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("select *from books where id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Books book = new Books();
        while (resultSet.next()) {
            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthor(resultSet.getString("author"));
            book.setPublication(resultSet.getString("publication"));
            book.setPrice(resultSet.getDouble("price"));
        }
        return book;
    }

    @Override
    public List<Books> findAll() throws SQLException, ClassNotFoundException {
        List<Books> books = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("select *from books");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Books book = new Books();
            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthor(resultSet.getString("author"));
            book.setPublication(resultSet.getString("publication"));
            book.setPrice(resultSet.getDouble("price"));
            books.add(book);
        }
        return books;
    }

    @Override
    public List<Books> search(String query) throws SQLException, ClassNotFoundException {
        List<Books> books = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection().prepareStatement(
                "select *from books where name like concat ('%' ? '%')"
                + "or author like concat ('%' ? '%') "
                + "or publication like concat ('%' ? '%') "
                + "or cast(id as char) like ('%' ? '%') "
                + "or cast(price as char) like ('%' ? '%')");
        preparedStatement.setString(1, query);
        preparedStatement.setString(2, query);
        preparedStatement.setString(3, query);
        preparedStatement.setString(4, query);
        preparedStatement.setString(5, query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Books book = new Books();
            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthor(resultSet.getString("author"));
            book.setPublication(resultSet.getString("publication"));
            book.setPrice(resultSet.getDouble("price"));
            books.add(book);
        }
        return books;
    }
    
}
