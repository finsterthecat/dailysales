/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brouwersystems.control;

import com.brouwersystems.model.Mall;
import com.brouwersystems.model.Store;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tonybrouwer
 */
@Stateless
@Named
public class StoreFacade extends AbstractFacade<Store> {
    @PersistenceContext
    EntityManager em;
    
    private static final Logger LOG = LoggerFactory.getLogger(StoreFacade.class);
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StoreFacade() {
        super(Store.class);
    }

    public List<Store> findByMall(Mall mall) {
        LOG.debug("findByMall");
        if (mall == null || mall.getId() == null) {
            return super.findAll();
        }
        TypedQuery<Store> q = em.createNamedQuery("Store.findByMall", Store.class);
        q.setParameter("id", mall.getId());
        return q.getResultList();
    }

    public void addStore(Mall mall, Store store) {
        LOG.debug("addStore");
        mall.addStore(store);
        this.create(store);     //Needed to generate pk
        em.merge(mall);
        LOG.trace("added " + store.getName() + " to mall " + mall.getName());
    }
    
    public void removeStore(Store store) {
        LOG.debug("removeStore");
        Mall mall = store.getMall();
        mall.removeStore(store);
        em.merge(mall);
        LOG.trace("removed " + store.getName() + " from " + mall.getName());
    }

}
