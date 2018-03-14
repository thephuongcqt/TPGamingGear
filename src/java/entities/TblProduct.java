/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author PhuongNT
 */
@Entity
@Table(name = "TblProduct", catalog = "NTPGamingGear", schema = "dbo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Product", propOrder = {
//    "productName",
//    "price",
//    "thumbnail"
})
@XmlSeeAlso({
    TblProduct.class
})
@XmlRootElement(name = "ProductType", namespace = "www.product.vn")
@NamedQueries({
    @NamedQuery(name = "TblProduct.findAll", query = "SELECT t FROM TblProduct t"),
    @NamedQuery(name = "TblProduct.findByProductID", query = "SELECT t FROM TblProduct t WHERE t.productID = :productID"),
    @NamedQuery(name = "TblProduct.findByProductName", query = "SELECT t FROM TblProduct t WHERE t.productName = :productName"),
    @NamedQuery(name = "TblProduct.findByPrice", query = "SELECT t FROM TblProduct t WHERE t.price = :price"),
    @NamedQuery(name = "TblProduct.findByThumbnail", query = "SELECT t FROM TblProduct t WHERE t.thumbnail = :thumbnail"),
    @NamedQuery(name = "TblProduct.findByCategoryID", query = "SELECT t FROM TblProduct t WHERE t.categoryID = :categoryID"),
    @NamedQuery(name = "TblProduct.findByIsActive", query = "SELECT t FROM TblProduct t WHERE t.isActive = :isActive"),
    @NamedQuery(name = "TblProduct.findByNameAndCategoryId", query = "SELECT t FROM TblProduct t WHERE t.productName = :productName AND t.categoryID = :categoryID"),
    @NamedQuery(name = "TblProduct.findTrendingProducts", query = "SELECT t FROM TblProduct t"),
    @NamedQuery(name = "TblProduct.countAllRecordsInCategory", query = "SELECT count(t.productID) FROM TblProduct t WHERE t.categoryID = :categoryID"), 
    @NamedQuery(name = "TblProduct.searchLikeProductName", query = "SELECT t FROM TblProduct t WHERE t.productName LIKE :productName")})
public class TblProduct implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ProductID", nullable = false)
    @XmlAttribute(name = "ProductID", required = true)
    private Long productID;
    
    @XmlElement(name = "ProductName", namespace = "www.product.vn", required = true)
    @Column(name = "ProductName", length = 250)
    private String productName;
    
    @XmlElement(name = "Price", namespace = "www.product.vn", required = true)
    @Column(name = "Price")
    private BigInteger price;
    
    @XmlElement(name = "Thumbnail", namespace = "www.product.vn", required = true)
    @Column(name = "Thumbnail", length = 250)
    private String thumbnail;
    
    @Column(name = "CategoryID", length = 250)
    @XmlAttribute(name = "CategoryID", required = true)
    private String categoryID;
    
    @Column(name = "IsActive")
    @XmlAttribute(name = "IsActive")
    private Boolean isActive;

    public TblProduct(Long productID, String productName, BigInteger price, String thumbnail, String categoryID, Boolean isActive) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.thumbnail = thumbnail;
        this.categoryID = categoryID;
        this.isActive = isActive;
    }   
    
    public TblProduct() {
    }

    public TblProduct(Long productID) {
        this.productID = productID;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productID != null ? productID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblProduct)) {
            return false;
        }
        TblProduct other = (TblProduct) object;
        if ((this.productID == null && other.productID != null) || (this.productID != null && !this.productID.equals(other.productID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TblProduct[ productID=" + productID + " ]";
    }
    
}
