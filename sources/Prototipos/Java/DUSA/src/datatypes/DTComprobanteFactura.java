package datatypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

public class DTComprobanteFactura {
	private int idOrden;
	private int tipoCFE;
    private String serieCFE;
    private int numeroCFE;
    private XMLGregorianCalendar fechaComprobante;
    private int numeroCliente;
    private String formaDePago;
    private long ordenDeCompra;
    private BigDecimal montoNoGravado;
    private BigDecimal montoNetoGravadoIvaMinimo;
    private BigDecimal montoNetoGravadoIvaBasico;
    private BigDecimal totalIvaMinimo;
    private BigDecimal totalIvaBasico;
    private BigDecimal montoTotal;
    private Integer cantidadLineas;
    private BigDecimal montoRetenidoIVA;
    private BigDecimal montoRetenidoIRAE;
    private BigDecimal montoNoFacturable;
    private BigDecimal montoTotalAPagar;
    private BigDecimal montoTributoIvaMinimo;
    private BigDecimal montoTributoIvaBasico;
    private List<DTLineaFacturaCompra> detalle = new ArrayList<DTLineaFacturaCompra>();
   // private List<DataVencimiento> vencimientos;
    
    
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
	public int getNumeroCFE() {
		return numeroCFE;
	}
	public void setNumeroCFE(int numeroCFE) {
		this.numeroCFE = numeroCFE;
	}
	public int getNumeroCliente() {
		return numeroCliente;
	}
	public void setNumeroCliente(int numeroCliente) {
		this.numeroCliente = numeroCliente;
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
	public XMLGregorianCalendar getFechaComprobante() {
		return fechaComprobante;
	}
	public void setFechaComprobante(XMLGregorianCalendar fechaComprobante) {
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
}
