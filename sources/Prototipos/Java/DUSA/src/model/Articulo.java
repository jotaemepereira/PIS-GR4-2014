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
	private long idArticulo;
	private int idMarca;
	private boolean idMarcaModificado;
	private int numero;
	private char tipoArticulo;
	private boolean tipoArticuloModificado;
	private String descripcion;
	private boolean descripcionModificado;
	private String clave1;
	private boolean clave1Modificado;
	private String clave2;
	private boolean clave2Modificado;
	private String clave3;
	private boolean clave3Modificado;
	private boolean esPsicofarmaco;
	private boolean esPsicofarmacoModificado;
	private boolean esEstupefaciente;
	private boolean esEstupefacienteModificado;
	private boolean esHeladera;
	private boolean esHeladeraModificado;
	private char codigoVenta;
	private boolean codigoVentaModificado;
	private char tipoAutorizacion;
	private boolean tipoAutorizacionModificado;
	private BigDecimal precioUnitario;
	private boolean precioUnitarioModificado;
	private BigDecimal precioVenta;
	private boolean precioVentaModificado;
	private BigDecimal porcentajePrecioVenta;
	private boolean porcentajePrecioVentaModificado;
	private BigDecimal costoLista;
	private boolean costoListaModificado;
	private BigDecimal costoOferta;
	private boolean costoOfertaModificado;
	private BigDecimal ultimoCosto;
	private boolean ultimoCostoModificado;
	private BigDecimal costoPromedio;
	private boolean costoPromedioModificado;
	private TipoIva tipoIva;
	private boolean tipoIvaModificado;
	private String codigoBarras;
	private boolean codigoBarrasModificado;
	private Date fechaUltimoPrecio;
	private Date vencimientoMasCercano;
	private boolean vencimientoMasCercanoModificado;
	private long stock;
	private long stockMinimo;
	private boolean stockMinimoModificado;
	private Usuario usuario;
	private boolean usuarioModificado;
	private Date fechaUltimaModificacion;
	private boolean status;
	private Map<Integer,DTProveedor> proveedores = new HashMap<Integer,DTProveedor>();
	private boolean proveedoresModificado;
	private String presentacion;
	private boolean presentacionModificado;
	private long[] drogas;
	private boolean drogasModificado;
	private long[] accionesTer;
	private boolean accionesTerModificado;
	
	public Articulo(Articulo articulo) {
		this.accionesTer = articulo.accionesTer;
		this.clave1 = articulo.clave1;
		this.clave2 = articulo.clave2;
		this.clave3 = articulo.clave3;
		this.codigoBarras = articulo.codigoBarras;
		this.codigoVenta = articulo.codigoVenta;
		this.costoLista = articulo.costoLista;
		this.costoOferta = articulo.costoOferta;
		this.costoPromedio = articulo.costoPromedio;
		this.descripcion = articulo.descripcion;
		this.drogas = articulo.drogas;
		this.esEstupefaciente = articulo.esEstupefaciente;
		this.esHeladera = articulo.esHeladera;
		this.esPsicofarmaco = articulo.esPsicofarmaco;
		this.fechaUltimaModificacion = articulo.fechaUltimaModificacion;
		this.fechaUltimoPrecio = articulo.fechaUltimoPrecio;
		this.idArticulo = articulo.idArticulo;
		this.idMarca = articulo.idMarca;
		this.numero = articulo.numero;
		this.porcentajePrecioVenta = articulo.porcentajePrecioVenta;
		this.precioUnitario = articulo.precioUnitario;
		this.precioVenta = articulo.precioVenta;
		this.presentacion = articulo.presentacion;
		this.proveedores = new HashMap<Integer, DTProveedor>(articulo.proveedores);
		this.status = articulo.status;
		this.stock = articulo.stock;
		this.stockMinimo = articulo.stockMinimo;
		this.tipoArticulo = articulo.tipoArticulo;
		this.tipoAutorizacion = articulo.tipoAutorizacion;
		this.tipoIva = new TipoIva();
		this.tipoIva.setTipoIVA(articulo.tipoIva.getTipoIVA());
		this.ultimoCosto = articulo.ultimoCosto;
		this.usuario = new Usuario();
		this.usuario.setNombre(articulo.getUsuario().getNombre());
		this.vencimientoMasCercano = articulo.vencimientoMasCercano;
	}
	public Articulo() {

	}
	public long getIdArticulo() {
		return idArticulo;
	}
	public void setIdArticulo(long idArticulo) {
		this.idArticulo = idArticulo;
	}
	public int getIdMarca() {
		return idMarca;
	}
	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
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
	public TipoIva getTipoIva() {
		return tipoIva;
	}
	public void setTipoIva(TipoIva tipoIva) {
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
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public void setStock(long stock) {
		this.stock = new Long(stock);
	}
	public Long getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(Long stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
	public void setStockMinimo(long stockMinimo) {
		this.stockMinimo = new Long(stockMinimo);
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	public String getPresentacion() {
		return presentacion;
	}
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}
	public long[] getDrogas() {
		return drogas;
	}
	public void setDrogas(long[] drogas) {
		this.drogas = drogas;
	}
	public long[] getAccionesTer() {
		return accionesTer;
	}
	public void setAccionesTer(long[] accionesTer) {
		this.accionesTer = accionesTer;
	}
	public boolean isIdMarcaModificado() {
		return idMarcaModificado;
	}
	public void setIdMarcaModificado(boolean idMarcaModificado) {
		this.idMarcaModificado = idMarcaModificado;
	}
	public boolean isTipoArticuloModificado() {
		return tipoArticuloModificado;
	}
	public void setTipoArticuloModificado(boolean tipoArticuloModificado) {
		this.tipoArticuloModificado = tipoArticuloModificado;
	}
	public boolean isDescripcionModificado() {
		return descripcionModificado;
	}
	public void setDescripcionModificado(boolean descripcionModificado) {
		this.descripcionModificado = descripcionModificado;
	}
	public boolean isClave1Modificado() {
		return clave1Modificado;
	}
	public void setClave1Modificado(boolean clave1Modificado) {
		this.clave1Modificado = clave1Modificado;
	}
	public boolean isClave2Modificado() {
		return clave2Modificado;
	}
	public void setClave2Modificado(boolean clave2Modificado) {
		this.clave2Modificado = clave2Modificado;
	}
	public boolean isClave3Modificado() {
		return clave3Modificado;
	}
	public void setClave3Modificado(boolean clave3Modificado) {
		this.clave3Modificado = clave3Modificado;
	}
	public boolean isEsPsicofarmacoModificado() {
		return esPsicofarmacoModificado;
	}
	public void setEsPsicofarmacoModificado(boolean esPsicofarmacoModificado) {
		this.esPsicofarmacoModificado = esPsicofarmacoModificado;
	}
	public boolean isEsEstupefacienteModificado() {
		return esEstupefacienteModificado;
	}
	public void setEsEstupefacienteModificado(boolean esEstupefacienteModificado) {
		this.esEstupefacienteModificado = esEstupefacienteModificado;
	}
	public boolean isEsHeladeraModificado() {
		return esHeladeraModificado;
	}
	public void setEsHeladeraModificado(boolean esHeladeraModificado) {
		this.esHeladeraModificado = esHeladeraModificado;
	}
	public boolean isCodigoVentaModificado() {
		return codigoVentaModificado;
	}
	public void setCodigoVentaModificado(boolean codigoVentaModificado) {
		this.codigoVentaModificado = codigoVentaModificado;
	}
	public boolean isTipoAutorizacionModificado() {
		return tipoAutorizacionModificado;
	}
	public void setTipoAutorizacionModificado(boolean tipoAutorizacionModificado) {
		this.tipoAutorizacionModificado = tipoAutorizacionModificado;
	}
	public boolean isPrecioUnitarioModificado() {
		return precioUnitarioModificado;
	}
	public void setPrecioUnitarioModificado(boolean precioUnitarioModificado) {
		this.precioUnitarioModificado = precioUnitarioModificado;
	}
	public boolean isPrecioVentaModificado() {
		return precioVentaModificado;
	}
	public void setPrecioVentaModificado(boolean precioVentaModificado) {
		this.precioVentaModificado = precioVentaModificado;
	}
	public boolean isPorcentajePrecioVentaModificado() {
		return porcentajePrecioVentaModificado;
	}
	public void setPorcentajePrecioVentaModificado(
			boolean porcentajePrecioVentaModificado) {
		this.porcentajePrecioVentaModificado = porcentajePrecioVentaModificado;
	}
	public boolean isCostoListaModificado() {
		return costoListaModificado;
	}
	public void setCostoListaModificado(boolean costoListaModificado) {
		this.costoListaModificado = costoListaModificado;
	}
	public boolean isCostoOfertaModificado() {
		return costoOfertaModificado;
	}
	public void setCostoOfertaModificado(boolean costoOfertaModificado) {
		this.costoOfertaModificado = costoOfertaModificado;
	}
	public boolean isUltimoCostoModificado() {
		return ultimoCostoModificado;
	}
	public void setUltimoCostoModificado(boolean ultimoCostoModificado) {
		this.ultimoCostoModificado = ultimoCostoModificado;
	}
	public boolean isCostoPromedioModificado() {
		return costoPromedioModificado;
	}
	public void setCostoPromedioModificado(boolean costoPromedioModificado) {
		this.costoPromedioModificado = costoPromedioModificado;
	}
	public boolean isTipoIvaModificado() {
		return tipoIvaModificado;
	}
	public void setTipoIvaModificado(boolean tipoIvaModificado) {
		this.tipoIvaModificado = tipoIvaModificado;
	}
	public boolean isCodigoBarrasModificado() {
		return codigoBarrasModificado;
	}
	public void setCodigoBarrasModificado(boolean codigoBarrasModificado) {
		this.codigoBarrasModificado = codigoBarrasModificado;
	}
	public boolean isVencimientoMasCercanoModificado() {
		return vencimientoMasCercanoModificado;
	}
	public void setVencimientoMasCercanoModificado(
			boolean vencimientoMasCercanoModificado) {
		this.vencimientoMasCercanoModificado = vencimientoMasCercanoModificado;
	}
	public boolean isStockMinimoModificado() {
		return stockMinimoModificado;
	}
	public void setStockMinimoModificado(boolean stockMinimoModificado) {
		this.stockMinimoModificado = stockMinimoModificado;
	}
	public boolean isUsuarioModificado() {
		return usuarioModificado;
	}
	public void setUsuarioModificado(boolean usuarioModificado) {
		this.usuarioModificado = usuarioModificado;
	}
	public boolean isProveedoresModificado() {
		return proveedoresModificado;
	}
	public void setProveedoresModificado(boolean proveedoresModificado) {
		this.proveedoresModificado = proveedoresModificado;
	}
	public boolean isPresentacionModificado() {
		return presentacionModificado;
	}
	public void setPresentacionModificado(boolean presentacionModificado) {
		this.presentacionModificado = presentacionModificado;
	}
	public boolean isDrogasModificado() {
		return drogasModificado;
	}
	public void setDrogasModificado(boolean drogasModificado) {
		this.drogasModificado = drogasModificado;
	}
	public boolean isAccionesTerModificado() {
		return accionesTerModificado;
	}
	public void setAccionesTerModificado(boolean accionesTerModificado) {
		this.accionesTerModificado = accionesTerModificado;
	}
	public void agregarProveedor(DTProveedor p) {
		this.proveedores.put(p.getIdProveedor(), p);		
	}
}