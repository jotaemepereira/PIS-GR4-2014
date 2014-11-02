package datatypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import controladores.FabricaPersistencia;
import uy.com.dusa.ws.DataComprobante;
import uy.com.dusa.ws.DataLineaComprobante;

public class DTComprobanteFactura {
	
	private int idProveedor;
	private int idOrden;
	private int tipoCFE;
    private String serieCFE;
    private long numeroCFE;
    private Date fechaComprobante;
    private String formaDePago;
    private long ordenDeCompra;
    private BigDecimal montoNoGravado = new BigDecimal(0);
    private BigDecimal montoNetoGravadoIvaMinimo = new BigDecimal(0);
    private BigDecimal montoNetoGravadoIvaBasico = new BigDecimal(0);
    private BigDecimal totalIvaMinimo = new BigDecimal(0);
    private BigDecimal totalIvaBasico = new BigDecimal(0);
    private BigDecimal montoTotal = new BigDecimal(0);
    private Integer cantidadLineas;
    private BigDecimal montoRetenidoIVA = new BigDecimal(0);
    private BigDecimal montoRetenidoIRAE = new BigDecimal(0);
    private BigDecimal montoNoFacturable = new BigDecimal(0);
    private BigDecimal montoTotalAPagar = new BigDecimal(0);
    private BigDecimal montoTributoIvaMinimo = new BigDecimal(0);
    private BigDecimal montoTributoIvaBasico = new BigDecimal(0);
    private List<DTLineaFacturaCompra> detalle = new ArrayList<DTLineaFacturaCompra>();
   // private List<DataVencimiento> vencimientos;

    // suma de los subtotales de cada producto individual
    private BigDecimal subtotalProdctos = new BigDecimal(0);
    
    public DTComprobanteFactura(){}
    
    public DTComprobanteFactura(DataComprobante comprobante){
    	this.idProveedor = 1;
    	this.tipoCFE = comprobante.getTipoCFE();
    	this.serieCFE = comprobante.getSerieCFE();
    	this.numeroCFE = comprobante.getNumeroCFE();
    	this.fechaComprobante = comprobante.getFechaComprobante().toGregorianCalendar().getTime();
    	this.formaDePago = comprobante.getFormaDePago().name();
    	this.ordenDeCompra = comprobante.getOrdenDeCompra();
    	this.montoNoGravado = comprobante.getMontoNoGravado();
    	this.montoNetoGravadoIvaMinimo = comprobante.getMontoNetoGravadoIvaMinimo();
    	this.montoNetoGravadoIvaBasico = comprobante.getMontoNetoGravadoIvaBasico();
    	this.totalIvaMinimo = comprobante.getTotalIvaMinimo();
    	this.totalIvaBasico = comprobante.getTotalIvaBasico();
    	this.montoTotal = comprobante.getMontoTotal();
    	this.montoRetenidoIVA = comprobante.getMontoRetenidoIVA();
    	this.montoRetenidoIRAE = comprobante.getMontoRetenidoIRAE();
    	this.montoNoFacturable = comprobante.getMontoNoFacturable();
    	this.montoTotalAPagar = comprobante.getMontoTotalAPagar();
    	this.montoTributoIvaMinimo = comprobante.getMontoTributoIvaMinimo();
    	this.montoTributoIvaBasico = comprobante.getMontoTributoIvaBasico();
    	
    	Iterator<DataLineaComprobante> it = comprobante.getDetalle().iterator();
    	while (it.hasNext()) {
			DataLineaComprobante dataLineaComprobante = (DataLineaComprobante) it
					.next();
			this.detalle.add(new DTLineaFacturaCompra(dataLineaComprobante));
		}
    	
    }
    
