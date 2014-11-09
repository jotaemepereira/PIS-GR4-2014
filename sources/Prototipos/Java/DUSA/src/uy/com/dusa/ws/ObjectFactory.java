
package uy.com.dusa.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the uy.com.dusa.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RealizarPedidoSimpleResponse_QNAME = new QName("http://ws.dusa.com.uy/", "realizarPedidoSimpleResponse");
    private final static QName _RealizarPedidoSimple_QNAME = new QName("http://ws.dusa.com.uy/", "realizarPedidoSimple");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: uy.com.dusa.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RealizarPedidoSimple }
     * 
     */
    public RealizarPedidoSimple createRealizarPedidoSimple() {
        return new RealizarPedidoSimple();
    }

    /**
     * Create an instance of {@link RealizarPedidoSimpleResponse }
     * 
     */
    public RealizarPedidoSimpleResponse createRealizarPedidoSimpleResponse() {
        return new RealizarPedidoSimpleResponse();
    }

    /**
     * Create an instance of {@link DataLineaPedidoSimple }
     * 
     */
    public DataLineaPedidoSimple createDataLineaPedidoSimple() {
        return new DataLineaPedidoSimple();
    }

    /**
     * Create an instance of {@link DataPedidoSimple }
     * 
     */
    public DataPedidoSimple createDataPedidoSimple() {
        return new DataPedidoSimple();
    }

    /**
     * Create an instance of {@link ResultRealizarPedido }
     * 
     */
    public ResultRealizarPedido createResultRealizarPedido() {
        return new ResultRealizarPedido();
    }

    /**
     * Create an instance of {@link MensajeError }
     * 
     */
    public MensajeError createMensajeError() {
        return new MensajeError();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RealizarPedidoSimpleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "realizarPedidoSimpleResponse")
    public JAXBElement<RealizarPedidoSimpleResponse> createRealizarPedidoSimpleResponse(RealizarPedidoSimpleResponse value) {
        return new JAXBElement<RealizarPedidoSimpleResponse>(_RealizarPedidoSimpleResponse_QNAME, RealizarPedidoSimpleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RealizarPedidoSimple }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "realizarPedidoSimple")
    public JAXBElement<RealizarPedidoSimple> createRealizarPedidoSimple(RealizarPedidoSimple value) {
        return new JAXBElement<RealizarPedidoSimple>(_RealizarPedidoSimple_QNAME, RealizarPedidoSimple.class, null, value);
    }

}
