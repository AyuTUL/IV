/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.personal.booksmgmt.dao;

import java.util.List;
import org.personal.booksmgmt.model.Book;

/**
 *
 * @author nischalshaky
 */
public interface BookDaoIO {
    
    List<Book> findAll();
    
    void saveAll(List<Book> books);
    
}
