
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
@WebServiceClient(name = "WSConsultaComprobantesService", targetNamespace = "http://ws.dusa.com.uy/", wsdlLocation = "http://dev.dusa.com.uy/ws_consulta_comprobantes?wsdl")
public class WSConsultaComprobantesService
    extends Service
{

    private final static URL WSCONSULTACOMPROBANTESSERVICE_WSDL_LOCATION;
    private final static WebServiceException WSCONSULTACOMPROBANTESSERVICE_EXCEPTION;
    private final static QName WSCONSULTACOMPROBANTESSERVICE_QNAME = new QName("http://ws.dusa.com.uy/", "WSConsultaComprobantesService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://dev.dusa.com.uy/ws_consulta_comprobantes?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSCONSULTACOMPROBANTESSERVICE_WSDL_LOCATION = url;
        WSCONSULTACOMPROBANTESSERVICE_EXCEPTION = e;
    }

    public WSConsultaComprobantesService() {
        super(__getWsdlLocation(), WSCONSULTACOMPROBANTESSERVICE_QNAME);
    }

    public WSConsultaComprobantesService(WebServiceFeature... features) {
        super(__getWsdlLocation(), WSCONSULTACOMPROBANTESSERVICE_QNAME, features);
    }

    public WSConsultaComprobantesService(URL wsdlLocation) {
        super(wsdlLocation, WSCONSULTACOMPROBANTESSERVICE_QNAME);
    }

    public WSConsultaComprobantesService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WSCONSULTACOMPROBANTESSERVICE_QNAME, features);
    }

    public WSConsultaComprobantesService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WSConsultaComprobantesService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WSConsultaComprobantes
     */
    @WebEndpoint(name = "WSConsultaComprobantesPort")
    public WSConsultaComprobantes getWSConsultaComprobantesPort() {
        return super.getPort(new QName("http://ws.dusa.com.uy/", "WSConsultaComprobantesPort"), WSConsultaComprobantes.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WSConsultaComprobantes
     */
    @WebEndpoint(name = "WSConsultaComprobantesPort")
    public WSConsultaComprobantes getWSConsultaComprobantesPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.dusa.com.uy/", "WSConsultaComprobantesPort"), WSConsultaComprobantes.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSCONSULTACOMPROBANTESSERVICE_EXCEPTION!= null) {
            throw WSCONSULTACOMPROBANTESSERVICE_EXCEPTION;
        }
        return WSCONSULTACOMPROBANTESSERVICE_WSDL_LOCATION;
    }

}
