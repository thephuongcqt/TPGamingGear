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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author PhuongNT
 */
@Entity
@Table(name = "Tbl_Product", catalog = "TPGamingGear", schema = "dbo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
//    "productName",
//    "price",
//    "Thumbnail",
//    "manufacturer",
//    "guarantee",
//    "categoryName"
})
@XmlRootElement(name = "Product", namespace = "www.product.vn")

@NamedQueries({
    @NamedQuery(name = "TblProduct.findAll", query = "SELECT t FROM TblProduct t"),
    @NamedQuery(name = "TblProduct.findById", query = "SELECT t FROM TblProduct t WHERE t.id = :id"),
    @NamedQuery(name = "TblProduct.findByProductName", query = "SELECT t FROM TblProduct t WHERE t.productName = :productName"),
    @NamedQuery(name = "TblProduct.findByPrice", query = "SELECT t FROM TblProduct t WHERE t.price = :price"),
    @NamedQuery(name = "TblProduct.findByThumbnail", query = "SELECT t FROM TblProduct t WHERE t.thumbnail = :thumbnail"),
    @NamedQuery(name = "TblProduct.findByManufacturer", query = "SELECT t FROM TblProduct t WHERE t.manufacturer = :manufacturer"),
    @NamedQuery(name = "TblProduct.findByGuarantee", query = "SELECT t FROM TblProduct t WHERE t.guarantee = :guarantee"),
    @NamedQuery(name = "TblProduct.findByCategoryName", query = "SELECT t FROM TblProduct t WHERE t.categoryName = :categoryName"),
    @NamedQuery(name = "TblProduct.findByIsActive", query = "SELECT t FROM TblProduct t WHERE t.isActive = :isActive")})
public class TblProduct implements Serializable {

    public TblProduct(String categoryID, String categoryName, BigInteger price, Integer id, String productName, String thumbnail, String manufacturer, String guarantee, Boolean isActive) {
        this.productName = productName;
        this.price = price;
        this.thumbnail = thumbnail;
        this.guarantee = guarantee;
        this.manufacturer = manufacturer;
        this.categoryName = categoryName;
        this.id = id;
        this.categoryID = categoryID;
        this.isActive = isActive;
    }

    @Column(name = "ProductName", length = 250)
    @XmlElement(name = "ProductName", namespace = "www.product.vn", required = true)
    private String productName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    @Column(name = "Price")
    @XmlElement(name = "Price", namespace = "www.product.vn")
    private BigInteger price;
    
    @Column(name = "Thumbnail", length = 50)
    @XmlElement(name = "Thumbnail", namespace = "www.product.vn", required = true)
    private String thumbnail;
    
    @Column(name = "Manufacturer", length = 50)
    @XmlElement(name = "Manufacturer", namespace = "www.product.vn", required = true)
    private String manufacturer;
    
    @Column(name = "Guarantee", length = 50)
    @XmlElement(name = "Guarantee", namespace = "www.product.vn", required = true)
    private String guarantee;
    
    @Column(name = "CategoryName", length = 250)
    @XmlElement(name = "CategoryName", namespace = "www.product.vn", required = true)
    private String categoryName;
    
    @Column(name = "CategoryID", length = 50)
    @XmlAttribute(name = "CategoryID", required = true)
    private String categoryID;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    @XmlAttribute(name = "ProductID", required = true)
    @XmlSchemaType(name = "positiveInteger")
    private Integer id;

    @Column(name = "IsActive")
    @XmlAttribute(name = "IsActive")
    private Boolean isActive;


    public TblProduct() {
    }

    public TblProduct(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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


    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
        if (!(object instanceof TblProduct)) {
            return false;
        }
        TblProduct other = (TblProduct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TblProduct[ id=" + id + " ]";
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
    
}
