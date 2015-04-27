/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ.lille1.entities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antoine
 */
public class CommandIT {

    /**
     * Test of getBooks method, of class Command.
     */
    @Test
    public void testGetBooks() {
        System.out.println("getBooks");
        Command instance = new Command();
        Collection<Book> expResult = new ArrayList<>();
        Collection<Book> result = instance.getBooks();
        assertEquals(expResult, result);

    }

    /**
     * Test of setBooks method, of class Command.
     */
    @Test
    public void testSetBooks() {
        System.out.println("setBooks");
        Collection<Book> books = new ArrayList<>();
        Command instance = new Command();
        instance.setBooks(books);
        assertEquals(new ArrayList<Book>(), instance.getBooks());
    }

    /**
     * Test of getClient method, of class Command.
     */
    @Test
    public void testGetClient() {
        System.out.println("getClient");
        Command instance = new Command();
        Client expResult = new Client("antoine", "test");
        instance.setClient(new Client("antoine","test"));
        Client result = instance.getClient();
        assertEquals(expResult.getUsername(), result.getUsername());
        assertEquals(expResult.getPassword(), result.getPassword());
    }

    /**
     * Test of setClient method, of class Command.
     */
    @Test
    public void testSetClient() {
        System.out.println("setClient");
        Client client = new Client("antoine", "test");
        Command instance = new Command();
        instance.setClient(client);
        assertEquals("antoine", instance.getClient().getUsername());
        assertEquals("test",  instance.getClient().getPassword());

    }

}
