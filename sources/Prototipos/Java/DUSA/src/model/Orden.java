package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import uy.com.dusa.ws.DataComprobante;
import uy.com.dusa.ws.DataLineaComprobante;
import controladores.Excepciones;
import controladores.FabricaPersistencia;
import datatypes.DTLineaFacturaCompra;

public class Orden {
	private int idProveedor;
	private long idOrden;
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
	private List<OrdenDetalle> detalle = new ArrayList<OrdenDetalle>();
	
	private Boolean procesada;

	// private List<DataVencimiento> vencimientos;
	
	public Orden(){}
	
	public Orden(DataComprobante comprobante, Boolean procesada) throws Excepciones{
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
    	this.procesada = procesada;
    	this.cantidadLineas = comprobante.getCantidadLineas();
    	
    	// Primeramente casteo los articulos de la factura
    	Iterator<DataLineaComprobante> it = comprobante.getDetalle().iterator();
    	while (it.hasNext()) {
			DataLineaComprobante dataLineaComprobante = (DataLineaComprobante) it
					.next();
			OrdenDetalle detalle = new OrdenDetalle(dataLineaComprobante);
			if(detalle.getProductId() != 0){ // el producto se encuentra en el sistema
				this.detalle.add(detalle);
			}else{
				this.detalle = null;
				return;
			}
			
		}
    	
	}

	public int getTipoCFE() {
		return tipoCFE;
	}

	public void setTipoCFE(int tipoCFE) throws Excepciones {
		if (tipoCFE == 0) {
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_DATOS,
					Excepciones.ERROR_DATOS));
		}
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

	public void setNumeroCFE(long numeroCFE) throws Excepciones {
		if (numeroCFE == 0) {
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_DATOS,
					Excepciones.ERROR_DATOS));
		}
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

	public void setMontoNetoGravadoIvaMinimo(
			BigDecimal montoNetoGravadoIvaMinimo) {
		this.montoNetoGravadoIvaMinimo = montoNetoGravadoIvaMinimo;
	}

	public BigDecimal getMontoNetoGravadoIvaBasico() {
		return montoNetoGravadoIvaBasico;
	}

	public void setMontoNetoGravadoIvaBasico(
			BigDecimal montoNetoGravadoIvaBasico) {
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

	public void setCantidadLineas(Integer cantidadLineas) throws Excepciones {
		if (cantidadLineas == 0) {
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_DATOS,
					Excepciones.ERROR_DATOS));
		}
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

	public void setMontoTotalAPagar(BigDecimal montoTotalAPagar)
			throws Excepciones {
		if ((montoTotalAPagar == null)
				|| (montoTotalAPagar == (new BigDecimal(0)))) {
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_DATOS,
					Excepciones.ERROR_DATOS));
		}
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

	public List<OrdenDetalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<OrdenDetalle> detalle) {
		this.detalle = detalle;
	}

	public String getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}

	public long getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(long idOrden) {
		this.idOrden = idOrden;
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) throws Excepciones {
		if(idProveedor == 0){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_DATOS, Excepciones.ERROR_DATOS));
		}
		this.idProveedor = idProveedor;
	}

	public Boolean getProcesada() {
		return procesada;
	}

	public void setProcesada(Boolean procesada) {
		this.procesada = procesada;
	}

}
