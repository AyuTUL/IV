/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.personal.booksmgmt.dao.impl;

import java.util.List;
import org.personal.booksmgmt.dao.BookDao;
import org.personal.booksmgmt.exception.BookNotFoundException;
import org.personal.booksmgmt.model.Book;

public class BookDaoImpl implements BookDao {

    private final List<Book> books;

    public BookDaoImpl(List<Book> books) {
        this.books = books;
    }

    private static int id = 0;

    @Override
    public Book save(Book book) {
        id += 1;
        book.setId(id);
        books.add(book);
        return book;
    }

    @Override
    public Book update(int index, Book book) {
        books.set(index, book);
        return book;
    }

    @Override
    public int remove(int id) {
        Book removeBook = findOne(id);
        books.remove(removeBook);
        return 1;
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Book findOne(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        throw new BookNotFoundException("Book with given " + id + " is missing");
    }

    @Override
    public int findIndex(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return books.indexOf(book);
            }
        }
        return -1;
    }

}
