/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brouwersystems.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.faces.model.SelectItem;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tonybrouwer
 */
@Entity
@Table(name = "MALL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mall.findAll", query = "SELECT m FROM Mall m"),
    @NamedQuery(name = "Mall.findById",
            query = "SELECT m FROM Mall m join fetch m.stores WHERE m.id = :id"),
    @NamedQuery(name = "Mall.findByName", query = "SELECT m FROM Mall m WHERE m.name = :name")})
public class Mall implements Serializable, Selectable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MALL")
    @SequenceGenerator(name="SEQ_MALL", sequenceName="SEQ_MALL", allocationSize=1)
    private Long id;
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mall", orphanRemoval = true)
    private Collection<Store> stores = new ArrayList<>();

    public Mall() {
    }

    public Mall(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Store> getStores() {
        return stores;
    }

    public void setStores(Collection<Store> stores) {
        this.stores = stores;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mall)) {
            return false;
        }
        Mall other = (Mall) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softwaremotif.model.Mall[id=" + id + ", name=" + name + "]";
    }

    @Override
    public SelectItem getSelectItem() {
        return new SelectItem(this, name);
    }
    
    public void addStore(Store store) {
        getStores().add(store);
        store.setMall(this);
    }
    
    public void removeStore(Store store) {
        getStores().remove(store);
    }
    
}
