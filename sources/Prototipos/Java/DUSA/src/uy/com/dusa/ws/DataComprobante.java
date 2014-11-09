
package uy.com.dusa.ws;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for dataComprobante complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataComprobante">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoCFE" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="serieCFE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCFE" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fechaComprobante" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaEmision" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="numeroCliente" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="formaDePago" type="{http://ws.dusa.com.uy/}comprobanteFormaDePago" minOccurs="0"/>
 *         &lt;element name="ordenDeCompra" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="montoNoGravado" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="montoNetoGravadoIvaMinimo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="montoNetoGravadoIvaBasico" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="totalIvaMinimo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="totalIvaBasico" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="montoTotal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="cantidadLineas" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="montoRetenidoIVA" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="montoRetenidoIRAE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="montoNoFacturable" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="montoTotalAPagar" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="montoTributoIvaMinimo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="montoTributoIvaBasico" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="detalle" type="{http://ws.dusa.com.uy/}dataLineaComprobante" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="vencimientos" type="{http://ws.dusa.com.uy/}dataVencimiento" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataComprobante", propOrder = {
    "tipoCFE",
    "serieCFE",
    "numeroCFE",
    "fechaComprobante",
    "fechaEmision",
    "numeroCliente",
    "formaDePago",
    "ordenDeCompra",
    "montoNoGravado",
    "montoNetoGravadoIvaMinimo",
    "montoNetoGravadoIvaBasico",
    "totalIvaMinimo",
    "totalIvaBasico",
    "montoTotal",
    "cantidadLineas",
    "montoRetenidoIVA",
    "montoRetenidoIRAE",
    "montoNoFacturable",
    "montoTotalAPagar",
    "montoTributoIvaMinimo",
    "montoTributoIvaBasico",
    "detalle",
    "vencimientos"
})
public class DataComprobante {

    protected int tipoCFE;
    protected String serieCFE;
    protected int numeroCFE;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaComprobante;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaEmision;
    protected int numeroCliente;
    protected ComprobanteFormaDePago formaDePago;
    protected long ordenDeCompra;
    protected BigDecimal montoNoGravado;
    protected BigDecimal montoNetoGravadoIvaMinimo;
    protected BigDecimal montoNetoGravadoIvaBasico;
    protected BigDecimal totalIvaMinimo;
    protected BigDecimal totalIvaBasico;
    protected BigDecimal montoTotal;
    protected Integer cantidadLineas;
    protected BigDecimal montoRetenidoIVA;
    protected BigDecimal montoRetenidoIRAE;
    protected BigDecimal montoNoFacturable;
    protected BigDecimal montoTotalAPagar;
    protected BigDecimal montoTributoIvaMinimo;
    protected BigDecimal montoTributoIvaBasico;
    @XmlElement(nillable = true)
    protected List<DataLineaComprobante> detalle;
    @XmlElement(nillable = true)
    protected List<DataVencimiento> vencimientos;

    /**
     * Gets the value of the tipoCFE property.
     * 
     */
    public int getTipoCFE() {
        return tipoCFE;
    }

    /**
     * Sets the value of the tipoCFE property.
     * 
     */
    public void setTipoCFE(int value) {
        this.tipoCFE = value;
    }

    /**
     * Gets the value of the serieCFE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerieCFE() {
        return serieCFE;
    }

    /**
     * Sets the value of the serieCFE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerieCFE(String value) {
        this.serieCFE = value;
    }

    /**
     * Gets the value of the numeroCFE property.
     * 
     */
    public int getNumeroCFE() {
        return numeroCFE;
    }

    /**
     * Sets the value of the numeroCFE property.
     * 
     */
    public void setNumeroCFE(int value) {
        this.numeroCFE = value;
    }

    /**
     * Gets the value of the fechaComprobante property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaComprobante() {
        return fechaComprobante;
    }

    /**
     * Sets the value of the fechaComprobante property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaComprobante(XMLGregorianCalendar value) {
        this.fechaComprobante = value;
    }

    /**
     * Gets the value of the fechaEmision property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Sets the value of the fechaEmision property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaEmision(XMLGregorianCalendar value) {
        this.fechaEmision = value;
    }

    /**
     * Gets the value of the numeroCliente property.
     * 
     */
    public int getNumeroCliente() {
        return numeroCliente;
    }

    /**
     * Sets the value of the numeroCliente property.
     * 
     */
    public void setNumeroCliente(int value) {
        this.numeroCliente = value;
    }

    /**
     * Gets the value of the formaDePago property.
     * 
     * @return
     *     possible object is
     *     {@link ComprobanteFormaDePago }
     *     
     */
    public ComprobanteFormaDePago getFormaDePago() {
        return formaDePago;
    }

    /**
     * Sets the value of the formaDePago property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComprobanteFormaDePago }
     *     
     */
    public void setFormaDePago(ComprobanteFormaDePago value) {
        this.formaDePago = value;
    }

