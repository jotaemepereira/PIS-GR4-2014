
package uy.com.dusa.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WSConsultaComprobantes", targetNamespace = "http://ws.dusa.com.uy/")
@XmlSeeAlso({
    ComprobanteObjectFactory.class
})
public interface WSConsultaComprobantes {


    /**
     * 
     * @param usuario
     * @param hasta
     * @param password
     * @param desde
     * @return
     *     returns uy.com.dusa.ws.ResultGetComprobantes
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getComprobantesEntreFechas", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetComprobantesEntreFechas")
    @ResponseWrapper(localName = "getComprobantesEntreFechasResponse", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetComprobantesEntreFechasResponse")
    @Action(input = "http://ws.dusa.com.uy/WSConsultaComprobantes/getComprobantesEntreFechasRequest", output = "http://ws.dusa.com.uy/WSConsultaComprobantes/getComprobantesEntreFechasResponse")
    public ResultGetComprobantes getComprobantesEntreFechas(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "desde", targetNamespace = "")
        XMLGregorianCalendar desde,
        @WebParam(name = "hasta", targetNamespace = "")
        XMLGregorianCalendar hasta);

    /**
     * 
     * @param serieCFE
     * @param tipoCFE
     * @param usuario
     * @param nroCFE
     * @param password
     * @return
     *     returns uy.com.dusa.ws.ResultGetComprobante
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getComprobante", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetComprobante")
    @ResponseWrapper(localName = "getComprobanteResponse", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetComprobanteResponse")
    @Action(input = "http://ws.dusa.com.uy/WSConsultaComprobantes/getComprobanteRequest", output = "http://ws.dusa.com.uy/WSConsultaComprobantes/getComprobanteResponse")
    public ResultGetComprobante getComprobante(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "tipoCFE", targetNamespace = "")
        int tipoCFE,
        @WebParam(name = "serieCFE", targetNamespace = "")
        String serieCFE,
        @WebParam(name = "nroCFE", targetNamespace = "")
        int nroCFE);

    /**
     * 
     * @param serieCFE
     * @param tipoCFE
     * @param usuario
     * @param nroCFE
     * @param password
     * @return
     *     returns uy.com.dusa.ws.ResultGetComprobantes
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getComprobantesDesdeNumero", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetComprobantesDesdeNumero")
    @ResponseWrapper(localName = "getComprobantesDesdeNumeroResponse", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetComprobantesDesdeNumeroResponse")
    @Action(input = "http://ws.dusa.com.uy/WSConsultaComprobantes/getComprobantesDesdeNumeroRequest", output = "http://ws.dusa.com.uy/WSConsultaComprobantes/getComprobantesDesdeNumeroResponse")
    public ResultGetComprobantes getComprobantesDesdeNumero(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "tipoCFE", targetNamespace = "")
        int tipoCFE,
        @WebParam(name = "serieCFE", targetNamespace = "")
        String serieCFE,
        @WebParam(name = "nroCFE", targetNamespace = "")
        int nroCFE);

    /**
     * 
     * @param usuario
     * @param password
     * @param desde
     * @return
     *     returns uy.com.dusa.ws.ResultGetComprobantes
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getComprobantesDesdeFecha", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetComprobantesDesdeFecha")
    @ResponseWrapper(localName = "getComprobantesDesdeFechaResponse", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetComprobantesDesdeFechaResponse")
    @Action(input = "http://ws.dusa.com.uy/WSConsultaComprobantes/getComprobantesDesdeFechaRequest", output = "http://ws.dusa.com.uy/WSConsultaComprobantes/getComprobantesDesdeFechaResponse")
    public ResultGetComprobantes getComprobantesDesdeFecha(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "desde", targetNamespace = "")
        XMLGregorianCalendar desde);

}
