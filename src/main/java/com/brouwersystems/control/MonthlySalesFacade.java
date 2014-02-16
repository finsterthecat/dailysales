/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brouwersystems.control;

import com.brouwersystems.model.MonthlySales;
import com.brouwersystems.model.Store;
import java.util.List;
import javax.ejb.Stateless;
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
public class MonthlySalesFacade extends AbstractFacade<MonthlySales> {
    @PersistenceContext(unitName = "com.brouwer_datascape_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private static final Logger LOG = LoggerFactory.getLogger(MonthlySalesFacade.class);
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MonthlySalesFacade() {
        super(MonthlySales.class);
    }

    public List<MonthlySales> findByStore(Store store) {
        if (store == null || store.getId() == null) {
            return super.findAll();
        }
        TypedQuery<MonthlySales> q = em.createNamedQuery("MonthlySales.findByStore", MonthlySales.class);
        q.setParameter("id", store.getId());
        return q.getResultList();
    }
    
    public void addSale(Store store, MonthlySales monthlySales) {
        store.addMonthlySale(monthlySales);
        this.create(monthlySales);
        em.merge(store);
        LOG.debug("Added sale to " + store.getName());
    }
    
    public void removeSale(MonthlySales monthlySales) {
        Store store = monthlySales.getStore();
        store.getMonthlySales().remove(monthlySales);
        em.merge(store);
        LOG.debug("Removed sale from " + monthlySales.getStore().getName());
    }
}
