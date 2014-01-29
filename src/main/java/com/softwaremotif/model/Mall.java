/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softwaremotif.model;

import java.io.Serializable;
import java.util.Collection;
import javax.faces.model.SelectItem;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Mall.findById", query = "SELECT m FROM Mall m WHERE m.id = :id"),
    @NamedQuery(name = "Mall.findByName", query = "SELECT m FROM Mall m WHERE m.name = :name")})
public class Mall implements Serializable, Selectable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mall")
    private Collection<Store> storeCollection;

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
    public Collection<Store> getStoreCollection() {
        return storeCollection;
    }

    public void setStoreCollection(Collection<Store> storeCollection) {
        this.storeCollection = storeCollection;
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
    @Transient
    public SelectItem getSelectItem() {
        return new SelectItem(this, name);
    }
    
}