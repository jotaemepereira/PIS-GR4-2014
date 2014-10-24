
package uy.com.dusa.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataLineaComprobante complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataLineaComprobante">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoCFE" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="serieCFE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCFE" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numeroLinea" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numeroArticulo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="precioUnitario" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="descuento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="descripcionOferta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indicadorDeFacturacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataLineaComprobante", propOrder = {
    "tipoCFE",
    "serieCFE",
    "numeroCFE",
    "numeroLinea",
    "numeroArticulo",
    "cantidad",
    "precioUnitario",
    "descuento",
    "descripcionOferta",
    "indicadorDeFacturacion"
})
public class DataLineaComprobante {

    protected int tipoCFE;
    protected String serieCFE;
    protected int numeroCFE;
    protected int numeroLinea;
    protected int numeroArticulo;
    protected int cantidad;
    protected BigDecimal precioUnitario;
    protected BigDecimal descuento;
    protected String descripcionOferta;
    protected int indicadorDeFacturacion;

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
     * Gets the value of the numeroLinea property.
     * 
     */
    public int getNumeroLinea() {
        return numeroLinea;
    }

    /**
     * Sets the value of the numeroLinea property.
     * 
     */
    public void setNumeroLinea(int value) {
        this.numeroLinea = value;
    }

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
     * Gets the value of the cantidad property.
     * 
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Sets the value of the cantidad property.
     * 
     */
    public void setCantidad(int value) {
        this.cantidad = value;
    }

    /**
     * Gets the value of the precioUnitario property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Sets the value of the precioUnitario property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrecioUnitario(BigDecimal value) {
        this.precioUnitario = value;
    }

    /**
     * Gets the value of the descuento property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * Sets the value of the descuento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDescuento(BigDecimal value) {
        this.descuento = value;
    }

    /**
     * Gets the value of the descripcionOferta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionOferta() {
        return descripcionOferta;
    }

    /**
     * Sets the value of the descripcionOferta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionOferta(String value) {
        this.descripcionOferta = value;
    }

    /**
     * Gets the value of the indicadorDeFacturacion property.
     * 
     */
    public int getIndicadorDeFacturacion() {
        return indicadorDeFacturacion;
    }

    /**
     * Sets the value of the indicadorDeFacturacion property.
     * 
     */
    public void setIndicadorDeFacturacion(int value) {
        this.indicadorDeFacturacion = value;
    }

}
