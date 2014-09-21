package model;

import java.math.BigDecimal;
import java.util.Date;

public class Articulo {
	private int idArticulo;
	private int numero;
	private char tipoArticulo;
	private String descripcion;
	private int idProveedor;
	private String clave1;
	private String clave2;
	private String clave3;
	private boolean esPsicofarmaco;
	private boolean esEstupefaciente;
	private char codigoVenta;
	private char tipoAutorizacion;
	private BigDecimal precioUnitario;
	private BigDecimal precioVenta;
	private BigDecimal costoLista;
	private BigDecimal costoOferta;
	private BigDecimal ultimoCosto;
	private BigDecimal costoPromedio;
	private int tipoIva;
	private String codigoBarras;
	private Date fechaUltimoPrecio;
	private Date vencimientoMasCercano;
	private int stock;
	private Date fechaUltimaModificacion;
	private boolean status;
	public int getIdArticulo() {
		return idArticulo;
	}
	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public char getTipoArticulo() {
		return tipoArticulo;
	}
	public void setTipoArticulo(char tipoArticulo) {
		this.tipoArticulo = tipoArticulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	public String getClave1() {
		return clave1;
	}
	public void setClave1(String clave1) {
		this.clave1 = clave1;
	}
	public String getClave2() {
		return clave2;
	}
	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}
	public String getClave3() {
		return clave3;
	}
	public void setClave3(String clave3) {
		this.clave3 = clave3;
	}
	public boolean isEsPsicofarmaco() {
		return esPsicofarmaco;
	}
	public void setEsPsicofarmaco(boolean esPsicofarmaco) {
		this.esPsicofarmaco = esPsicofarmaco;
	}
	public boolean isEsEstupefaciente() {
		return esEstupefaciente;
	}
	public void setEsEstupefaciente(boolean esEstupefaciente) {
		this.esEstupefaciente = esEstupefaciente;
	}
	public char getCodigoVenta() {
		return codigoVenta;
	}
	public void setCodigoVenta(char codigoVenta) {
		this.codigoVenta = codigoVenta;
	}
	public char getTipoAutorizacion() {
		return tipoAutorizacion;
	}
	public void setTipoAutorizacion(char tipoAutorizacion) {
		this.tipoAutorizacion = tipoAutorizacion;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}
	public BigDecimal getCostoLista() {
		return costoLista;
	}
	public void setCostoLista(BigDecimal costoLista) {
		this.costoLista = costoLista;
	}
	public BigDecimal getCostoOferta() {
		return costoOferta;
	}
	public void setCostoOferta(BigDecimal costoOferta) {
		this.costoOferta = costoOferta;
	}
	public BigDecimal getUltimoCosto() {
		return ultimoCosto;
	}
	public void setUltimoCosto(BigDecimal ultimoCosto) {
		this.ultimoCosto = ultimoCosto;
	}
	public BigDecimal getCostoPromedio() {
		return costoPromedio;
	}
	public void setCostoPromedio(BigDecimal costoPromedio) {
		this.costoPromedio = costoPromedio;
	}
	public int getTipoIva() {
		return tipoIva;
	}
	public void setTipoIva(int tipoIva) {
		this.tipoIva = tipoIva;
	}
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	public Date getFechaUltimoPrecio() {
		return fechaUltimoPrecio;
	}
	public void setFechaUltimoPrecio(Date fechaUltimoPrecio) {
		this.fechaUltimoPrecio = fechaUltimoPrecio;
	}
	public Date getVencimientoMasCercano() {
		return vencimientoMasCercano;
	}
	public void setVencimientoMasCercano(Date vencimientoMasCercano) {
		this.vencimientoMasCercano = vencimientoMasCercano;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}