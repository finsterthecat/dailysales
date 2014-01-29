/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softwaremotif.view;

import com.softwaremotif.control.MallFacade;
import com.softwaremotif.control.MonthlySalesFacade;
import com.softwaremotif.control.StoreFacade;
import com.softwaremotif.model.Mall;
import com.softwaremotif.model.MonthlySales;
import com.softwaremotif.model.Store;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author tonybrouwer
 */
@ViewScoped
@Named
public class MallBean implements Serializable {
    @EJB
    MallFacade mallFacade;
    @EJB
    StoreFacade storeFacade;
    @EJB
    MonthlySalesFacade monthlySalesFacade;
    
    private Mall currentMall = null;
    private Store currentStore = null;
    private MonthlySales currentMonthlySales;
    
    enum SalesView {
        MALLS, STORES, SALES, SALESDIALOG;
    };
    
    SalesView currentView = SalesView.MALLS;
    
    public List<Mall> findMalls() {
        return mallFacade.findAll();
    }
    
    public Mall findMall(int id) {
        currentMall = mallFacade.find(id);
        return currentMall;
    }
    
    public Mall prepareNewMall() {
        currentMall = new Mall();
        return currentMall;
    }
    
    public void setCurrentMall(Mall mall) {
        currentMall = mall;
    }
    
    public Mall getCurrentMall() {
        if (currentMall == null) {
            currentMall = new Mall();
        }
        return currentMall;
    }
    
    public void removeMall(Mall mall) {
        showMessage(mall.getName(), "Mall Deleted");
        mallFacade.remove(mall);
    }
    
    public void createMall(Mall mall) {
        showMessage(mall.getName(), "Mall Created");
        mall.setId(null);
        mallFacade.create(mall);
    }
    
    public void updateMall(Mall mall) {
        showMessage(mall.getName(), "Mall Updated");
        mallFacade.edit(mall);
    }
    
    private void showMessage(String name, String text) {
        FacesMessage msg = new FacesMessage(text, name);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<Store> findStores() {
        return storeFacade.findByMall(getCurrentMall());
    }
    
    public Store findStore(int id) {
        currentStore = storeFacade.find(id);
        return currentStore;
    }
    
    public Store prepareNewStore() {
        currentStore = new Store();
        return currentStore;
    }
    
    public void setCurrentStore(Store store) {
        currentStore = store;
    }
    
    public Store getCurrentStore() {
        if (currentStore == null) {
            currentStore = new Store();
        }
        return currentStore;
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

    public void onMallSelect(SelectEvent event) {
        showMessage(((Mall) event.getObject()).getName(), "Mall Selected");
        currentView = SalesView.STORES;
    }
    
    public void onMallUnselect(UnselectEvent event) {
        showMessage(((Mall) event.getObject()).getName(), "Mall Unselected");
    }

    public void onStoreSelect(SelectEvent event) {
        showMessage(((Store) event.getObject()).getName(), "Store Selected");
        currentView = SalesView.SALES;
    }
    
    public void onStoreUnselect(UnselectEvent event) {
        showMessage(((Store) event.getObject()).getName(), "Store Unselected");
    }

    public void onMonthlySalesSelect(SelectEvent event) {
        showMessage(((MonthlySales) event.getObject()).getId().toString(), "Sales Selected");
        currentView = SalesView.SALESDIALOG;
    }
    
    public void onMonthlySalesUnselect(UnselectEvent event) {
        showMessage(((MonthlySales) event.getObject()).getId().toString(), "Store Unselected");
    }
    
    public void navHome() {
        currentView = SalesView.MALLS;
    }
    
    public boolean isMallsVisible() {
        return currentView == SalesView.MALLS;
    }
    
    public boolean isStoresVisible() {
        return currentView == SalesView.STORES;
    }
    
    public boolean isSalesVisible() {
        return currentView == SalesView.SALES || currentView == SalesView.SALESDIALOG;
    }
    
    public boolean isSalesDialogVisible() {
        return currentView == SalesView.SALESDIALOG;
    }
    
    public List<MonthlySales> findMonthlySales() {
        return monthlySalesFacade.findByStore(currentStore);
    }
    
    public MonthlySales findMonthlySale(int id) {
        currentMonthlySales = monthlySalesFacade.find(id);
        return currentMonthlySales;
    }
    
    public MonthlySales prepareNewMonthlySale() {
        currentMonthlySales = new MonthlySales();
        currentMonthlySales.setStore(currentStore);
        return currentMonthlySales;
    }
    
    public void setCurrentMonthlySales(MonthlySales mall) {
        currentMonthlySales = mall;
    }
    
    public MonthlySales getCurrentMonthlySales() {
        if (currentMonthlySales == null) {
            currentMonthlySales = new MonthlySales();
        }
        return currentMonthlySales;
    }
    
    public void removeMonthlySales(MonthlySales monthlySale) {
        showMessage(monthlySale.getId().toString(), "MonthlySales Deleted");
        monthlySalesFacade.remove(monthlySale);
    }
    
    public void createMonthlySales() {
        //showMessage(monthlySale.getId().toString(), "MonthlySales Created");
        currentMonthlySales.setId(null);
        currentMonthlySales.setStore(currentStore);
        monthlySalesFacade.create(currentMonthlySales);
    }
    
    public void updateMonthlySales(MonthlySales mall) {
        showMessage(mall.getId().toString(), "MonthlySales Updated");
        monthlySalesFacade.edit(mall);
    }
    


}
