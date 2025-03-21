package com.onndoo.booker.entities;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderDetailPK implements Serializable {
    
    private static final long serialVersionUID = -1381453765352891148L;
    
    @Basic(optional = false)
    @Column(name = "ORDER_ID")
    private int orderId;
    @Basic(optional = false)
    @Column(name = "PRODUCT_ID")
    private int productId;

    public OrderDetailPK() {
    }

    public OrderDetailPK(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderId;
        hash += (int) productId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDetailPK)) {
            return false;
        }
        OrderDetailPK other = (OrderDetailPK) object;
        if (this.orderId != other.orderId) {
            return false;
        }
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.forest.entity.OrderDetailPK[orderId=" + orderId + ", productId=" + productId + "]";
    }

}