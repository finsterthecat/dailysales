/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softwaremotif.control;

import com.softwaremotif.model.Mall;
import com.softwaremotif.model.Store;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
    @PersistenceContext(unitName = "com.brouwer_datascape_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    private static final Logger LOG = LoggerFactory.getLogger(StoreFacade.class);
    
    @Inject
    MallFacade mallFacade;
    
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
        mall.addStore(store);
        this.create(store);
        mallFacade.edit(mall);
        LOG.debug("added " + store.getName());
    }
    
    public void removeStore(Store store) {
        LOG.debug("remove " + store.getName() + " from " + store.getMall().getName());
        store.getMall().getStores().remove(store);
        mallFacade.edit(store.getMall());
        this.remove(store);
        LOG.debug("deleted " + store.getName());
    }

}
