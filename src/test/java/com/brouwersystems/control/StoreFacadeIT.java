/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brouwersystems.control;

import com.brouwersystems.model.Mall;
import com.brouwersystems.model.Store;
import com.brouwersystems.testing.SqlBatchManager;
import com.brouwersystems.testing.TestingUtilities;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tonybrouwer
 */
public class StoreFacadeIT {

    private static EJBContainer container;
    private static StoreFacade storeFacade;
    private static MallFacade mallFacade;
    private static SqlBatchManager sqlBatchManager;

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(StoreFacadeIT.class);

    public StoreFacadeIT() {
    }

    @BeforeClass
    public static void setUpClass() throws NamingException {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        storeFacade = (StoreFacade) container.getContext().lookup("java:global/classes/StoreFacade");
        mallFacade = (MallFacade) container.getContext().lookup("java:global/classes/MallFacade");
        sqlBatchManager = (SqlBatchManager) container.getContext().lookup("java:global/classes/SqlBatchManager");
        sqlBatchManager.loadSqlCommands("/tmp/dailysalesdb/data.sql");
        
        System.out.println("Opening the container");
    }

    @AfterClass
    public static void tearDownClass() {
        container.close();
        System.out.println("Closing the container");
    }

    @Before
    public void setUp() {
        System.out.println("--setting up");
        sqlBatchManager.executeSqlCommands();
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
        Mall mall = mallFacade.find(1L);
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
        Mall mall = new Mall(1L);
        List<Store> result = storeFacade.findByMall(mall);
        assertEquals(3, result.size());
    }

    /**
     * Test of removeStore method, of class StoreFacade. Test the myriad of
     * relationships and which ones still work after a remove...
     */
    @Test
    public void testRemoveNewStore() throws Exception {
        System.out.println("removeNewStore");
        Store store = new Store();
        store.setName("Waldo3");
        Mall mall = mallFacade.find(1L);
        assertEquals(3, mall.getStores().size());
        storeFacade.addStore(mall, store);
        assertNotNull(store.getId());
        assertEquals(4, mall.getStores().size());
        Mall dbMall = mallFacade.find(mall.getId());
        assertEquals(4, dbMall.getStores().size());
        List<Mall> malls = mallFacade.findAll();
        dbMall = malls.iterator().next();
        assertEquals(dbMall.getId().longValue(), 1L);
        assertEquals("Stores must also be updated for when retrieving list of malls",
                4, dbMall.getStores().size());
        Long id = store.getId();
        storeFacade.removeStore(store);
        Store store2 = storeFacade.find(id);
        assertNull(store2);
        mall = mallFacade.find(1L);
        assertEquals(3, mall.getStores().size());
    }

    /**
     * Test of removeStore method, of class StoreFacade. addStore does more than
     * just add a store
     */
    @Test
    public void testRemoveStore() throws Exception {
        System.out.println("removeStore");

        Mall mall = mallFacade.find(1L);
        System.out.println("Number of stores: " + mall.getStores().size());
        assertTrue("Expected three stores", mall.getStores().size() == 3);
        storeFacade.removeStore(mall.getStores().iterator().next());
        assertEquals("Deleted one store, should be two left", 2, mall.getStores().size());
        assertEquals("Deleted one store, should be two left after find", 2,
                mallFacade.find(1L).getStores().size());
    }

}
