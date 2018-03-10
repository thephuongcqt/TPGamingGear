/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
@XmlType(name = "ListCategories", propOrder = {
    "Category"
})
@XmlRootElement(name = "Categories")
public class jaxb_Categories implements Serializable{
    @XmlElement(name = "Category")
    private List<TblCategory> listCategories;

    /**
     * @return the listCategories
     */
    public List<TblCategory> getListCategories() {
        return listCategories;
    }

    /**
     * @param listCategories the listCategories to set
     */
    public void setListCategories(List<TblCategory> listCategories) {
        this.listCategories = listCategories;
    }
}
