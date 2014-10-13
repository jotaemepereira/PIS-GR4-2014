package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import datatypes.DTProveedor;

public class Articulo {
	private int idArticulo;
	private int numero;
	private char tipoArticulo;
	private String descripcion;
	private String clave1;
	private String clave2;
	private String clave3;
	private boolean esPsicofarmaco;
	private boolean esEstupefaciente;
	private boolean esHeladera;
	private char codigoVenta;
	private char tipoAutorizacion;
	private BigDecimal precioUnitario;
	private BigDecimal precioVenta;
	private BigDecimal porcentajePrecioVenta;
	private BigDecimal costoLista;
	private BigDecimal costoOferta;
	private BigDecimal ultimoCosto;
	private BigDecimal costoPromedio;
	private int tipoIva;
	private String codigoBarras;
	private Date fechaUltimoPrecio;
	private Date vencimientoMasCercano;
	private int stock;
	private int stockMinimo;
	private Date fechaUltimaModificacion;
	private boolean status;
	private Map<Integer,DTProveedor> proveedores = new HashMap<Integer,DTProveedor>();
	private List<Presentacion> presentaciones;
	private List<Droga> drogas;
	private List<AccionTer> accionesTer;
	
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
	public boolean isEsHeladera() {
		return esHeladera;
	}
	public void setEsHeladera(boolean esHeladera) {
		this.esHeladera = esHeladera;
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
	public BigDecimal getPorcentajePrecioVenta() {
		return porcentajePrecioVenta;
	}
	public void setPorcentajePrecioVenta(BigDecimal porcentajePrecioVenta) {
		this.porcentajePrecioVenta = porcentajePrecioVenta;
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
	public int getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
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
	public Map<Integer, DTProveedor> getProveedores() {
		return proveedores;
	}
	public void setProveedores(Map<Integer, DTProveedor> proveedores) {
		this.proveedores = proveedores;
	}
	public List<Presentacion> getPresentaciones() {
		return presentaciones;
	}
	public void setPresentaciones(List<Presentacion> presentaciones) {
		this.presentaciones = presentaciones;
	}
	public List<Droga> getDrogas() {
		return drogas;
	}
	public void setDrogas(List<Droga> drogas) {
		this.drogas = drogas;
	}
	public List<AccionTer> getAccionesTer() {
		return accionesTer;
	}
	public void setAccionesTer(List<AccionTer> accionesTer) {
		this.accionesTer = accionesTer;
	}
	public void agregarProveedor(DTProveedor p) {
		this.proveedores.put(p.getIdProveedor(), p);		
	}
}