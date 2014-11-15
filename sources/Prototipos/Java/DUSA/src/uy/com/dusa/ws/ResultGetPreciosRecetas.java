
package uy.com.dusa.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resultGetPreciosRecetas complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resultGetPreciosRecetas">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="preciosRecetas" type="{http://ws.dusa.com.uy/}dataPrecioReceta" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mensaje" type="{http://ws.dusa.com.uy/}mensajeError" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultGetPreciosRecetas", propOrder = {
    "preciosRecetas",
    "mensaje"
})
public class ResultGetPreciosRecetas {

    @XmlElement(nillable = true)
    protected List<DataPrecioReceta> preciosRecetas;
    protected MensajeError mensaje;

    /**
     * Gets the value of the preciosRecetas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the preciosRecetas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPreciosRecetas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataPrecioReceta }
     * 
     * 
     */
    public List<DataPrecioReceta> getPreciosRecetas() {
        if (preciosRecetas == null) {
            preciosRecetas = new ArrayList<DataPrecioReceta>();
        }
        return this.preciosRecetas;
    }

    /**
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link MensajeError }
     *     
     */
    public MensajeError getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link MensajeError }
     *     
     */
    public void setMensaje(MensajeError value) {
        this.mensaje = value;
    }

}
