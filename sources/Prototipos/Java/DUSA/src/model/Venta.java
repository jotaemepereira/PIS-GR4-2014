package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Venta")
public class Venta implements Serializable {
	long ventaId;
	int clienteId;
	Cliente cliente;
	int usuarioId;
	Usuario usuario;
	Date fechaVenta;
	char tipoVenta;
	int tipoDgi;
	String serial;
	String formaDePago;
	BigDecimal montoNoGravado;
	BigDecimal montoNetoGravadoIvaMinimo;
	BigDecimal montoNetoGravadoIvaBasico;
	BigDecimal totalIvaMinimo;
	BigDecimal totalIvaBasico;
	BigDecimal montoTotal;
	BigDecimal montoRetenidoIVA;
	BigDecimal montoRetenidoIRAE;
	BigDecimal montoNoFacturable;
	BigDecimal montoTributoIvaMinimo;
	BigDecimal montoTributoIvaBasico;
	BigDecimal montoTotalAPagar;
	int cantidadLineas;
	List<LineaVenta> lineas;
	String estadoVenta;

	public long getVentaId() {
		return ventaId;
	}

	public void setVentaId(long ventaId) {
		this.ventaId = ventaId;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public char getTipoVenta() {
		return tipoVenta;
	}

	public void setTipoVenta(char tipoVenta) {
		this.tipoVenta = tipoVenta;
	}

	public int getTipoDgi() {
		return tipoDgi;
	}

	public void setTipoDgi(int tipoDgi) {
		this.tipoDgi = tipoDgi;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
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

	public BigDecimal getMontoTotalAPagar() {
		return montoTotalAPagar;
	}

	public void setMontoTotalAPagar(BigDecimal montoTotalAPagar) {
		this.montoTotalAPagar = montoTotalAPagar;
	}

	public int getCantidadLineas() {
		return cantidadLineas;
	}

	public void setCantidadLineas(int cantidadLineas) {
		this.cantidadLineas = cantidadLineas;
	}

	public List<LineaVenta> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaVenta> lineas) {
		this.lineas = lineas;
	}

	public String getEstadoVenta() {
		return estadoVenta;
	}

	public void setEstadoVenta(String estadoVenta) {
		this.estadoVenta = estadoVenta;
	}
}
