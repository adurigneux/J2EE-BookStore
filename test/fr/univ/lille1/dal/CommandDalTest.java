/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ.lille1.dal;

import fr.univ.lille1.entities.Book;
import fr.univ.lille1.entities.Client;
import fr.univ.lille1.entities.Command;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antoine
 */
public class CommandDalTest {

    /**
     * Test of addToCart method, of class CommandDal.
     */
    @Test
    public void testAddToCart() throws Exception {
        System.out.println("addToCart");
        String titre = "antoine";
        CommandDal instance = new CommandDalImpl();
        instance.addToCart(titre);
        List<Book> result = instance.getCart();
        assertEquals(1, result.size());
        
        assertEquals(titre, result.get(0).getTitle());

    }

    /**
     * Test of removeFromCart method, of class CommandDal.
     */
    @Test
    public void testRemoveFromCart() throws Exception {
        System.out.println("removeFromCart");
        String titre = "";
        CommandDal instance = new CommandDalImpl();

        instance.addToCart("testtitre");
        List<Book> result = instance.getCart();
        assertEquals(1, result.size());

        instance.removeFromCart("testtitre");
        result = instance.getCart();
        assertEquals(0, result.size());
    }

    /**
     * Test of saveCommand method, of class CommandDal.
     */
    @Test
    public void testSaveCommand() throws Exception {
        System.out.println("saveCommand");
        Client client = null;
        CommandDal instance = new CommandDalImpl();

        instance.addToCart("testtitre");
        List<Book> result = instance.getCart();
        assertEquals(1, result.size());

        instance.saveCommand(client);

        int expResult = 0;
        int resultnb = instance.getNbBooks();
        assertEquals(expResult, resultnb);

    }

    /**
     * Test of getNbBooks method, of class CommandDal.
     */
    @Test
    public void testGetNbBooks() throws Exception {
        System.out.println("getNbBooks");
        CommandDal instance = new CommandDalImpl();
        int expResult = 0;
        int result = instance.getNbBooks();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCart method, of class CommandDal.
     */
    @Test
    public void testGetCart() throws Exception {
        System.out.println("getCart");
        CommandDal instance = new CommandDalImpl();
        List<Book> expResult = new ArrayList<>();
        List<Book> result = instance.getCart();
        assertEquals(0, result.size());

        instance.addToCart("testtitre");
        result = instance.getCart();
        assertEquals(1, result.size());

        assertEquals("testtitre", result.get(0).getTitle());

    }

    public class CommandDalImpl implements CommandDal {

        private List<Book> books = new ArrayList<>();
        private Client client = new Client();

        public void addToCart(String titre) throws Exception {
            books.add(new Book(titre, "", 1));
        }

        public void removeFromCart(String titre) throws Exception {
            
            List<Book> copy = new ArrayList<>(books);
            
            for (Book b : copy) {
                if (b.getTitle().compareTo(titre) == 0) {
                    books.remove(b);
                }
            }
        }

        public List<Command> getCommands() throws Exception {
            return new ArrayList<>();
        }

        public void saveCommand(Client client) throws Exception {
            this.client = new Client("antoine", "test");
            this.books.clear();
        }

        public int getNbBooks() throws Exception {
            return books.size();
        }

        public List<Book> getCart() throws Exception {
            return books;
        }

        public void endSession() throws Exception {
        }
    }

}
