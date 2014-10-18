
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
@WebService(name = "WSConsultaStock", targetNamespace = "http://ws.dusa.com.uy/")
@XmlSeeAlso({
    StockObjectFactory.class
})
public interface WSConsultaStock {


    /**
     * 
     * @param usuario
     * @param password
     * @param nroArticulo
     * @return
     *     returns uy.com.dusa.ws.ResultGetStock
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getStock", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetStock")
    @ResponseWrapper(localName = "getStockResponse", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetStockResponse")
    @Action(input = "http://ws.dusa.com.uy/WSConsultaStock/getStockRequest", output = "http://ws.dusa.com.uy/WSConsultaStock/getStockResponse")
    public ResultGetStock getStock(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "nroArticulo", targetNamespace = "")
        int nroArticulo);

    /**
     * 
     * @param usuario
     * @param password
     * @return
     *     returns uy.com.dusa.ws.ResultGetLaboratorios
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getLaboratorios", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetLaboratorios")
    @ResponseWrapper(localName = "getLaboratoriosResponse", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetLaboratoriosResponse")
    @Action(input = "http://ws.dusa.com.uy/WSConsultaStock/getLaboratoriosRequest", output = "http://ws.dusa.com.uy/WSConsultaStock/getLaboratoriosResponse")
    public ResultGetLaboratorios getLaboratorios(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "password", targetNamespace = "")
        String password);

    /**
     * 
     * @param usuario
     * @param password
     * @param desde
     * @return
     *     returns uy.com.dusa.ws.ResultGetStockUpdates
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getStockUpdates", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetStockUpdates")
    @ResponseWrapper(localName = "getStockUpdatesResponse", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetStockUpdatesResponse")
    @Action(input = "http://ws.dusa.com.uy/WSConsultaStock/getStockUpdatesRequest", output = "http://ws.dusa.com.uy/WSConsultaStock/getStockUpdatesResponse")
    public ResultGetStockUpdates getStockUpdates(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "desde", targetNamespace = "")
        XMLGregorianCalendar desde);

    /**
     * 
     * @param usuario
     * @param password
     * @return
     *     returns uy.com.dusa.ws.ResultGetOfertas
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getOfertas", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetOfertas")
    @ResponseWrapper(localName = "getOfertasResponse", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetOfertasResponse")
    @Action(input = "http://ws.dusa.com.uy/WSConsultaStock/getOfertasRequest", output = "http://ws.dusa.com.uy/WSConsultaStock/getOfertasResponse")
    public ResultGetOfertas getOfertas(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "password", targetNamespace = "")
        String password);

    /**
     * 
     * @param usuario
     * @param password
     * @return
     *     returns uy.com.dusa.ws.ResultGetPreciosRecetas
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPreciosRecetas", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetPreciosRecetas")
    @ResponseWrapper(localName = "getPreciosRecetasResponse", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetPreciosRecetasResponse")
    @Action(input = "http://ws.dusa.com.uy/WSConsultaStock/getPreciosRecetasRequest", output = "http://ws.dusa.com.uy/WSConsultaStock/getPreciosRecetasResponse")
    public ResultGetPreciosRecetas getPreciosRecetas(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "password", targetNamespace = "")
        String password);

    /**
     * 
     * @param usuario
     * @param password
     * @return
     *     returns uy.com.dusa.ws.ResultGetTiposIVA
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTiposIVA", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetTiposIVA")
    @ResponseWrapper(localName = "getTiposIVAResponse", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetTiposIVAResponse")
    @Action(input = "http://ws.dusa.com.uy/WSConsultaStock/getTiposIVARequest", output = "http://ws.dusa.com.uy/WSConsultaStock/getTiposIVAResponse")
    public ResultGetTiposIVA getTiposIVA(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "password", targetNamespace = "")
        String password);

    /**
     * 
     * @param usuario
     * @param idLaboratorio
     * @param password
     * @return
     *     returns uy.com.dusa.ws.ResultGetLaboratorio
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getLaboratorio", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetLaboratorio")
    @ResponseWrapper(localName = "getLaboratorioResponse", targetNamespace = "http://ws.dusa.com.uy/", className = "uy.com.dusa.ws.GetLaboratorioResponse")
    @Action(input = "http://ws.dusa.com.uy/WSConsultaStock/getLaboratorioRequest", output = "http://ws.dusa.com.uy/WSConsultaStock/getLaboratorioResponse")
    public ResultGetLaboratorio getLaboratorio(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "idLaboratorio", targetNamespace = "")
        String idLaboratorio);

}