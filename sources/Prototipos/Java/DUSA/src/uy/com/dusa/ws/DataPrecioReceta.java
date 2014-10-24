
package uy.com.dusa.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataPrecioReceta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataPrecioReceta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroArticulo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="precioReceta" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="descuentoReceta" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataPrecioReceta", propOrder = {
    "numeroArticulo",
    "precioReceta",
    "descuentoReceta"
})
public class DataPrecioReceta {

    protected int numeroArticulo;
    protected BigDecimal precioReceta;
    protected BigDecimal descuentoReceta;

    /**
     * Gets the value of the numeroArticulo property.
     * 
     */
    public int getNumeroArticulo() {
        return numeroArticulo;
    }

    /**
     * Sets the value of the numeroArticulo property.
     * 
     */
    public void setNumeroArticulo(int value) {
        this.numeroArticulo = value;
    }

    /**
     * Gets the value of the precioReceta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrecioReceta() {
        return precioReceta;
    }

    /**
     * Sets the value of the precioReceta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrecioReceta(BigDecimal value) {
        this.precioReceta = value;
    }

    /**
     * Gets the value of the descuentoReceta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDescuentoReceta() {
        return descuentoReceta;
    }

    /**
     * Sets the value of the descuentoReceta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDescuentoReceta(BigDecimal value) {
        this.descuentoReceta = value;
    }

}
