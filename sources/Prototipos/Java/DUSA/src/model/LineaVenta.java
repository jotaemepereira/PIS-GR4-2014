package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class LineaVenta implements Serializable {
	private long ventaId;
	private long productoId;
	private Articulo articulo;
	private BigDecimal precio;
	private int cantidad;
	private BigDecimal descuento;
	private int linea;
	private boolean recetaBlanca;
	private boolean recetaNaranja;
	private boolean recetaVerde;
	private String descuentoPrecio;
	private String descuentoReceta;
	private String descripcionOferta;
	
	private BigDecimal irae;
	private BigDecimal iva;
	private int indicadorFacturacion;

	public long getVentaId() {
		return ventaId;
	}

	public void setVentaId(long ventaId) {
		this.ventaId = ventaId;
	}

	public long getProductoId() {
		return productoId;
	}

	public void setProductoId(long productoId) {
		this.productoId = productoId;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
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

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public boolean isRecetaBlanca() {
		return recetaBlanca;
	}

	public void setRecetaBlanca(boolean recetaBlanca) {
		this.recetaBlanca = recetaBlanca;
	}

	public boolean isRecetaNaranja() {
		return recetaNaranja;
	}

	public void setRecetaNaranja(boolean recetaNaranja) {
		this.recetaNaranja = recetaNaranja;
	}

	public boolean isRecetaVerde() {
		return recetaVerde;
	}

	public void setRecetaVerde(boolean recetaVerde) {
		this.recetaVerde = recetaVerde;
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

	public String getDescripcionOferta() {
		return descripcionOferta;
	}

	public void setDescripcionOferta(String descripcionOferta) {
		this.descripcionOferta = descripcionOferta;
	}

}
