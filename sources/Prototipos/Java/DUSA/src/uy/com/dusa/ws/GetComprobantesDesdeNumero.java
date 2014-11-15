
package uy.com.dusa.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getComprobantesDesdeNumero complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getComprobantesDesdeNumero">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoCFE" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="serieCFE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nroCFE" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getComprobantesDesdeNumero", propOrder = {
    "usuario",
    "password",
    "tipoCFE",
    "serieCFE",
    "nroCFE"
})
public class GetComprobantesDesdeNumero {

    protected String usuario;
    protected String password;
    protected int tipoCFE;
    protected String serieCFE;
    protected int nroCFE;

    /**
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuario(String value) {
        this.usuario = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

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
     * Gets the value of the nroCFE property.
     * 
     */
    public int getNroCFE() {
        return nroCFE;
    }

    /**
     * Sets the value of the nroCFE property.
     * 
     */
    public void setNroCFE(int value) {
        this.nroCFE = value;
    }

}
