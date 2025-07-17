/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.personal.booksmgmt.main;

import java.util.ArrayList;
import java.util.List;
import org.personal.booksmgmt.dao.BookDao;
import org.personal.booksmgmt.dao.BookDaoIO;
import org.personal.booksmgmt.dao.impl.BookDaoImpl;
import org.personal.booksmgmt.dao.impl.BookDaoIoImpl;
import org.personal.booksmgmt.model.Book;

/**
 *
 * @author nischalshaky
 */
public class Main {

    public static void operation(List<Book> books) {
        BookDao bookDao = new BookDaoImpl(books);

        // save
        bookDao.save(new Book("ABC", "XYZ", 100.0));
        bookDao.save(new Book("ABCDE", "XXYZ", 200.0));
        bookDao.save(new Book("ABCDEF", "XXXYZ", 300.0));
        bookDao.save(new Book("ABCDEFG", "XXXXYZ", 400.0));

        // display
        for (Book book : bookDao.findAll()) {
            System.out.println(book.toString());
        }

        // update
        Book bookToUpdate = bookDao.findOne(1);
        int index = bookDao.findIndex(1);
        System.out.println("Book to update " + bookToUpdate.toString());
        bookToUpdate.setAuthor("GHF");
        bookToUpdate.setName("Hello world");
        bookToUpdate.setPrice(1000.0);
        bookDao.update(index, bookToUpdate);
        System.out.println("---Update-----");
        // display
        for (Book book : bookDao.findAll()) {
            System.out.println(book.toString());
        }

        bookDao.remove(1);
        System.out.println("---Remove----");
        for (Book book : bookDao.findAll()) {
            System.out.println(book.toString());
        }
    }

    public static void main(String[] args) {
//        operation(new ArrayList<>());
//        operation(new LinkedList<>());

        BookDaoIO bookDaoIO = new BookDaoIoImpl();
        List<Book> result = bookDaoIO.findAll();
        bookDaoIO.saveAll(result);
    }

}
