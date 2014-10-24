
package uy.com.dusa.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataIVA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataIVA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoIVA" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoTasa" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="indicadorFacturacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="valorIVA" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="valorTributo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="resguardoIVA" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="resguardoIRAE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataIVA", propOrder = {
    "tipoIVA",
    "descripcion",
    "tipoTasa",
    "indicadorFacturacion",
    "valorIVA",
    "valorTributo",
    "resguardoIVA",
    "resguardoIRAE"
})
public class DataIVA {

    @XmlSchemaType(name = "unsignedShort")
    protected int tipoIVA;
    protected String descripcion;
    @XmlSchemaType(name = "unsignedShort")
    protected int tipoTasa;
    protected int indicadorFacturacion;
    protected BigDecimal valorIVA;
    protected BigDecimal valorTributo;
    protected BigDecimal resguardoIVA;
    protected BigDecimal resguardoIRAE;

    /**
     * Gets the value of the tipoIVA property.
     * 
     */
    public int getTipoIVA() {
        return tipoIVA;
    }

    /**
     * Sets the value of the tipoIVA property.
     * 
     */
    public void setTipoIVA(int value) {
        this.tipoIVA = value;
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
     * Gets the value of the tipoTasa property.
     * 
     */
    public int getTipoTasa() {
        return tipoTasa;
    }

    /**
     * Sets the value of the tipoTasa property.
     * 
     */
    public void setTipoTasa(int value) {
        this.tipoTasa = value;
    }

    /**
     * Gets the value of the indicadorFacturacion property.
     * 
     */
    public int getIndicadorFacturacion() {
        return indicadorFacturacion;
    }

    /**
     * Sets the value of the indicadorFacturacion property.
     * 
     */
    public void setIndicadorFacturacion(int value) {
        this.indicadorFacturacion = value;
    }

    /**
     * Gets the value of the valorIVA property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorIVA() {
        return valorIVA;
    }

    /**
     * Sets the value of the valorIVA property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorIVA(BigDecimal value) {
        this.valorIVA = value;
    }

    /**
     * Gets the value of the valorTributo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorTributo() {
        return valorTributo;
    }

    /**
     * Sets the value of the valorTributo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorTributo(BigDecimal value) {
        this.valorTributo = value;
    }

    /**
     * Gets the value of the resguardoIVA property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getResguardoIVA() {
        return resguardoIVA;
    }

    /**
     * Sets the value of the resguardoIVA property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setResguardoIVA(BigDecimal value) {
        this.resguardoIVA = value;
    }

    /**
     * Gets the value of the resguardoIRAE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getResguardoIRAE() {
        return resguardoIRAE;
    }

    /**
     * Sets the value of the resguardoIRAE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setResguardoIRAE(BigDecimal value) {
        this.resguardoIRAE = value;
    }

}
