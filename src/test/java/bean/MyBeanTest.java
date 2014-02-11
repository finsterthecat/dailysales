/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author tonybrouwer
 */
@Ignore
public class MyBeanTest {
    
    public MyBeanTest() {
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
     * Test of addNumbers method, of class MyBean.
     */
    @Test
    public void testAddNumbers() throws Exception {
        System.out.println("addNumbers");
        int numberA = 1;
        int numberB = 2;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        MyBean instance = (MyBean)container.getContext().lookup("java:global/classes/MyBean");
        int expResult = 3;
        int result = instance.addNumbers(numberA, numberB);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
