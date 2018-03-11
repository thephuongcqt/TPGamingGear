//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.11 at 01:57:11 PM ICT 
//


package entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetailProduct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetailProduct">
 *   &lt;complexContent>
 *     &lt;extension base="{www.product.vn}Product">
 *       &lt;sequence>
 *         &lt;element name="Manufacturer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Guarantee" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ProdID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetailProduct", namespace = "www.detailproduct.vn", propOrder = {
    "manufacturer",
    "guarantee"
})
@XmlRootElement(name = "DetailProduct")
public class DetailProduct
    extends Product
{

    public DetailProduct() {
    }
    
    public DetailProduct(String manufacturer, String guarantee, String prodID) {
        this.manufacturer = manufacturer;
        this.guarantee = guarantee;
        this.prodID = prodID;
    }
    
    @XmlElement(name = "Manufacturer", required = true)
    protected String manufacturer;
    @XmlElement(name = "Guarantee", required = true)
    protected String guarantee;
    @XmlAttribute(name = "ProdID")
    protected String prodID;

    /**
     * Gets the value of the manufacturer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the value of the manufacturer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturer(String value) {
        this.manufacturer = value;
    }

    /**
     * Gets the value of the guarantee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuarantee() {
        return guarantee;
    }

    /**
     * Sets the value of the guarantee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuarantee(String value) {
        this.guarantee = value;
    }

    /**
     * Gets the value of the prodID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdID() {
        return prodID;
    }

    /**
     * Sets the value of the prodID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdID(String value) {
        this.prodID = value;
    }

}
