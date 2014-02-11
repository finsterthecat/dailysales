/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brouwersystems.view;

import com.brouwersystems.control.MallFacade;
import com.brouwersystems.control.MonthlySalesFacade;
import com.brouwersystems.control.StoreFacade;
import com.brouwersystems.model.Mall;
import com.brouwersystems.model.MonthlySales;
import com.brouwersystems.model.Store;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.Valid;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

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
    
    @Valid
    private Mall currentMall = new Mall();
    @Valid
    private Store currentStore = null;
    @Valid
    private MonthlySales currentMonthlySales;
    
    private MenuModel crumbs;
    
    public enum SalesView {
        MALLS(0), MALLDIALOG(1), STORES(2), STOREDIALOG(3), SALES(4), SALESDIALOG(5);
        
        private int card;
        
        private SalesView(int card) {
            this.card = card;
        }
        
        public int getCard() {
            return this.card;
        }
        
        public static SalesView forCard(int card) {
            for(SalesView sv: SalesView.values()) {
                if (sv.card == card) {
                    return sv;
                }
            }
            return MALLS;
        }
        
        public static SalesView forName(String name) {
            for(SalesView sv: SalesView.values()) {
                if (sv.toString().equals(name)) {
                    return sv;
                }
            }
            return MALLS;
        }
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
        currentView = SalesView.MALLDIALOG;
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
        showMessage(mall.getName() + " deleted", mall.getName() + " deleted");
        mallFacade.remove(mall);
    }
    
    public void cancelCreateMall() {
        showMessage("No changes made", "Cancelled");
        currentView = SalesView.MALLS;
    }
    
    public void createMall() {
        Mall mall = currentMall;
        mall.setId(null);
        try {
            mallFacade.create(mall);            
        }
        catch (RuntimeException e) {
            showMessage(FacesMessage.SEVERITY_ERROR, "dberror", "omg " + mall.getName() + " already exists");
            return;
        }
        showMessage(mall.getName() + " created", mall.getName());
        currentView = SalesView.MALLS;
    }
    
    public void updateMall(Mall mall) {
        showMessage(mall.getName(), "Mall Updated");
        mallFacade.edit(mall);
    }
    
    private void showMessage(FacesMessage.Severity severity, String name, String text) {
        FacesMessage msg = new FacesMessage(severity, name, text);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    private void showMessage(String name, String text) {
        showMessage(FacesMessage.SEVERITY_INFO, name, text);
    }
    
    public List<Store> findStores() {
        return storeFacade.findByMall(getCurrentMall());
    }
    
    public Store findStore(int id) {
        currentStore = storeFacade.find(id);
        return currentStore;
    }
    
    public Store prepareNewStore() {
        currentView = SalesView.STOREDIALOG;
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
        storeFacade.removeStore(store);
        currentStore = null;
        showMessage(store.getName() + " Deleted", store.getName() + " Deleted");
    }

    public void cancelCreateStore() {
        showMessage("No changes made", "Cancelled");
        currentView = SalesView.STORES;
    }
    
    public void createStore() {
        Store store = currentStore;
        store.setId(null);
        //currentMall.addStore(store);
        try {
            storeFacade.addStore(currentMall, store);
        }
        catch (RuntimeException e) {
            showMessage(FacesMessage.SEVERITY_ERROR, "omg " + store.getName() + " already exists", "omg " + store.getName() + " already exists");
            return;
        }
        showMessage(store.getName() + " created", store.getName());
        currentView = SalesView.STORES;
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
    }
    
    public void onMonthlySalesUnselect(UnselectEvent event) {
        showMessage(((MonthlySales) event.getObject()).getId().toString(), "Store Unselected");
    }
    
    public void navHome() {
        currentView = SalesView.MALLS;
    }

    public void nav(String view) {
        currentView = SalesView.forName(view);
    }
    
    public MenuModel getCrumbs() {
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        ELContext elCtx = facesCtx.getELContext();
        ExpressionFactory expFact = facesCtx.getApplication().getExpressionFactory();

        crumbs = new DefaultMenuModel();
        
        MenuItem item = new MenuItem();
        item.setValue("Malls");
        item.setActionExpression(expFact.createMethodExpression(elCtx,
                "#{mallBean.navHome}",
                null,
                new Class[0]));
        item.setUpdate(":mallform:mallPanel :mallform:storePanel :mallform:salesPanel :mallform:salesDialog :mallform:mallDialog :mallform:storeDialog :mallform:crumbs");
        item.setProcess("@this");
        crumbs.addMenuItem(item);
        
        if (currentView.card > SalesView.MALLDIALOG.getCard()) {
           item = new MenuItem();
           item.setValue("Mall: " + currentMall.getName());
           item.setActionExpression(expFact.createMethodExpression(elCtx,
                   "#{mallBean.nav('STORES')}",
                   null,
                   new Class[]{Integer.class}));
           item.setProcess("@this");
           if (currentView.getCard() <= SalesView.STORES.getCard()) {
               item.setDisabled(true);
           }
           item.setUpdate(":mallform:mallPanel :mallform:storePanel :mallform:salesPanel :mallform:table :mallform:mallDialog :mallform:storeDialog :mallform:salesDialog :mallform:crumbs");
           crumbs.addMenuItem(item);
        }
        if (currentView.card > SalesView.STOREDIALOG.getCard()) {
           item = new MenuItem();
           item.setValue("Store: " + currentStore.getName());
           item.setActionExpression(expFact.createMethodExpression(elCtx,
                   "#{mallBean.nav('SALES')}",
                   null,
                   new Class[]{Integer.class}));
           item.setProcess("@this");
           if (currentView == SalesView.SALES) {
               item.setDisabled(true);
           }
           item.setUpdate(":mallform:mallPanel :mallform:storePanel :mallform:salesPanel :mallform:salesDialog  :mallform:storeDialog :mallform:crumbs");
           crumbs.addMenuItem(item);
        }
        return crumbs;
    }
    
    public boolean isMallsVisible() {
        return currentView == SalesView.MALLS;
    }
    
    public boolean isStoresVisible() {
        return currentView == SalesView.STORES;
    }
    
    public boolean isSalesVisible() {
        return currentView == SalesView.SALES;
    }
    
    public boolean isSalesDialogVisible() {
        return currentView == SalesView.SALESDIALOG;
    }
    
    public boolean isMallDialogVisible() {
        return currentView == SalesView.MALLDIALOG;
    }
    
    public boolean isStoreDialogVisible() {
        return currentView == SalesView.STOREDIALOG;
    }
    
    public List<MonthlySales> findMonthlySales() {
        return monthlySalesFacade.findByStore(currentStore);
    }
    
    public MonthlySales findMonthlySale(int id) {
        currentMonthlySales = monthlySalesFacade.find(id);
        return currentMonthlySales;
    }
    
    public MonthlySales prepareNewMonthlySale() {
        currentView = SalesView.SALESDIALOG;
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
        monthlySalesFacade.removeSale(monthlySale);
        showMessage("Deleted a daily sales entry", "deleted");
    }
    
    public void cancelCreate() {
        showMessage("No changes made", "Cancelled");
        currentView = SalesView.SALES;
    }
    
    public void createMonthlySales() {
        currentMonthlySales.setId(null);
        try {
            monthlySalesFacade.addSale(currentStore, currentMonthlySales);
        }
        catch (RuntimeException e) {
            showMessage(FacesMessage.SEVERITY_ERROR, "dberror", "something brokee");
            return;
        }
        showMessage(" New Sales for " + currentStore.getName() + " at " + currentMall.getName(), "New...");
        currentView = SalesView.SALES;
    }
    
    public void updateMonthlySales(MonthlySales mall) {
        showMessage(mall.getId().toString(), "MonthlySales Updated");
        monthlySalesFacade.edit(mall);
    }

}
