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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
public class MallTest {

    private EntityTransaction transaction;
    private MallFacade mallFacade;
    private StoreFacade storeFacade;
    TestingUtilities testingUtilities;
    private EntityManager em;

    private static SqlBatchManager sqlBatchManager = null;

    public MallTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        sqlBatchManager = new SqlBatchManager();
        String ef = System.getProperty("embeddeddb.folder");
        sqlBatchManager.loadSqlCommands( ef + "/data.sql");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        System.out.println("--setting up");
        em = Persistence.createEntityManagerFactory("integration")
                .createEntityManager();
        mallFacade = new MallFacade();
        mallFacade.em = em;
        storeFacade = new StoreFacade();
        storeFacade.em = em;
        transaction = em.getTransaction();
        transaction.begin();
        testingUtilities = new TestingUtilities();
        testingUtilities.em = em;
        sqlBatchManager.testingUtilities = testingUtilities;
        sqlBatchManager.executeSqlCommands();
        transaction.commit();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Mall.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        transaction.begin();
        List<Mall> malls = mallFacade.findAll();
        transaction.commit();
        assertEquals(4, malls.size());

    }

    /**
     * Test of create method, of class StoreFacade.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        transaction.begin();
        Mall mall = mallFacade.find(1L);
        transaction.commit();
        Store entity = new Store();
        entity.setName("Waldo");
        entity.setMall(mall);
        transaction.begin();
        storeFacade.create(entity);
        transaction.commit();
        System.out.println("created with id:" + entity.getId());
        assertNotNull(entity.getId());
    }

    /**
     * Test of create method, of class StoreFacade: passing a null store
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() throws Exception {
        System.out.println("create null");
        transaction.begin();
        storeFacade.create(null);
        transaction.commit();
    }

    /**
     * Test of findByMall method, of class StoreFacade.
     */
    @Test
    public void testFindByMall() throws Exception {
        System.out.println("findByMall");
        Mall mall = new Mall(1L);
        transaction.begin();
        List<Store> result = storeFacade.findByMall(mall);
        transaction.commit();
        assertEquals(3, result.size());
    }

    @Test
    public void testAddStore() throws Exception {
        System.out.println("addStore");
        transaction.begin();
        Mall mall = mallFacade.find(1L);
        transaction.commit();
        int oldCount = mall.getStores().size();
        System.out.println("Number of stores: " + oldCount);
        Store store = new Store();
        store.setName("Big Store");
        transaction.begin();
        storeFacade.addStore(mall, store);
        transaction.commit();
        assertNotNull("After add, store must have an id", store.getId());
        assertEquals("Must be one more store for the mall after add",
                oldCount + 1, mall.getStores().size());
    }

    /**
     * Test of removeStore method, of class StoreFacade.
     */
    @Test
    public void testRemoveStore() throws Exception {
        System.out.println("removeStore");

        transaction.begin();
        Mall mall = mallFacade.find(1L);
        transaction.commit();
        System.out.println("Number of stores: " + mall.getStores().size());
        assertTrue("Expected three stores", mall.getStores().size() == 3);
        transaction.begin();
        storeFacade.removeStore(mall.getStores().iterator().next());
        transaction.commit();
        assertEquals("Deleted one store, should be two left", 2, mall.getStores().size());
        transaction.begin();
        assertEquals("Deleted one store, should be two left after find", 2,
                mallFacade.find(1L).getStores().size());
        transaction.commit();
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
        transaction.begin();
        Mall mall = mallFacade.find(1L);
        transaction.commit();
        assertEquals(3, mall.getStores().size());
        transaction.begin();
        storeFacade.addStore(mall, store);
        transaction.commit();
        assertNotNull(store.getId());
        assertEquals(4, mall.getStores().size());
        transaction.begin();
        Mall dbMall = mallFacade.find(mall.getId());
        transaction.commit();
        assertEquals(4, dbMall.getStores().size());
        transaction.begin();
        List<Mall> malls = mallFacade.findAll();
        transaction.commit();
        dbMall = malls.iterator().next();
        assertEquals(dbMall.getId().longValue(), 1L);
        assertEquals("Stores must also be updated for when retrieving list of malls",
                4, dbMall.getStores().size());
        Long id = store.getId();
        transaction.begin();
        storeFacade.removeStore(store);
        transaction.commit();
        store = null;
        transaction.begin();
        Store store2 = storeFacade.find(id);
        transaction.commit();
        assertNull(store2);
        transaction.begin();
        mall = mallFacade.find(1L);
        transaction.commit();
        assertEquals(3, mall.getStores().size());
    }

}
