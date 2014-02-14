/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brouwersystems.control;

import com.brouwersystems.model.Mall;
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
public class MallFacade extends AbstractFacade<Mall> {

    @PersistenceContext(unitName = "com.brouwer_datascape_war_1.0-SNAPSHOTPU")
    EntityManager em;

    private static final Logger LOG = LoggerFactory.getLogger(MallFacade.class);

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MallFacade() {
        super(Mall.class);
    }
}
