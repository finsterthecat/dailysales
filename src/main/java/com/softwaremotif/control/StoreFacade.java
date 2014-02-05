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
    
    private static final Logger _log = LoggerFactory.getLogger(StoreFacade.class);

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StoreFacade() {
        super(Store.class);
    }

    public List<Store> findByMall(Mall mall) {
        _log.debug("findByMall!!!!!!");
        if (mall == null || mall.getId() == null) {
            return super.findAll();
        }
        TypedQuery<Store> q = em.createNamedQuery("Store.findByMall", Store.class);
        q.setParameter("id", mall.getId());
        return q.getResultList();
    }

}
