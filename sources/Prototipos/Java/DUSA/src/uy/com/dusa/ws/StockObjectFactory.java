
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
public class StockObjectFactory {

    private final static QName _GetLaboratoriosResponse_QNAME = new QName("http://ws.dusa.com.uy/", "getLaboratoriosResponse");
    private final static QName _GetLaboratorio_QNAME = new QName("http://ws.dusa.com.uy/", "getLaboratorio");
    private final static QName _GetOfertasResponse_QNAME = new QName("http://ws.dusa.com.uy/", "getOfertasResponse");
    private final static QName _GetTiposIVA_QNAME = new QName("http://ws.dusa.com.uy/", "getTiposIVA");
    private final static QName _GetPreciosRecetasResponse_QNAME = new QName("http://ws.dusa.com.uy/", "getPreciosRecetasResponse");
    private final static QName _GetStockUpdatesResponse_QNAME = new QName("http://ws.dusa.com.uy/", "getStockUpdatesResponse");
    private final static QName _GetStock_QNAME = new QName("http://ws.dusa.com.uy/", "getStock");
    private final static QName _GetTiposIVAResponse_QNAME = new QName("http://ws.dusa.com.uy/", "getTiposIVAResponse");
    private final static QName _GetStockResponse_QNAME = new QName("http://ws.dusa.com.uy/", "getStockResponse");
    private final static QName _GetOfertas_QNAME = new QName("http://ws.dusa.com.uy/", "getOfertas");
    private final static QName _GetPreciosRecetas_QNAME = new QName("http://ws.dusa.com.uy/", "getPreciosRecetas");
    private final static QName _GetLaboratorioResponse_QNAME = new QName("http://ws.dusa.com.uy/", "getLaboratorioResponse");
    private final static QName _GetStockUpdates_QNAME = new QName("http://ws.dusa.com.uy/", "getStockUpdates");
    private final static QName _GetLaboratorios_QNAME = new QName("http://ws.dusa.com.uy/", "getLaboratorios");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: uy.com.dusa.ws
     * 
     */
    public StockObjectFactory() {
    }

    /**
     * Create an instance of {@link GetStock }
     * 
     */
    public GetStock createGetStock() {
        return new GetStock();
    }

    /**
     * Create an instance of {@link GetStockResponse }
     * 
     */
    public GetStockResponse createGetStockResponse() {
        return new GetStockResponse();
    }

    /**
     * Create an instance of {@link GetLaboratorios }
     * 
     */
    public GetLaboratorios createGetLaboratorios() {
        return new GetLaboratorios();
    }

    /**
     * Create an instance of {@link GetStockUpdates }
     * 
     */
    public GetStockUpdates createGetStockUpdates() {
        return new GetStockUpdates();
    }

    /**
     * Create an instance of {@link GetPreciosRecetas }
     * 
     */
    public GetPreciosRecetas createGetPreciosRecetas() {
        return new GetPreciosRecetas();
    }

    /**
     * Create an instance of {@link GetTiposIVA }
     * 
     */
    public GetTiposIVA createGetTiposIVA() {
        return new GetTiposIVA();
    }

    /**
     * Create an instance of {@link GetOfertasResponse }
     * 
     */
    public GetOfertasResponse createGetOfertasResponse() {
        return new GetOfertasResponse();
    }

    /**
     * Create an instance of {@link GetPreciosRecetasResponse }
     * 
     */
    public GetPreciosRecetasResponse createGetPreciosRecetasResponse() {
        return new GetPreciosRecetasResponse();
    }

    /**
     * Create an instance of {@link GetTiposIVAResponse }
     * 
     */
    public GetTiposIVAResponse createGetTiposIVAResponse() {
        return new GetTiposIVAResponse();
    }

    /**
     * Create an instance of {@link GetLaboratorioResponse }
     * 
     */
    public GetLaboratorioResponse createGetLaboratorioResponse() {
        return new GetLaboratorioResponse();
    }

    /**
     * Create an instance of {@link GetOfertas }
     * 
     */
    public GetOfertas createGetOfertas() {
        return new GetOfertas();
    }

    /**
     * Create an instance of {@link GetLaboratorio }
     * 
     */
    public GetLaboratorio createGetLaboratorio() {
        return new GetLaboratorio();
    }

    /**
     * Create an instance of {@link GetLaboratoriosResponse }
     * 
     */
    public GetLaboratoriosResponse createGetLaboratoriosResponse() {
        return new GetLaboratoriosResponse();
    }

    /**
     * Create an instance of {@link GetStockUpdatesResponse }
     * 
     */
    public GetStockUpdatesResponse createGetStockUpdatesResponse() {
        return new GetStockUpdatesResponse();
    }

    /**
     * Create an instance of {@link DataLineaLaboratorio }
     * 
     */
    public DataLineaLaboratorio createDataLineaLaboratorio() {
        return new DataLineaLaboratorio();
    }

    /**
     * Create an instance of {@link DataIVA }
     * 
     */
    public DataIVA createDataIVA() {
        return new DataIVA();
    }

    /**
     * Create an instance of {@link DataLaboratorio }
     * 
     */
    public DataLaboratorio createDataLaboratorio() {
        return new DataLaboratorio();
    }

    /**
     * Create an instance of {@link DataOferta }
     * 
     */
    public DataOferta createDataOferta() {
        return new DataOferta();
    }

    /**
     * Create an instance of {@link ResultGetPreciosRecetas }
     * 
     */
    public ResultGetPreciosRecetas createResultGetPreciosRecetas() {
        return new ResultGetPreciosRecetas();
    }

