/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softwaremotif.model;

import java.io.Serializable;
import java.util.Collection;
import javax.faces.model.SelectItem;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.BatchFetch;
import org.eclipse.persistence.annotations.BatchFetchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tonybrouwer
 */
@Entity
@Table(name = "STORE")
@XmlRootElement
@Access(AccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Store.findAll",
            query = "SELECT s FROM Store s join fetch s.monthlySales"),
    @NamedQuery(name = "Store.findByMall",
            query = "SELECT s FROM Store s WHERE s.mall.id = :id"),
    @NamedQuery(name = "Store.findById", query = "SELECT s FROM Store s WHERE s.id = :id"),
    @NamedQuery(name = "Store.findByName", query = "SELECT s FROM Store s WHERE s.name = :name")})
public class Store implements Serializable, Selectable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_STORE")
    @SequenceGenerator(name="SEQ_STORE", sequenceName="SEQ_STORE", allocationSize=1)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    private String name;
    
    @BatchFetch(BatchFetchType.JOIN)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store", orphanRemoval = true)
    private Collection<MonthlySales> monthlySales;
    
    @JoinColumn(name = "MALL_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Mall mall;

    private static final Logger LOG = LoggerFactory.getLogger(Store.class);
    
    public Store() {
    }

    public Store(Long id) {
        this.id = id;
    }

    public Store(Long id, String name) {
        this.id = id;
        this.name = name;
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
    public Collection<MonthlySales> getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(Collection<MonthlySales> monthlySales) {
        this.monthlySales = monthlySales;
    }

    public Mall getMall() {
        return mall;
    }

    public void setMall(Mall mall) {
        this.mall = mall;
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
        if (!(object instanceof Store)) {
            return false;
        }
        Store other = (Store) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softwaremotif.model.Store[id=" + id + ", name=" + name + "]";
    }
    
    @Override
    public SelectItem getSelectItem() {
        return new SelectItem(this, name);
    }
    
    public int getSalesCount() {
        return this.monthlySales.size();
    }
    
    public void addMonthlySale(MonthlySales monthlySales) {
        monthlySales.setStore(this);
        this.getMonthlySales().add(monthlySales);
    }
}
