/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softwaremotif.view;

import com.softwaremotif.control.StoreFacade;
import com.softwaremotif.model.Store;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author tonybrouwer
 */
@RequestScoped
@Named
public class StoreBean implements Serializable {
    @EJB
    StoreFacade storeFacade;
    
    @Inject
    MallBean mallBean;
    
    Store current = new Store();
    
    public List<Store> findAll() {
        return storeFacade.findByMall(mallBean.getCurrentMall());
    }
    
    public Store find(int id) {
        current = storeFacade.find(id);
        return current;
    }
    
    public Store prepareNewStore() {
        current = new Store();
        return current;
    }
    
    public void setCurrentStore(Store store) {
        current = store;
    }
    
    public Store getCurrentStore() {
        if (current == null) {
            current = new Store();
        }
        return current;
    }
    
    public void removeStore(Store store) {
        showMessage(store.getName(), "Store Deleted");
        storeFacade.remove(store);
    }
    
    public void createStore(Store store) {
        showMessage(store.getName(), "Store Created");
        store.setId(null);
        storeFacade.create(store);
    }
    
    public void updateStore(Store store) {
        showMessage(store.getName(), "Store Updated");
        storeFacade.edit(store);
    }
    
    private void showMessage(String name, String text) {
        FacesMessage msg = new FacesMessage(text, name);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        showMessage(((Store) event.getObject()).getName(), "Store Selected");
    }
    
    public void onRowUnselect(UnselectEvent event) {
        showMessage(((Store) event.getObject()).getName(), "Store Selected");
    }
}