	public int getTipoCFE() {
		return tipoCFE;
	}
	public void setTipoCFE(int tipoCFE) {
		this.tipoCFE = tipoCFE;
	}
	public String getSerieCFE() {
		return serieCFE;
	}
	public void setSerieCFE(String serieCFE) {
		this.serieCFE = serieCFE;
	}
	public long getNumeroCFE() {
		return numeroCFE;
	}
	public void setNumeroCFE(long numeroCFE) {
		this.numeroCFE = numeroCFE;
	}
	public long getOrdenDeCompra() {
		return ordenDeCompra;
	}
	public void setOrdenDeCompra(long ordenDeCompra) {
		this.ordenDeCompra = ordenDeCompra;
	}
	public BigDecimal getMontoNoGravado() {
		return montoNoGravado;
	}
	public void setMontoNoGravado(BigDecimal montoNoGravado) {
		this.montoNoGravado = montoNoGravado;
	}
	public BigDecimal getMontoNetoGravadoIvaMinimo() {
		return montoNetoGravadoIvaMinimo;
	}
	public void setMontoNetoGravadoIvaMinimo(BigDecimal montoNetoGravadoIvaMinimo) {
		this.montoNetoGravadoIvaMinimo = montoNetoGravadoIvaMinimo;
	}
	public BigDecimal getMontoNetoGravadoIvaBasico() {
		return montoNetoGravadoIvaBasico;
	}
	public void setMontoNetoGravadoIvaBasico(BigDecimal montoNetoGravadoIvaBasico) {
		this.montoNetoGravadoIvaBasico = montoNetoGravadoIvaBasico;
	}
	public BigDecimal getTotalIvaMinimo() {
		return totalIvaMinimo;
	}
	public void setTotalIvaMinimo(BigDecimal totalIvaMinimo) {
		this.totalIvaMinimo = totalIvaMinimo;
	}
	public BigDecimal getTotalIvaBasico() {
		return totalIvaBasico;
	}
	public void setTotalIvaBasico(BigDecimal totalIvaBasico) {
		this.totalIvaBasico = totalIvaBasico;
	}
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
	public Integer getCantidadLineas() {
		return cantidadLineas;
	}
	public void setCantidadLineas(Integer cantidadLineas) {
		this.cantidadLineas = cantidadLineas;
	}
	public BigDecimal getMontoRetenidoIVA() {
		return montoRetenidoIVA;
	}
	public void setMontoRetenidoIVA(BigDecimal montoRetenidoIVA) {
		this.montoRetenidoIVA = montoRetenidoIVA;
	}
	public BigDecimal getMontoRetenidoIRAE() {
		return montoRetenidoIRAE;
	}
	public void setMontoRetenidoIRAE(BigDecimal montoRetenidoIRAE) {
		this.montoRetenidoIRAE = montoRetenidoIRAE;
	}
	public BigDecimal getMontoNoFacturable() {
		return montoNoFacturable;
	}
	public void setMontoNoFacturable(BigDecimal montoNoFacturable) {
		this.montoNoFacturable = montoNoFacturable;
	}
	public BigDecimal getMontoTotalAPagar() {
		return montoTotalAPagar;
	}
	public void setMontoTotalAPagar(BigDecimal montoTotalAPagar) {
		this.montoTotalAPagar = montoTotalAPagar;
	}
	public BigDecimal getMontoTributoIvaMinimo() {
		return montoTributoIvaMinimo;
	}
	public void setMontoTributoIvaMinimo(BigDecimal montoTributoIvaMinimo) {
		this.montoTributoIvaMinimo = montoTributoIvaMinimo;
	}
	public BigDecimal getMontoTributoIvaBasico() {
		return montoTributoIvaBasico;
	}
	public void setMontoTributoIvaBasico(BigDecimal montoTributoIvaBasico) {
		this.montoTributoIvaBasico = montoTributoIvaBasico;
	}
	public Date getFechaComprobante() {
		return fechaComprobante;
	}
	public void setFechaComprobante(Date fechaComprobante) {
		this.fechaComprobante = fechaComprobante;
	}
	public List<DTLineaFacturaCompra> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<DTLineaFacturaCompra> detalle) {
		this.detalle = detalle;
	}
	public String getFormaDePago() {
		return formaDePago;
	}
	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}
	public int getIdOrden() {
		return idOrden;
	}
	public void setIdOrden(int idOrden) {
		this.idOrden = idOrden;
	}
	public int getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	public BigDecimal getSubtotalProdctos() {
		return subtotalProdctos;
	}
	public void setSubtotalProdctos(BigDecimal subtotalProdctos) {
		this.subtotalProdctos = subtotalProdctos;
	}

}
