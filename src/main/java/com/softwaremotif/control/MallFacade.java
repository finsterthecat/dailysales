/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softwaremotif.control;

import com.softwaremotif.model.Mall;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tonybrouwer
 */
@Stateless
public class MallFacade extends AbstractFacade<Mall> {
    @PersistenceContext(unitName = "com.brouwer_datascape_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MallFacade() {
        super(Mall.class);
    }
    
}
