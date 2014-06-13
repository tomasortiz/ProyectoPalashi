/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrador
 */
public class SpringContextTest {
    
    public SpringContextTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class SpringContext.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        SpringContext result = SpringContext.getInstance();
        assertNotNull("Objeto instanciado", result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getBean method, of class SpringContext.
     */
    @Test
    public void testGetBean() {
        System.out.println("getBean");
        String bean = "DaoHibernate";
        SpringContext instance = SpringContext.getInstance();
        Object result = instance.getBean(bean);
        assertNotNull("Bean Encontrado", result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
}