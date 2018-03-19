/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author PhuongNT
 */
@Embeddable
public class TblDetailOrderPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "OrderID", nullable = false)
    private long orderID;
    @Basic(optional = false)
    @Column(name = "ProductID", nullable = false)
    private long productID;

    public TblDetailOrderPK() {
    }

    public TblDetailOrderPK(long orderID, long productID) {
        this.orderID = orderID;
        this.productID = productID;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderID;
        hash += (int) productID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDetailOrderPK)) {
            return false;
        }
        TblDetailOrderPK other = (TblDetailOrderPK) object;
        if (this.orderID != other.orderID) {
            return false;
        }
        if (this.productID != other.productID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TblDetailOrderPK[ orderID=" + orderID + ", productID=" + productID + " ]";
    }
    
}
