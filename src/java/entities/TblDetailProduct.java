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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PhuongNT
 */
@Entity
@Table(name = "TblDetailProduct", catalog = "NTPGamingGear", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblDetailProduct.findAll", query = "SELECT t FROM TblDetailProduct t"),
    @NamedQuery(name = "TblDetailProduct.findByProductID", query = "SELECT t FROM TblDetailProduct t WHERE t.productID = :productID"),
    @NamedQuery(name = "TblDetailProduct.findByProductName", query = "SELECT t FROM TblDetailProduct t WHERE t.productName = :productName"),
    @NamedQuery(name = "TblDetailProduct.findByPrice", query = "SELECT t FROM TblDetailProduct t WHERE t.price = :price"),
    @NamedQuery(name = "TblDetailProduct.findByThumbnail", query = "SELECT t FROM TblDetailProduct t WHERE t.thumbnail = :thumbnail"),
    @NamedQuery(name = "TblDetailProduct.findByCategoryID", query = "SELECT t FROM TblDetailProduct t WHERE t.categoryID = :categoryID"),
    @NamedQuery(name = "TblDetailProduct.findByIsActive", query = "SELECT t FROM TblDetailProduct t WHERE t.isActive = :isActive"),
    @NamedQuery(name = "TblDetailProduct.findByManufacturer", query = "SELECT t FROM TblDetailProduct t WHERE t.manufacturer = :manufacturer"),
    @NamedQuery(name = "TblDetailProduct.findByGuarantee", query = "SELECT t FROM TblDetailProduct t WHERE t.guarantee = :guarantee")})
public class TblDetailProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ProductID", nullable = false)
    private Long productID;
    @Column(name = "ProductName", length = 250)
    private String productName;
    @Column(name = "Price")
    private BigInteger price;
    @Column(name = "Thumbnail", length = 250)
    private String thumbnail;
    @Column(name = "CategoryID", length = 250)
    private String categoryID;
    @Column(name = "IsActive")
    private Boolean isActive;
    @Column(name = "Manufacturer", length = 250)
    private String manufacturer;
    @Column(name = "Guarantee", length = 250)
    private String guarantee;

    public TblDetailProduct() {
    }

    public TblDetailProduct(Long productID) {
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
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
        if (!(object instanceof TblDetailProduct)) {
            return false;
        }
        TblDetailProduct other = (TblDetailProduct) object;
        if ((this.productID == null && other.productID != null) || (this.productID != null && !this.productID.equals(other.productID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TblDetailProduct[ productID=" + productID + " ]";
    }
    
}
