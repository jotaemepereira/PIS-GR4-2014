
package uy.com.dusa.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for dataOferta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataOferta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroArticulo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cantidadVenta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cantidadBonificado" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="porcentajeBonificado" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="fechaFin" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataOferta", propOrder = {
    "numeroArticulo",
    "cantidadVenta",
    "descripcion",
    "cantidadBonificado",
    "porcentajeBonificado",
    "fechaFin"
})
public class DataOferta {

    protected int numeroArticulo;
    protected int cantidadVenta;
    protected String descripcion;
    protected int cantidadBonificado;
    protected BigDecimal porcentajeBonificado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaFin;

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
     * Gets the value of the cantidadVenta property.
     * 
     */
    public int getCantidadVenta() {
        return cantidadVenta;
    }

    /**
     * Sets the value of the cantidadVenta property.
     * 
     */
    public void setCantidadVenta(int value) {
        this.cantidadVenta = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the cantidadBonificado property.
     * 
     */
    public int getCantidadBonificado() {
        return cantidadBonificado;
    }

    /**
     * Sets the value of the cantidadBonificado property.
     * 
     */
    public void setCantidadBonificado(int value) {
        this.cantidadBonificado = value;
    }

    /**
     * Gets the value of the porcentajeBonificado property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPorcentajeBonificado() {
        return porcentajeBonificado;
    }

    /**
     * Sets the value of the porcentajeBonificado property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPorcentajeBonificado(BigDecimal value) {
        this.porcentajeBonificado = value;
    }

    /**
     * Gets the value of the fechaFin property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaFin() {
        return fechaFin;
    }

    /**
     * Sets the value of the fechaFin property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaFin(XMLGregorianCalendar value) {
        this.fechaFin = value;
    }

}
