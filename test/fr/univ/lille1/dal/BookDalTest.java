/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ.lille1.dal;

import fr.univ.lille1.entities.Book;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antoine
 */
public class BookDalTest {

    /**
     * Test of init method, of class BookDal.
     */
    @Test
    public void testInit() throws Exception {
        System.out.println("init");
        BookDal instance = new BookDalImpl();
        boolean expResult = true;
        //first init
        boolean result = instance.init();
        assertEquals(expResult, result);

        //second init
        result = instance.init();
        assertEquals(false, result);
    }

    /**
     * Test of getAuthors method, of class BookDal.
     */
    @Test
    public void testGetAuthors() throws Exception {
        System.out.println("getAuthors");
        BookDal instance = new BookDalImpl();
        List<String> expResult = new ArrayList();
        List<String> result = instance.getAuthors();
        assertEquals(expResult, result);

        instance.init();
        assertEquals(2, instance.getAuthors().size());

    }

    /**
     * Test of getBooks method, of class BookDal.
     */
    @Test
    public void testGetBooks() throws Exception {
        System.out.println("getBooks");
        BookDal instance = new BookDalImpl();
        List<Book> expResult = new ArrayList<>();
        List<Book> result = instance.getBooks();
        assertEquals(expResult, result);

        instance.init();
        assertEquals(2, instance.getBooks().size());
    }

    /**
     * Test of addBook method, of class BookDal.
     */
    @Test
    public void testAddBook() throws Exception {
        System.out.println("addBook");
        String title = "test";
        String author = "test";
        int year = 0;
        BookDal instance = new BookDalImpl();
        instance.addBook(title, author, year);

        Book result = instance.getBooks().get(0);
        assertEquals(title, result.getTitle());
        assertEquals(author, result.getAuthor());
        assertEquals(year, result.getYearOfProd());
    }

    /**
     * Test of findBook method, of class BookDal.
     */
    @Test
    public void testFindBook() throws Exception {
        System.out.println("findBook");
        String title = "test";
        String author = "test";
        int year = 0;
        BookDal instance = new BookDalImpl();
        instance.addBook(title, author, year);

        Book result = instance.findBook(title);
        assertEquals(title, result.getTitle());
        assertEquals(author, result.getAuthor());
        assertEquals(year, result.getYearOfProd());
    }

    /**
     * Test of findBooks method, of class BookDal.
     */
    @Test
    public void testFindBooks() throws Exception {
        System.out.println("findBooks");
        String title = "Nov";
        String author = "test";
        int year = 0;
        BookDal instance = new BookDalImpl();

        List<Book> list = instance.findBooks(title);

        assertEquals(0, list.size());

        instance.init();
        list = instance.findBooks(title);
        assertEquals(2, list.size());
    }

    public class BookDalImpl implements BookDal {

        private List<String> authors = new ArrayList<>();
        private List<Book> books = new ArrayList<>();

        public boolean init() throws Exception {
            String ant = "antoine";
            String clem = "clement";

            if (authors.contains(ant)) {
                return false;
            }
            if (authors.contains(clem)) {
                return false;
            }

            authors.add(clem);
            authors.add(ant);

            Book b = new Book("Nova", "antoine", 2015);
            Book b1 = new Book("Nova T2", "antoine", 2015);

            if (books.contains(b)) {
                return false;
            }
            if (books.contains(b1)) {
                return false;
            }

            books.add(b);
            books.add(b1);

            return true;
        }

        public List<String> getAuthors() throws Exception {
            return authors;
        }

        public List<Book> getBooks() throws Exception {
            return books;
        }

        public void addBook(String title, String author, int year) throws Exception {
            books.add(new Book(title, author, year));
        }

        public Book findBook(String title) throws Exception {
            for (Book b : books) {
                if (b.getTitle().compareTo(title) == 0) {
                    return b;
                }
            }
            return null;
        }

        public List<Book> findBooks(String titlePart) throws Exception {
            List<Book> list = new ArrayList<>();
            for (Book b : books) {
                if (b.getTitle().contains(titlePart)) {
                    list.add(b);
                }
            }
            return list;
        }
    }

}
