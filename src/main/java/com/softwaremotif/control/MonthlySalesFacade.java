/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softwaremotif.control;

import com.softwaremotif.model.MonthlySales;
import com.softwaremotif.model.Store;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author tonybrouwer
 */
@Stateless
public class MonthlySalesFacade extends AbstractFacade<MonthlySales> {
    @PersistenceContext(unitName = "com.brouwer_datascape_war_1.0-SNAPSHOTPU")
    private EntityManager em;

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
}
