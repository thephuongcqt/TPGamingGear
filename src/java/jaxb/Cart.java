//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.19 at 02:17:27 PM ICT 
//


package jaxb;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{www.cartitem.vn}CartItem"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
//    "cartItem"
})
@XmlRootElement(name = "Cart", namespace = "www.cart.vn")
public class Cart {

    @XmlElement(name = "CartItem", namespace = "www.cartitem.vn", required = true)
    protected List<CartItem> cartItem;

    /**
     * Gets the value of the cartItem property.
     * 
     * @return
     *     possible object is
     *     {@link CartItem }
     *     
     */
    public List<CartItem> getCartItem() {
        return cartItem;
    }

    /**
     * Sets the value of the cartItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link CartItem }
     *     
     */
    public void setCartItem(List<CartItem> value) {
        this.cartItem = value;
    }

}
