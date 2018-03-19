/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PhuongNT
 */
@Entity
@Table(name = "TblOrder", catalog = "NTPGamingGear", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblOrder.findAll", query = "SELECT t FROM TblOrder t"),
    @NamedQuery(name = "TblOrder.findByOrderID", query = "SELECT t FROM TblOrder t WHERE t.orderID = :orderID"),
    @NamedQuery(name = "TblOrder.findByDate", query = "SELECT t FROM TblOrder t WHERE t.date = :date")})
public class TblOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OrderID", nullable = false)
    private Long orderID;
    
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblOrder")
    private List<TblDetailOrder> tblDetailOrderCollection;
    
    @JoinColumn(name = "UserID", referencedColumnName = "Email")
    @ManyToOne
    @XmlTransient
    private TblUser userID;

    public TblOrder(Date date, List<TblDetailOrder> tblDetailOrderCollection, TblUser userID) {        
        this.date = date;
        this.tblDetailOrderCollection = tblDetailOrderCollection;
        this.userID = userID;
    }
    
//    public void addDetailOrder(TblDetailOrder detailOrder){
//        if(detailOrder == null){
//            return;
//        } 
//        if(tblDetailOrderCollection == null){
//            tblDetailOrderCollection = new ArrayList<TblDetailOrder>();
//        }
//        tblDetailOrderCollection.add(detailOrder);
//        detailOrder.setTblOrder(this);
//        detailOrder.tblDetailOrderPK.setOrderID(this.orderID);
//    }
        
    public TblOrder() {
    }

    public TblOrder(Long orderID) {
        this.orderID = orderID;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @XmlTransient
    public Collection<TblDetailOrder> getTblDetailOrderCollection() {
        return tblDetailOrderCollection;
    }

    public void setTblDetailOrderCollection(List<TblDetailOrder> tblDetailOrderCollection) {
        this.tblDetailOrderCollection = tblDetailOrderCollection;
    }

    public TblUser getUserID() {
        return userID;
    }

    public void setUserID(TblUser userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderID != null ? orderID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblOrder)) {
            return false;
        }
        TblOrder other = (TblOrder) object;
        if ((this.orderID == null && other.orderID != null) || (this.orderID != null && !this.orderID.equals(other.orderID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TblOrder[ orderID=" + orderID + " ]";
    }
    
}
