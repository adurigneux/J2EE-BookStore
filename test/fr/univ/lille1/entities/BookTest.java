/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ.lille1.entities;

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
public class BookTest {
    

    /**
     * Test of getTitle method, of class Book.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");
        Book instance = new Book("Nova","author",2015);
        String expResult = "Nova";
        String result = instance.getTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTitle method, of class Book.
     */
    @Test
    public void testSetTitle() {
        System.out.println("setTitle");
        String title = "Nova";
        Book instance = new Book();
        instance.setTitle(title);
        assertEquals("Nova", instance.getTitle());
    }

    /**
     * Test of getAuthor method, of class Book.
     */
    @Test
    public void testGetAuthor() {
        System.out.println("getAuthor");
        Book instance = new Book("","antoine",0);
        String expResult = "antoine";
        String result = instance.getAuthor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAuthor method, of class Book.
     */
    @Test
    public void testSetAuthor() {
        System.out.println("setAuthor");
        String author = "antoine";
        Book instance = new Book();
        instance.setAuthor(author);
        assertEquals("antoine", instance.getAuthor());
    }

    /**
     * Test of getYearOfProd method, of class Book.
     */
    @Test
    public void testGetYearOfProd() {
        System.out.println("getYearOfProd");
        Book instance = new Book("","",2000);
        int expResult = 2000;
        int result = instance.getYearOfProd();
        assertEquals(expResult, result);
    }

    /**
     * Test of setYearOfProd method, of class Book.
     */
    @Test
    public void testSetYearOfProd() {
        System.out.println("setYearOfProd");
        int yearOfProd = 0;
        Book instance = new Book();
        instance.setYearOfProd(yearOfProd);
        assertEquals(0, instance.getYearOfProd());
    }
    
}
