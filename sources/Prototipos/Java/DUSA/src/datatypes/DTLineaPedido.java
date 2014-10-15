package datatypes;

import java.math.BigDecimal;

public class DTLineaPedido {
	private Long numeroArticulo;
	private Long idArticulo;
	private int cantidad;
	private String descripcionArticulo;
	private BigDecimal precioUnitario;
	private BigDecimal precioPonderado;
	private Long stockMinimo;
	private BigDecimal subtotal;
	
	public Long getNumeroArticulo() {
		return numeroArticulo;
	}
	public void setNumeroArticulo(Long numeroArticulo) {
		this.numeroArticulo = numeroArticulo;
	}
	public void setNumeroArticulo(int numeroArticulo) {
		this.numeroArticulo = new Long(numeroArticulo);
	}
	public Long getIdArticulo() {
		return idArticulo;
	}
	public void setIdArticulo(int idArticulo) {
		this.idArticulo = new Long(idArticulo);
	}
	public void setIdArticulo(Long idArticulo) {
		this.idArticulo = idArticulo;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripcionArticulo() {
		return descripcionArticulo;
	}
	public void setDescripcionArticulo(String descripcionArticulo) {
		this.descripcionArticulo = descripcionArticulo;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public void setPrecioUnitario(int precioUnitario) {
		this.precioUnitario = new BigDecimal(precioUnitario);
	}
	public void setPrecioUnitario(long precioUnitario) {
		this.precioUnitario = new BigDecimal(precioUnitario);
	}
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = new BigDecimal(precioUnitario);
	}
	public BigDecimal getPrecioPonderado() {
		return precioPonderado;
	}
	public void setPrecioPonderado(BigDecimal precioPonderado) {
		this.precioPonderado = precioPonderado;
	}
	public void setPrecioPonderado(int precioPonderado) {
		this.precioPonderado = new BigDecimal(precioPonderado);
	}
	public void setPrecioPonderado(long precioPonderado) {
		this.precioPonderado = new BigDecimal(precioPonderado);
	}
	public void setPrecioPonderado(double precioPonderado) {
		this.precioPonderado = new BigDecimal(precioPonderado);
	}
	public Long getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(Long stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = new Long(stockMinimo);
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public void setSubtotal(int subtotal) {
		this.subtotal = new BigDecimal(subtotal);
	}
	public void setSubtotal(long subtotal) {
		this.subtotal = new BigDecimal(subtotal);
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = new BigDecimal(subtotal);
	}
}

