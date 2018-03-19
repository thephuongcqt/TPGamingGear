/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PhuongNT
 */
@Entity
@Table(name = "TblDetailOrder", catalog = "NTPGamingGear", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblDetailOrder.findAll", query = "SELECT t FROM TblDetailOrder t"),
    @NamedQuery(name = "TblDetailOrder.findByOrderID", query = "SELECT t FROM TblDetailOrder t WHERE t.tblDetailOrderPK.orderID = :orderID"),
    @NamedQuery(name = "TblDetailOrder.findByProductID", query = "SELECT t FROM TblDetailOrder t WHERE t.tblDetailOrderPK.productID = :productID"),
    @NamedQuery(name = "TblDetailOrder.findByQuantity", query = "SELECT t FROM TblDetailOrder t WHERE t.quantity = :quantity")})
public class TblDetailOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TblDetailOrderPK tblDetailOrderPK;
    @Column(name = "Quantity")
    private Integer quantity;
    @JoinColumn(name = "OrderID", referencedColumnName = "OrderID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TblOrder tblOrder;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TblProduct tblProduct;

    public TblDetailOrder() {
    }

    public TblDetailOrder(TblDetailOrderPK tblDetailOrderPK) {
        this.tblDetailOrderPK = tblDetailOrderPK;
    }

    public TblDetailOrder(long orderID, long productID) {
        this.tblDetailOrderPK = new TblDetailOrderPK(orderID, productID);
    }

    public TblDetailOrderPK getTblDetailOrderPK() {
        return tblDetailOrderPK;
    }

    public void setTblDetailOrderPK(TblDetailOrderPK tblDetailOrderPK) {
        this.tblDetailOrderPK = tblDetailOrderPK;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public TblOrder getTblOrder() {
        return tblOrder;
    }

    public void setTblOrder(TblOrder tblOrder) {
        this.tblOrder = tblOrder;
    }

    public TblProduct getTblProduct() {
        return tblProduct;
    }

    public void setTblProduct(TblProduct tblProduct) {
        this.tblProduct = tblProduct;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tblDetailOrderPK != null ? tblDetailOrderPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDetailOrder)) {
            return false;
        }
        TblDetailOrder other = (TblDetailOrder) object;
        if ((this.tblDetailOrderPK == null && other.tblDetailOrderPK != null) || (this.tblDetailOrderPK != null && !this.tblDetailOrderPK.equals(other.tblDetailOrderPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TblDetailOrder[ tblDetailOrderPK=" + tblDetailOrderPK + " ]";
    }
    
}
