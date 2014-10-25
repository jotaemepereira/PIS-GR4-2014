
package uy.com.dusa.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WSPedidosService", targetNamespace = "http://ws.dusa.com.uy/", wsdlLocation = "http://dev.dusa.com.uy/ws_pedidos?wsdl")
public class WSPedidosService
    extends Service
{

    private final static URL WSPEDIDOSSERVICE_WSDL_LOCATION;
    private final static WebServiceException WSPEDIDOSSERVICE_EXCEPTION;
    private final static QName WSPEDIDOSSERVICE_QNAME = new QName("http://ws.dusa.com.uy/", "WSPedidosService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://dev.dusa.com.uy/ws_pedidos?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSPEDIDOSSERVICE_WSDL_LOCATION = url;
        WSPEDIDOSSERVICE_EXCEPTION = e;
    }

    public WSPedidosService() {
        super(__getWsdlLocation(), WSPEDIDOSSERVICE_QNAME);
    }

    public WSPedidosService(WebServiceFeature... features) {
        super(__getWsdlLocation(), WSPEDIDOSSERVICE_QNAME, features);
    }

    public WSPedidosService(URL wsdlLocation) {
        super(wsdlLocation, WSPEDIDOSSERVICE_QNAME);
    }

    public WSPedidosService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WSPEDIDOSSERVICE_QNAME, features);
    }

    public WSPedidosService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WSPedidosService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WSPedidos
     */
    @WebEndpoint(name = "WSPedidosPort")
    public WSPedidos getWSPedidosPort() {
        return super.getPort(new QName("http://ws.dusa.com.uy/", "WSPedidosPort"), WSPedidos.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WSPedidos
     */
    @WebEndpoint(name = "WSPedidosPort")
    public WSPedidos getWSPedidosPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.dusa.com.uy/", "WSPedidosPort"), WSPedidos.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSPEDIDOSSERVICE_EXCEPTION!= null) {
            throw WSPEDIDOSSERVICE_EXCEPTION;
        }
        return WSPEDIDOSSERVICE_WSDL_LOCATION;
    }

}
