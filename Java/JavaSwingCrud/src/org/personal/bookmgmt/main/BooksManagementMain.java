package org.personal.bookmgmt.main;

import java.util.LinkedList;
import org.personal.bookmgmt.dao.BooksDao;
import org.personal.bookmgmt.dao.impl.BooksDaoImpl;
import org.personal.bookmgmt.dao.impl.BooksDaoListImpl;
import org.personal.bookmgmt.ui.Dashboard;

public class BooksManagementMain {
    
    public static void main(String[] args) {
//        BooksDao booksDao = new BooksDaoListImpl(new LinkedList<>());
        BooksDao booksDao = new BooksDaoImpl();
        Dashboard dashboard = new Dashboard(booksDao);
        dashboard.setTitle("Books Management System");
        dashboard.setLocationRelativeTo(null);
        dashboard.setVisible(true);
    }
}
