package datatypes;

import java.io.Serializable;
import java.math.BigDecimal;

import model.Articulo;

public class DTVenta implements Serializable {
	private int productId;
	private String presentacion;
	private String principioActivo;
	private String descripcion;
	private String laboratorio;
	private String concentracion;
	private String nombre;
	private String barcode;
	private BigDecimal precioVenta = new BigDecimal(0);
	private int cantidad = 1;
	private boolean recetaBlanca;
	private boolean recetaVerde;
	private boolean recetaNaranja;
	private boolean refrigerado;
	private BigDecimal descuento = new BigDecimal(0);
	private String CodigoBarras;
	private int stock;
	private String descuentoPrecio;
	private String descuentoReceta;
	
	private BigDecimal irae;
	private BigDecimal iva;
	private int indicadorFacturacion;
	

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getBarcode() {
		return barcode;
	}

	public boolean isRecetaBlanca() {
		return recetaBlanca;
	}

	public void setRecetaBlanca(boolean recetaBlanca) {
		this.recetaBlanca = recetaBlanca;
	}

	public boolean isRecetaVerde() {
		return recetaVerde;
	}

	public void setRecetaVerde(boolean recetaVerde) {
		this.recetaVerde = recetaVerde;
	}

	public boolean isRecetaNaranja() {
		return recetaNaranja;
	}

	public void setRecetaNaranja(boolean recetaNaranja) {
		this.recetaNaranja = recetaNaranja;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public String getPrincipioActivo() {
		return principioActivo;
	}

	public void setPrincipioActivo(String principioActivo) {
		this.principioActivo = principioActivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getCodigoBarras() {
		return CodigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		CodigoBarras = codigoBarras;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getConcentracion() {
		return concentracion;
	}

	public void setConcentracion(String concentracion) {
		this.concentracion = concentracion;
	}

	public boolean isRefrigerado() {
		return refrigerado;
	}

	public void setRefrigerado(boolean refrigerado) {
		this.refrigerado = refrigerado;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getDescuentoPrecio() {
		return descuentoPrecio;
	}

	public void setDescuentoPrecio(String descuentoPrecio) {
		this.descuentoPrecio = descuentoPrecio;
	}

	public String getDescuentoReceta() {
		return descuentoReceta;
	}

	public void setDescuentoReceta(String descuentoReceta) {
		this.descuentoReceta = descuentoReceta;
	}

	public BigDecimal getIrae() {
		return irae;
	}

	public void setIrae(BigDecimal irae) {
		this.irae = irae;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public int getIndicadorFacturacion() {
		return indicadorFacturacion;
	}

	public void setIndicadorFacturacion(int indicadorFacturacion) {
		this.indicadorFacturacion = indicadorFacturacion;
	}
	
}
