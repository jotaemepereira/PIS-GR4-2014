
package uy.com.dusa.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resultGetLaboratorio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resultGetLaboratorio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="laboratorio" type="{http://ws.dusa.com.uy/}dataLaboratorio" minOccurs="0"/>
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
@XmlType(name = "resultGetLaboratorio", propOrder = {
    "laboratorio",
    "mensaje"
})
public class ResultGetLaboratorio {

    protected DataLaboratorio laboratorio;
    protected MensajeError mensaje;

    /**
     * Gets the value of the laboratorio property.
     * 
     * @return
     *     possible object is
     *     {@link DataLaboratorio }
     *     
     */
    public DataLaboratorio getLaboratorio() {
        return laboratorio;
    }

    /**
     * Sets the value of the laboratorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataLaboratorio }
     *     
     */
    public void setLaboratorio(DataLaboratorio value) {
        this.laboratorio = value;
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
