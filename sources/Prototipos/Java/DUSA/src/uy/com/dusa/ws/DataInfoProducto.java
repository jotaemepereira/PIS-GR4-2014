
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
 * <p>Java class for dataInfoProducto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataInfoProducto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroArticulo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idLaboratorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idLineaLaboratorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clave1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clave2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clave3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoIVA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="precioVenta" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="precioOferta" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="precioPublico" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="habilitado" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="porcentajeDescuentoOferta" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="hasCodigoBarra" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="codigoBarra" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoLaboratorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaUlitmoPrecio" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaUltimaActualizacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ofertas" type="{http://ws.dusa.com.uy/}dataOferta" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="precioReceta" type="{http://ws.dusa.com.uy/}dataPrecioReceta" minOccurs="0"/>
 *         &lt;element name="idProductoNoritel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idPresentacionNoritel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataInfoProducto", propOrder = {
    "numeroArticulo",
    "idLaboratorio",
    "idLineaLaboratorio",
    "clave1",
    "clave2",
    "clave3",
    "descripcion",
    "tipoIVA",
    "precioVenta",
    "precioOferta",
    "precioPublico",
    "habilitado",
    "porcentajeDescuentoOferta",
    "hasCodigoBarra",
    "codigoBarra",
    "codigoLaboratorio",
    "fechaUlitmoPrecio",
    "fechaUltimaActualizacion",
    "ofertas",
    "precioReceta",
    "idProductoNoritel",
    "idPresentacionNoritel"
})
public class DataInfoProducto {

    protected int numeroArticulo;
    protected String idLaboratorio;
    protected String idLineaLaboratorio;
    protected String clave1;
    protected String clave2;
    protected String clave3;
    protected String descripcion;
    protected String tipoIVA;
    protected BigDecimal precioVenta;
    protected BigDecimal precioOferta;
    protected BigDecimal precioPublico;
    @XmlSchemaType(name = "unsignedShort")
    protected int habilitado;
    protected BigDecimal porcentajeDescuentoOferta;
    protected boolean hasCodigoBarra;
    protected String codigoBarra;
    protected String codigoLaboratorio;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaUlitmoPrecio;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaUltimaActualizacion;
    @XmlElement(nillable = true)
    protected List<DataOferta> ofertas;
    protected DataPrecioReceta precioReceta;
    protected int idProductoNoritel;
    protected int idPresentacionNoritel;

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
     * Gets the value of the idLineaLaboratorio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdLineaLaboratorio() {
        return idLineaLaboratorio;
    }

    /**
     * Sets the value of the idLineaLaboratorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdLineaLaboratorio(String value) {
        this.idLineaLaboratorio = value;
    }

    /**
     * Gets the value of the clave1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClave1() {
        return clave1;
    }

    /**
     * Sets the value of the clave1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClave1(String value) {
        this.clave1 = value;
    }

    /**
     * Gets the value of the clave2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClave2() {
        return clave2;
    }

    /**
     * Sets the value of the clave2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClave2(String value) {
        this.clave2 = value;
    }

    /**
     * Gets the value of the clave3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClave3() {
        return clave3;
    }

    /**
     * Sets the value of the clave3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClave3(String value) {
        this.clave3 = value;
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
     * Gets the value of the tipoIVA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoIVA() {
        return tipoIVA;
    }

    /**
     * Sets the value of the tipoIVA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoIVA(String value) {
        this.tipoIVA = value;
    }

    /**
     * Gets the value of the precioVenta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    /**
     * Sets the value of the precioVenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrecioVenta(BigDecimal value) {
        this.precioVenta = value;
    }

    /**
     * Gets the value of the precioOferta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrecioOferta() {
        return precioOferta;
    }

    /**
     * Sets the value of the precioOferta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrecioOferta(BigDecimal value) {
        this.precioOferta = value;
    }

    /**
     * Gets the value of the precioPublico property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrecioPublico() {
        return precioPublico;
    }

    /**
     * Sets the value of the precioPublico property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrecioPublico(BigDecimal value) {
        this.precioPublico = value;
    }

    /**
     * Gets the value of the habilitado property.
     * 
     */
    public int getHabilitado() {
        return habilitado;
    }

    /**
     * Sets the value of the habilitado property.
     * 
     */
    public void setHabilitado(int value) {
        this.habilitado = value;
    }

    /**
     * Gets the value of the porcentajeDescuentoOferta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPorcentajeDescuentoOferta() {
        return porcentajeDescuentoOferta;
    }

    /**
     * Sets the value of the porcentajeDescuentoOferta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPorcentajeDescuentoOferta(BigDecimal value) {
        this.porcentajeDescuentoOferta = value;
    }

    /**
     * Gets the value of the hasCodigoBarra property.
     * 
     */
    public boolean isHasCodigoBarra() {
        return hasCodigoBarra;
    }

    /**
     * Sets the value of the hasCodigoBarra property.
     * 
     */
    public void setHasCodigoBarra(boolean value) {
        this.hasCodigoBarra = value;
    }

    /**
     * Gets the value of the codigoBarra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoBarra() {
        return codigoBarra;
    }

    /**
     * Sets the value of the codigoBarra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoBarra(String value) {
        this.codigoBarra = value;
    }

    /**
     * Gets the value of the codigoLaboratorio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoLaboratorio() {
        return codigoLaboratorio;
    }

    /**
     * Sets the value of the codigoLaboratorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoLaboratorio(String value) {
        this.codigoLaboratorio = value;
    }

    /**
     * Gets the value of the fechaUlitmoPrecio property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaUlitmoPrecio() {
        return fechaUlitmoPrecio;
    }

    /**
     * Sets the value of the fechaUlitmoPrecio property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaUlitmoPrecio(XMLGregorianCalendar value) {
        this.fechaUlitmoPrecio = value;
    }

    /**
     * Gets the value of the fechaUltimaActualizacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    /**
     * Sets the value of the fechaUltimaActualizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaUltimaActualizacion(XMLGregorianCalendar value) {
        this.fechaUltimaActualizacion = value;
    }

    /**
     * Gets the value of the ofertas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ofertas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOfertas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataOferta }
     * 
     * 
     */
    public List<DataOferta> getOfertas() {
        if (ofertas == null) {
            ofertas = new ArrayList<DataOferta>();
        }
        return this.ofertas;
    }

    /**
     * Gets the value of the precioReceta property.
     * 
     * @return
     *     possible object is
     *     {@link DataPrecioReceta }
     *     
     */
    public DataPrecioReceta getPrecioReceta() {
        return precioReceta;
    }

    /**
     * Sets the value of the precioReceta property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataPrecioReceta }
     *     
     */
    public void setPrecioReceta(DataPrecioReceta value) {
        this.precioReceta = value;
    }

    /**
     * Gets the value of the idProductoNoritel property.
     * 
     */
    public int getIdProductoNoritel() {
        return idProductoNoritel;
    }

    /**
     * Sets the value of the idProductoNoritel property.
     * 
     */
    public void setIdProductoNoritel(int value) {
        this.idProductoNoritel = value;
    }

    /**
     * Gets the value of the idPresentacionNoritel property.
     * 
     */
    public int getIdPresentacionNoritel() {
        return idPresentacionNoritel;
    }

    /**
     * Sets the value of the idPresentacionNoritel property.
     * 
     */
    public void setIdPresentacionNoritel(int value) {
        this.idPresentacionNoritel = value;
    }

}
