/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brouwersystems.control;

import com.brouwersystems.model.Mall;
import com.brouwersystems.model.Store;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
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
public class StoreFacadeIT {

    private static EJBContainer container;
    private static StoreFacade storeFacade;
    private static MallFacade mallFacade;
    
    
    public StoreFacadeIT() {
    }
    
    @BeforeClass
    public static void setUpClass() throws NamingException {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        storeFacade = (StoreFacade)container.getContext().lookup("java:global/classes/StoreFacade");
        mallFacade = (MallFacade)container.getContext().lookup("java:global/classes/MallFacade");
        System.out.println("Opening the container");
    }
    
    @AfterClass
    public static void tearDownClass() {
        container.close();
        System.out.println("Closing the container");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class StoreFacade.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Mall mall = mallFacade.find(100000L);
        Store entity = new Store();
        entity.setName("Waldo");
        entity.setMall(mall);
        storeFacade.create(entity);
        System.out.println("created with id:" + entity.getId());
        assertNotNull(entity.getId());
    }

    /**
     * Test of findByMall method, of class StoreFacade.
     */
    @Test
    public void testFindByMall() throws Exception {
        System.out.println("findByMall");
        Mall mall = new Mall(100000L);
        List<Store> result = storeFacade.findByMall(mall);
        assertEquals(4, result.size());
    }

    /**
     * Test of removeStore method, of class StoreFacade.
     * addStore does more than just add a store
     */
    @Test
    public void testRemoveStore() throws Exception {
        System.out.println("removeStore");
        Store store = new Store();
        store.setName("Waldo3");
        Mall mall = mallFacade.find(100000L);
        storeFacade.addStore(mall, store);
        assertNotNull(store.getId());
        Long id = store.getId();
        storeFacade.removeStore(store);
        Store store2 = storeFacade.find(id);
        assertNull(store2);
    }
    
}
