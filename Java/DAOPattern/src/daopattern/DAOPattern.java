package daopattern;

import java.util.ArrayList;
import java.util.List;

class Book {

    private int id;

    private String name;

    private String author;

    private Double price;

    public Book() {
    }

    public Book(String name, String author, Double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public Book(int id, String name, String author, Double price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", name=" + name + ", author=" + author + ", price=" + price + '}';
    }

}

public class DAOPattern {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        // save 
        books.add(new Book(1, "ABC", "XYZ", 90.0));
        books.add(new Book(2, "AABC", "XXXYZ", 190.0));
        books.add(new Book(3, "AAABC", "XXXXYZ", 190.0));

        // display 
        for (Book book : books) {
            System.out.println(book);
        }

        // update 
        int index = books.indexOf(books.get(0));
        Book updateBook = books.get(index);
        updateBook.setId(1);
        updateBook.setName("FGH");
        updateBook.setAuthor("ABCX");
        updateBook.setPrice(100.0);

        System.out.println("----Update-------");
        for (Book book : books) {
            System.out.println(book);
        }

        books.remove(index);

        System.out.println("----Delete-------");
        for (Book book : books) {
            System.out.println(book);
        }
    }

}
