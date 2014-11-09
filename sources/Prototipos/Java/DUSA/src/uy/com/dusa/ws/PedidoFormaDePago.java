
package uy.com.dusa.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pedidoFormaDePago.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="pedidoFormaDePago">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CONTADO"/>
 *     &lt;enumeration value="CREDITO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "pedidoFormaDePago")
@XmlEnum
public enum PedidoFormaDePago {

    CONTADO,
    CREDITO;

    public String value() {
        return name();
    }

    public static PedidoFormaDePago fromValue(String v) {
        return valueOf(v);
    }

}
