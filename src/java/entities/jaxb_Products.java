/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author PhuongNT
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListProducts", propOrder = {
    "Product"
})
@XmlRootElement(name = "Products")
public class jaxb_Products {
    @XmlElement(name = "Product")
    private List<TblProduct> listProducts;

    /**
     * @return the listProducts
     */
    public List<TblProduct> getListProducts() {
        return listProducts;
    }

    /**
     * @param listProducts the listProducts to set
     */
    public void setListProducts(List<TblProduct> listProducts) {
        this.listProducts = listProducts;
    }
}