    /**
     * Create an instance of {@link ResultGetLaboratorio }
     * 
     */
    public ResultGetLaboratorio createResultGetLaboratorio() {
        return new ResultGetLaboratorio();
    }

    /**
     * Create an instance of {@link ResultGetStockUpdates }
     * 
     */
    public ResultGetStockUpdates createResultGetStockUpdates() {
        return new ResultGetStockUpdates();
    }

    /**
     * Create an instance of {@link ResultGetOfertas }
     * 
     */
    public ResultGetOfertas createResultGetOfertas() {
        return new ResultGetOfertas();
    }

    /**
     * Create an instance of {@link DataPrecioReceta }
     * 
     */
    public DataPrecioReceta createDataPrecioReceta() {
        return new DataPrecioReceta();
    }

    /**
     * Create an instance of {@link ResultGetTiposIVA }
     * 
     */
    public ResultGetTiposIVA createResultGetTiposIVA() {
        return new ResultGetTiposIVA();
    }

    /**
     * Create an instance of {@link DataInfoProducto }
     * 
     */
    public DataInfoProducto createDataInfoProducto() {
        return new DataInfoProducto();
    }

    /**
     * Create an instance of {@link ResultGetStock }
     * 
     */
    public ResultGetStock createResultGetStock() {
        return new ResultGetStock();
    }

    /**
     * Create an instance of {@link MensajeError }
     * 
     */
    public MensajeError createMensajeError() {
        return new MensajeError();
    }

    /**
     * Create an instance of {@link ResultGetLaboratorios }
     * 
     */
    public ResultGetLaboratorios createResultGetLaboratorios() {
        return new ResultGetLaboratorios();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLaboratoriosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getLaboratoriosResponse")
    public JAXBElement<GetLaboratoriosResponse> createGetLaboratoriosResponse(GetLaboratoriosResponse value) {
        return new JAXBElement<GetLaboratoriosResponse>(_GetLaboratoriosResponse_QNAME, GetLaboratoriosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLaboratorio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getLaboratorio")
    public JAXBElement<GetLaboratorio> createGetLaboratorio(GetLaboratorio value) {
        return new JAXBElement<GetLaboratorio>(_GetLaboratorio_QNAME, GetLaboratorio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOfertasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getOfertasResponse")
    public JAXBElement<GetOfertasResponse> createGetOfertasResponse(GetOfertasResponse value) {
        return new JAXBElement<GetOfertasResponse>(_GetOfertasResponse_QNAME, GetOfertasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTiposIVA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getTiposIVA")
    public JAXBElement<GetTiposIVA> createGetTiposIVA(GetTiposIVA value) {
        return new JAXBElement<GetTiposIVA>(_GetTiposIVA_QNAME, GetTiposIVA.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPreciosRecetasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getPreciosRecetasResponse")
    public JAXBElement<GetPreciosRecetasResponse> createGetPreciosRecetasResponse(GetPreciosRecetasResponse value) {
        return new JAXBElement<GetPreciosRecetasResponse>(_GetPreciosRecetasResponse_QNAME, GetPreciosRecetasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStockUpdatesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getStockUpdatesResponse")
    public JAXBElement<GetStockUpdatesResponse> createGetStockUpdatesResponse(GetStockUpdatesResponse value) {
        return new JAXBElement<GetStockUpdatesResponse>(_GetStockUpdatesResponse_QNAME, GetStockUpdatesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getStock")
    public JAXBElement<GetStock> createGetStock(GetStock value) {
        return new JAXBElement<GetStock>(_GetStock_QNAME, GetStock.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTiposIVAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getTiposIVAResponse")
    public JAXBElement<GetTiposIVAResponse> createGetTiposIVAResponse(GetTiposIVAResponse value) {
        return new JAXBElement<GetTiposIVAResponse>(_GetTiposIVAResponse_QNAME, GetTiposIVAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStockResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getStockResponse")
    public JAXBElement<GetStockResponse> createGetStockResponse(GetStockResponse value) {
        return new JAXBElement<GetStockResponse>(_GetStockResponse_QNAME, GetStockResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOfertas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getOfertas")
    public JAXBElement<GetOfertas> createGetOfertas(GetOfertas value) {
        return new JAXBElement<GetOfertas>(_GetOfertas_QNAME, GetOfertas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPreciosRecetas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getPreciosRecetas")
    public JAXBElement<GetPreciosRecetas> createGetPreciosRecetas(GetPreciosRecetas value) {
        return new JAXBElement<GetPreciosRecetas>(_GetPreciosRecetas_QNAME, GetPreciosRecetas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLaboratorioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getLaboratorioResponse")
    public JAXBElement<GetLaboratorioResponse> createGetLaboratorioResponse(GetLaboratorioResponse value) {
        return new JAXBElement<GetLaboratorioResponse>(_GetLaboratorioResponse_QNAME, GetLaboratorioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStockUpdates }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getStockUpdates")
    public JAXBElement<GetStockUpdates> createGetStockUpdates(GetStockUpdates value) {
        return new JAXBElement<GetStockUpdates>(_GetStockUpdates_QNAME, GetStockUpdates.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLaboratorios }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dusa.com.uy/", name = "getLaboratorios")
    public JAXBElement<GetLaboratorios> createGetLaboratorios(GetLaboratorios value) {
        return new JAXBElement<GetLaboratorios>(_GetLaboratorios_QNAME, GetLaboratorios.class, null, value);
    }

}