    /**
     * Gets the value of the ordenDeCompra property.
     * 
     */
    public long getOrdenDeCompra() {
        return ordenDeCompra;
    }

    /**
     * Sets the value of the ordenDeCompra property.
     * 
     */
    public void setOrdenDeCompra(long value) {
        this.ordenDeCompra = value;
    }

    /**
     * Gets the value of the montoNoGravado property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoNoGravado() {
        return montoNoGravado;
    }

    /**
     * Sets the value of the montoNoGravado property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoNoGravado(BigDecimal value) {
        this.montoNoGravado = value;
    }

    /**
     * Gets the value of the montoNetoGravadoIvaMinimo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoNetoGravadoIvaMinimo() {
        return montoNetoGravadoIvaMinimo;
    }

    /**
     * Sets the value of the montoNetoGravadoIvaMinimo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoNetoGravadoIvaMinimo(BigDecimal value) {
        this.montoNetoGravadoIvaMinimo = value;
    }

    /**
     * Gets the value of the montoNetoGravadoIvaBasico property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoNetoGravadoIvaBasico() {
        return montoNetoGravadoIvaBasico;
    }

    /**
     * Sets the value of the montoNetoGravadoIvaBasico property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoNetoGravadoIvaBasico(BigDecimal value) {
        this.montoNetoGravadoIvaBasico = value;
    }

    /**
     * Gets the value of the totalIvaMinimo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalIvaMinimo() {
        return totalIvaMinimo;
    }

    /**
     * Sets the value of the totalIvaMinimo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalIvaMinimo(BigDecimal value) {
        this.totalIvaMinimo = value;
    }

    /**
     * Gets the value of the totalIvaBasico property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalIvaBasico() {
        return totalIvaBasico;
    }

    /**
     * Sets the value of the totalIvaBasico property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalIvaBasico(BigDecimal value) {
        this.totalIvaBasico = value;
    }

    /**
     * Gets the value of the montoTotal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    /**
     * Sets the value of the montoTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoTotal(BigDecimal value) {
        this.montoTotal = value;
    }

    /**
     * Gets the value of the cantidadLineas property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCantidadLineas() {
        return cantidadLineas;
    }

    /**
     * Sets the value of the cantidadLineas property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantidadLineas(Integer value) {
        this.cantidadLineas = value;
    }

    /**
     * Gets the value of the montoRetenidoIVA property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoRetenidoIVA() {
        return montoRetenidoIVA;
    }

    /**
     * Sets the value of the montoRetenidoIVA property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoRetenidoIVA(BigDecimal value) {
        this.montoRetenidoIVA = value;
    }

    /**
     * Gets the value of the montoRetenidoIRAE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoRetenidoIRAE() {
        return montoRetenidoIRAE;
    }

    /**
     * Sets the value of the montoRetenidoIRAE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoRetenidoIRAE(BigDecimal value) {
        this.montoRetenidoIRAE = value;
    }

    /**
     * Gets the value of the montoNoFacturable property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoNoFacturable() {
        return montoNoFacturable;
    }

    /**
     * Sets the value of the montoNoFacturable property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoNoFacturable(BigDecimal value) {
        this.montoNoFacturable = value;
    }

    /**
     * Gets the value of the montoTotalAPagar property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoTotalAPagar() {
        return montoTotalAPagar;
    }

    /**
     * Sets the value of the montoTotalAPagar property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoTotalAPagar(BigDecimal value) {
        this.montoTotalAPagar = value;
    }

    /**
     * Gets the value of the montoTributoIvaMinimo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoTributoIvaMinimo() {
        return montoTributoIvaMinimo;
    }

    /**
     * Sets the value of the montoTributoIvaMinimo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoTributoIvaMinimo(BigDecimal value) {
        this.montoTributoIvaMinimo = value;
    }

    /**
     * Gets the value of the montoTributoIvaBasico property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoTributoIvaBasico() {
        return montoTributoIvaBasico;
    }

    /**
     * Sets the value of the montoTributoIvaBasico property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoTributoIvaBasico(BigDecimal value) {
        this.montoTributoIvaBasico = value;
    }

    /**
     * Gets the value of the detalle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detalle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetalle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataLineaComprobante }
     * 
     * 
     */
    public List<DataLineaComprobante> getDetalle() {
        if (detalle == null) {
            detalle = new ArrayList<DataLineaComprobante>();
        }
        return this.detalle;
    }

    /**
     * Gets the value of the vencimientos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vencimientos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVencimientos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataVencimiento }
     * 
     * 
     */
    public List<DataVencimiento> getVencimientos() {
        if (vencimientos == null) {
            vencimientos = new ArrayList<DataVencimiento>();
        }
        return this.vencimientos;
    }

}
