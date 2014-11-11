
package uy.com.dusa.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataLineaLaboratorio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataLineaLaboratorio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idLaboratorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idLinea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataLineaLaboratorio", propOrder = {
    "idLaboratorio",
    "idLinea",
    "nombre"
})
public class DataLineaLaboratorio {

    protected String idLaboratorio;
    protected String idLinea;
    protected String nombre;

    /**
     * Gets the value of the idLaboratorio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdLaboratorio() {
        return idLaboratorio;
    }

    /**
     * Sets the value of the idLaboratorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdLaboratorio(String value) {
        this.idLaboratorio = value;
    }

    /**
     * Gets the value of the idLinea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdLinea() {
        return idLinea;
    }

    /**
     * Sets the value of the idLinea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdLinea(String value) {
        this.idLinea = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

}
