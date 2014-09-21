package model;

import java.math.BigDecimal;

public class LineaVenta {
	private long ventaId;
	private long productoId;
	private BigDecimal precio;
	private int cantidad;
	private BigDecimal descuento;

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

}
