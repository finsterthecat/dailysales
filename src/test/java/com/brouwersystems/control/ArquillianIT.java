/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brouwersystems.control;

import com.brouwersystems.model.Mall;
import com.brouwersystems.model.MonthlySales;
import com.brouwersystems.model.Store;
import com.brouwersystems.testing.SqlBatchManager;
import com.brouwersystems.testing.TestingUtil;
import java.io.File;
import java.util.List;
import javax.inject.Inject;
import javax.naming.NamingException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author tonybrouwer
 */
@RunWith(Arquillian.class)
public class ArquillianIT {
    @Inject
    StoreFacade storeFacade;
    @Inject
    MallFacade mallFacade;
    
    @Inject
    TestingUtil testingUtilities;
    
    static List<String> sqlCommands;
    
    @BeforeClass
    public static void setUpClass() throws NamingException {
        String ef = System.getProperty("embeddeddb.folder");
        sqlCommands = SqlBatchManager.loadSqlCommands(ef + "/data.sql");
        
        System.out.println("Opening the container");
    }

    @Before
    public void setUp() {
        System.out.println("--setting up");
        testingUtilities.runSqlCommands(sqlCommands);
    }
    
    @Deployment
    public static JavaArchive createArchiveAndDeploy() {
        /**
         * The persistence.xml is copied by an ant task in the pom.xml from test/resources...
         * It is the JTA embedded derby version
         */
        return ShrinkWrap.create(JavaArchive.class, "arq.jar").
                addAsManifestResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("bean.xml")).
                addAsManifestResource(
                        new FileAsset(new File("target/classes/META-INF/persistence.xml"))
                        , "persistence.xml").
                addClasses(StoreFacade.class, MallFacade.class, Store.class, Mall.class, MonthlySales.class
                , SqlBatchManager.class, TestingUtil.class);
                
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
    
    @Test
    public void testFindAllStores() {
        List<Store> stores = storeFacade.findAll();
        Assert.assertEquals(3, stores.size());
        dumpStoreNames(stores);
    }

    /**
     * Test of removeStore method, of class StoreFacade. Test the myriad of
     * relationships and which ones still work after a remove...
     */
    @Test
    public void testRemoveNewStore() {
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
    public void testRemoveStore() {
        System.out.println("removeStore");

        Mall mall = mallFacade.find(1L);
        System.out.println("Number of stores: " + mall.getStores().size());
        assertTrue("Expected three stores", mall.getStores().size() == 3);
        storeFacade.removeStore(mall.getStores().iterator().next());
        assertEquals("Deleted one store, should be two left", 2, mall.getStores().size());
        assertEquals("Deleted one store, should be two left after find", 2,
                mallFacade.find(1L).getStores().size());
    }
    
    /**
     * Ruby equivalent: "Stores: [ #{stores.map{|s| s.name}.join(',')}]"
     * @param stores 
     */
    private void dumpStoreNames(List<Store> stores) {
        boolean notfirst = false;
        System.out.print("Stores: [");
        for (Store store: stores) {
            if (notfirst) {
                System.out.print(", ");
            }
            notfirst = true;
            System.out.print(store.getName());
        }
        System.out.println("]");
    }
}
