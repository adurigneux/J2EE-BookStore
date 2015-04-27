/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ.lille1.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antoine
 */
public class ClientTest {
    

    /**
     * Test of getUsername method, of class Client.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        Client instance = new Client("antoine","test");
        String expResult = "antoine";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUsername method, of class Client.
     */
    @Test
    public void testSetUsername() {
        System.out.println("setUsername");
        String username = "antoine";
        Client instance = new Client("antoine","test");
        instance.setUsername(username);
        assertEquals(username, instance.getUsername());
    }

    /**
     * Test of getPassword method, of class Client.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        Client instance = new Client("antoine","test");
        String expResult = "test";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class Client.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "test";
        Client instance = new Client("antoine","");
        instance.setPassword(password);
        assertEquals("test", instance.getPassword());
    }

    /**
     * Test of isAdmin method, of class Client.
     */
    @Test
    public void testIsAdmin() {
        System.out.println("isAdmin");
        Client instance = new Client("antoine","test");
        boolean expResult = false;
        boolean result = instance.isAdmin();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAdmin method, of class Client.
     */
    @Test
    public void testSetAdmin() {
        System.out.println("setAdmin");
        boolean admin = true;
        Client instance = new Client("antoine","test");
        instance.setAdmin(admin);
        assertTrue(instance.isAdmin());

    }

    /**
     * Test of getCommands method, of class Client.
     */
    @Test
    public void testGetCommands() {
        System.out.println("getCommands");
        Client instance = new Client("antoine","test");
        Collection<Command> expResult = new ArrayList<>();
        Collection<Command> result = instance.getCommands();
        assertEquals(expResult, result);
        assertEquals(expResult.size(), result.size());

    }

    /**
     * Test of setCommands method, of class Client.
     */
    @Test
    public void testSetCommands() {
        System.out.println("setCommands");
        Collection<Command> commands = new ArrayList<>();
        Book b = new Book("b", "author", 2);
        Command c = new Command();
        List<Book> list = new ArrayList<Book>();
        list.add(b);
        c.setBooks(list);
        Client instance = new Client("antoine","test");
        instance.setCommands(commands);

        assertEquals(commands.size(), instance.getCommands().size());

    }
    
}
