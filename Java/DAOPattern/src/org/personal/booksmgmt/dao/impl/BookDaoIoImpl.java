/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.personal.booksmgmt.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.personal.booksmgmt.dao.BookDaoIO;
import org.personal.booksmgmt.model.Book;

/**
 *
 * @author nischalshaky
 */
public class BookDaoIoImpl implements BookDaoIO {

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("books.csv")
            );
            // read header
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitBook = line.split(",");
                int id = Integer.parseInt(splitBook[0]);
                String name = splitBook[1];
                String author = splitBook[2];
                Double price = Double.valueOf(splitBook[3]);
                Book book = new Book(id, name, author, price);
                books.add(book);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void saveAll(List<Book> books) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("book_duplicate.csv"));
            for (Book book : books) {
                writer.write(book.toString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
