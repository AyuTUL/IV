package org.personal.bookmgmt.dao;

import java.sql.SQLException;
import java.util.List;
import org.personal.bookmgmt.model.Books;

public interface BooksDao {

    int save(Books book) throws SQLException, ClassNotFoundException;

    int update(Books book, int id) throws SQLException, ClassNotFoundException;

    int remove(int id) throws SQLException, ClassNotFoundException;

    Books findOne(int id) throws SQLException, ClassNotFoundException;

    List<Books> findAll() throws SQLException, ClassNotFoundException;

    List<Books> search(String query) throws SQLException, ClassNotFoundException;
}
