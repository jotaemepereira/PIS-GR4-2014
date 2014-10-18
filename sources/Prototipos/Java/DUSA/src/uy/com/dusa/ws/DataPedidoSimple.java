
package uy.com.dusa.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataPedidoSimple complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataPedidoSimple">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="formaDePago" type="{http://ws.dusa.com.uy/}pedidoFormaDePago" minOccurs="0"/>
 *         &lt;element name="lineas" type="{http://ws.dusa.com.uy/}dataLineaPedidoSimple" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataPedidoSimple", propOrder = {
    "formaDePago",
    "lineas"
})
public class DataPedidoSimple {

    protected PedidoFormaDePago formaDePago;
    @XmlElement(nillable = true)
    protected List<DataLineaPedidoSimple> lineas;

    /**
     * Gets the value of the formaDePago property.
     * 
     * @return
     *     possible object is
     *     {@link PedidoFormaDePago }
     *     
     */
    public PedidoFormaDePago getFormaDePago() {
        return formaDePago;
    }

    /**
     * Sets the value of the formaDePago property.
     * 
     * @param value
     *     allowed object is
     *     {@link PedidoFormaDePago }
     *     
     */
    public void setFormaDePago(PedidoFormaDePago value) {
        this.formaDePago = value;
    }

    /**
     * Gets the value of the lineas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataLineaPedidoSimple }
     * 
     * 
     */
    public List<DataLineaPedidoSimple> getLineas() {
        if (lineas == null) {
            lineas = new ArrayList<DataLineaPedidoSimple>();
        }
        return this.lineas;
    }

}
