package org.personal.bookmgmt.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.personal.bookmgmt.dao.BooksDao;
import org.personal.bookmgmt.model.Books;

public class BooksDaoListImpl implements BooksDao {
    
    private final List<Books> books;
    
    public BooksDaoListImpl(List<Books> books) {
        this.books =books;
        init();
    }
    
    private void init() {
        books.add(new Books(1, "Kaizen", "Sarah Harvey", "ABC", 90.0));
        books.add(new Books(2, "After Dark", "Haurki Murakami", "Fine Prints", 190.0));
        books.add(new Books(3, "Kafka on the Shore", "Haruki Murakami", "Logic prints", 250.0));
        books.add(new Books(4, "The Midnight Library", "Matt Heig", "ABC", 500.0));
    }
    
    @Override
    public int save(Books book) throws SQLException, ClassNotFoundException {
        book.setId(getLastID() + 1);
        boolean isAdd = books.add(book);
        if (isAdd) {
            return 1;
        }
        return 0;
    }
    
    @Override
    public int update(Books book, int id) throws SQLException, ClassNotFoundException {
        Books existingBook = findOne(id);
        books.set(books.indexOf(existingBook), book);
        return 1;
    }
    
    @Override
    public int remove(int id) throws SQLException, ClassNotFoundException {
        Books existingBook = findOne(id);
        books.remove(books.indexOf(existingBook));
        return 1;
    }
    
    @Override
    public Books findOne(int id) throws SQLException, ClassNotFoundException {
        for (Books book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
    
    @Override
    public List<Books> findAll() throws SQLException, ClassNotFoundException {
        return books;
    }
    
    @Override
    public List<Books> search(String query) throws SQLException, ClassNotFoundException {
        return books
                .stream()
                .filter(f -> String.valueOf(f.getId()).contains(query)
                || f.getAuthor().toLowerCase().contains(query)
                || f.getName().toLowerCase().contains(query)
                || f.getPublication().toLowerCase().contains(query)
                || String.valueOf(f.getPrice()).contains(query))
                .collect(Collectors.toList());
    }
    
    private int getLastID() {
        if (books.size() >= 1) {
            return books.get(books.size() - 1).getId();
        }
        return 1;
    }
    
}
