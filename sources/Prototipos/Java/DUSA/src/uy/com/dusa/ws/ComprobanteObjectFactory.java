
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
public class ComprobanteObjectFactory {

    private final static QName _GetComprobantesDesdeNumeroResponse_QNAME = new QName("http://ws.dusa.com.uy/", "getComprobantesDesdeNumeroResponse");
    private final static QName _GetComprobanteResponse_QNAME = new QName("http://ws.dusa.com.uy/", "getComprobanteResponse");
    private final static QName _GetComprobantesDesdeFechaResponse_QNAME = new QName("http://ws.dusa.com.uy/", "getComprobantesDesdeFechaResponse");
    private final static QName _GetComprobantesEntreFechas_QNAME = new QName("http://ws.dusa.com.uy/", "getComprobantesEntreFechas");
    private final static QName _GetComprobantesDesdeFecha_QNAME = new QName("http://ws.dusa.com.uy/", "getComprobantesDesdeFecha");
    private final static QName _GetComprobantesEntreFechasResponse_QNAME = new QName("http://ws.dusa.com.uy/", "getComprobantesEntreFechasResponse");
    private final static QName _GetComprobantesDesdeNumero_QNAME = new QName("http://ws.dusa.com.uy/", "getComprobantesDesdeNumero");
    private final static QName _GetComprobante_QNAME = new QName("http://ws.dusa.com.uy/", "getComprobante");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: uy.com.dusa.ws
     * 
     */
    public ComprobanteObjectFactory() {
    }

    /**
     * Create an instance of {@link GetComprobantesDesdeNumero }
     * 
     */
    public GetComprobantesDesdeNumero createGetComprobantesDesdeNumero() {
        return new GetComprobantesDesdeNumero();
    }

    /**
     * Create an instance of {@link GetComprobante }
     * 
     */
    public GetComprobante createGetComprobante() {
        return new GetComprobante();
    }

    /**
     * Create an instance of {@link GetComprobanteResponse }
     * 
     */
    public GetComprobanteResponse createGetComprobanteResponse() {
        return new GetComprobanteResponse();
    }

    /**
     * Create an instance of {@link GetComprobantesDesdeNumeroResponse }
     * 
     */
    public GetComprobantesDesdeNumeroResponse createGetComprobantesDesdeNumeroResponse() {
        return new GetComprobantesDesdeNumeroResponse();
    }

    /**
     * Create an instance of {@link GetComprobantesEntreFechas }
     * 
     */
    public GetComprobantesEntreFechas createGetComprobantesEntreFechas() {
        return new GetComprobantesEntreFechas();
    }

    /**
     * Create an instance of {@link GetComprobantesDesdeFechaResponse }
     * 
     */
    public GetComprobantesDesdeFechaResponse createGetComprobantesDesdeFechaResponse() {
        return new GetComprobantesDesdeFechaResponse();
    }

    /**
     * Create an instance of {@link GetComprobantesEntreFechasResponse }
     * 
     */
    public GetComprobantesEntreFechasResponse createGetComprobantesEntreFechasResponse() {
        return new GetComprobantesEntreFechasResponse();
    }

    /**
     * Create an instance of {@link GetComprobantesDesdeFecha }
     * 
     */
    public GetComprobantesDesdeFecha createGetComprobantesDesdeFecha() {
        return new GetComprobantesDesdeFecha();
    }

    /**
     * Create an instance of {@link DataLineaComprobante }
     * 
     */
    public DataLineaComprobante createDataLineaComprobante() {
        return new DataLineaComprobante();
    }

    /**
     * Create an instance of {@link DataVencimiento }
     * 
     */
    public DataVencimiento createDataVencimiento() {
        return new DataVencimiento();
    }

    /**
     * Create an instance of {@link DataComprobante }
     * 
     */
    public DataComprobante createDataComprobante() {
        return new DataComprobante();
    }

    /**
     * Create an instance of {@link ResultGetComprobantes }
     * 
     */
    public ResultGetComprobantes createResultGetComprobantes() {
        return new ResultGetComprobantes();
    }

    /**
     * Create an instance of {@link ResultGetComprobante }
     * 
     */
    public ResultGetComprobante createResultGetComprobante() {
        return new ResultGetComprobante();
    }

    /**
     * Create an instance of {@link MensajeError }
     * 
     */
    public MensajeError createMensajeError() {
        return new MensajeError();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComprobantesDesdeNumeroResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getComprobantesDesdeNumeroResponse")
    public JAXBElement<GetComprobantesDesdeNumeroResponse> createGetComprobantesDesdeNumeroResponse(GetComprobantesDesdeNumeroResponse value) {
        return new JAXBElement<GetComprobantesDesdeNumeroResponse>(_GetComprobantesDesdeNumeroResponse_QNAME, GetComprobantesDesdeNumeroResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComprobanteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getComprobanteResponse")
    public JAXBElement<GetComprobanteResponse> createGetComprobanteResponse(GetComprobanteResponse value) {
        return new JAXBElement<GetComprobanteResponse>(_GetComprobanteResponse_QNAME, GetComprobanteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComprobantesDesdeFechaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getComprobantesDesdeFechaResponse")
    public JAXBElement<GetComprobantesDesdeFechaResponse> createGetComprobantesDesdeFechaResponse(GetComprobantesDesdeFechaResponse value) {
        return new JAXBElement<GetComprobantesDesdeFechaResponse>(_GetComprobantesDesdeFechaResponse_QNAME, GetComprobantesDesdeFechaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComprobantesEntreFechas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getComprobantesEntreFechas")
    public JAXBElement<GetComprobantesEntreFechas> createGetComprobantesEntreFechas(GetComprobantesEntreFechas value) {
        return new JAXBElement<GetComprobantesEntreFechas>(_GetComprobantesEntreFechas_QNAME, GetComprobantesEntreFechas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComprobantesDesdeFecha }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getComprobantesDesdeFecha")
    public JAXBElement<GetComprobantesDesdeFecha> createGetComprobantesDesdeFecha(GetComprobantesDesdeFecha value) {
        return new JAXBElement<GetComprobantesDesdeFecha>(_GetComprobantesDesdeFecha_QNAME, GetComprobantesDesdeFecha.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComprobantesEntreFechasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getComprobantesEntreFechasResponse")
    public JAXBElement<GetComprobantesEntreFechasResponse> createGetComprobantesEntreFechasResponse(GetComprobantesEntreFechasResponse value) {
        return new JAXBElement<GetComprobantesEntreFechasResponse>(_GetComprobantesEntreFechasResponse_QNAME, GetComprobantesEntreFechasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComprobantesDesdeNumero }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getComprobantesDesdeNumero")
    public JAXBElement<GetComprobantesDesdeNumero> createGetComprobantesDesdeNumero(GetComprobantesDesdeNumero value) {
        return new JAXBElement<GetComprobantesDesdeNumero>(_GetComprobantesDesdeNumero_QNAME, GetComprobantesDesdeNumero.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComprobante }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getComprobante")
    public JAXBElement<GetComprobante> createGetComprobante(GetComprobante value) {
        return new JAXBElement<GetComprobante>(_GetComprobante_QNAME, GetComprobante.class, null, value);
    }

}
