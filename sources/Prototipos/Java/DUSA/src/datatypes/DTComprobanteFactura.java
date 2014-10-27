package datatypes;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

import uy.com.dusa.ws.ComprobanteFormaDePago;
import uy.com.dusa.ws.DataLineaComprobante;
import uy.com.dusa.ws.DataVencimiento;

public class DTComprobanteFactura {
	private int tipoCFE;
    private String serieCFE;
    private int numeroCFE;
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fechaComprobante;
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fechaEmision;
    private int numeroCliente;
   // private ComprobanteFormaDePago formaDePago;
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
  /*  @XmlElement(nillable = true)
    private List<DataLineaComprobante> detalle;
    @XmlElement(nillable = true)
    private List<DataVencimiento> vencimientos;*/
    
    
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
	/*public ComprobanteFormaDePago getFormaDePago() {
		return formaDePago;
	}
	public void setFormaDePago(ComprobanteFormaDePago formaDePago) {
		this.formaDePago = formaDePago;
	}*/
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
}
