/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softwaremotif.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tonybrouwer
 */
@Entity
@Table(name = "MONTHLY_SALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MonthlySales.findAll", query = "SELECT m FROM MonthlySales m"),
    @NamedQuery(name = "MonthlySales.findById", query = "SELECT m FROM MonthlySales m WHERE m.id = :id"),
    @NamedQuery(name = "MonthlySales.findByStore", query = "SELECT m FROM MonthlySales m WHERE m.store.id = :id"),
    @NamedQuery(name = "MonthlySales.findBySalesAmt", query = "SELECT m FROM MonthlySales m WHERE m.salesAmt = :salesAmt"),
    @NamedQuery(name = "MonthlySales.findByCostAmt", query = "SELECT m FROM MonthlySales m WHERE m.costAmt = :costAmt")})
public class MonthlySales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MONTHLY_SALES")
    @SequenceGenerator(name="SEQ_MONTHLY_SALES", sequenceName="SEQ_MONTHLY_SALES", allocationSize=1)
    private Long id;
    @Max(value=1000000) @Min(value=0) 
    @Basic(optional = false)
    @NotNull
    @Column(name = "SALES_AMT")
    private BigDecimal salesAmt;
    @Max(value=1000000) @Min(value=0) 
    @Basic(optional = false)
    @NotNull
    @Column(name = "COST_AMT")
    private BigDecimal costAmt;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Store store;

    public MonthlySales() {
    }

    public MonthlySales(Long id) {
        this.id = id;
    }

    public MonthlySales(Long id, BigDecimal salesAmt, BigDecimal costAmt) {
        this.id = id;
        this.salesAmt = salesAmt;
        this.costAmt = costAmt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSalesAmt() {
        return salesAmt;
    }

    public void setSalesAmt(BigDecimal salesAmt) {
        this.salesAmt = salesAmt;
    }

    public BigDecimal getCostAmt() {
        return costAmt;
    }

    public void setCostAmt(BigDecimal costAmt) {
        this.costAmt = costAmt;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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
        if (!(object instanceof MonthlySales)) {
            return false;
        }
        MonthlySales other = (MonthlySales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.datascape.model.MonthlySales[ id=" + id + " ]";
    }
    
}
