package com.brouwersystems.testing;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class TestingUtil {

    @PersistenceContext
    public EntityManager em;
    
    private static final Logger LOG = LoggerFactory.getLogger(TestingUtil.class);
    
    public TestingUtil() {
    }
    
    /**
     * Runs arbitrary set of sqlCommands, then flushes the cache so that JPA
     * will go to the db for future requests.
     * @param sqlCommands 
     */
    public void runSqlCommands(List<String> sqlCommands) {
        for (String sql : sqlCommands) {
            em.createNativeQuery(sql).executeUpdate();
        }
        em.getEntityManagerFactory().getCache().evictAll();
    }
}
