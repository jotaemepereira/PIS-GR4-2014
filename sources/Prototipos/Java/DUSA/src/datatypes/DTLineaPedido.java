package datatypes;

import java.math.BigDecimal;

public class DTLineaPedido {
	private Long idArticulo;
	private String descripcionArticulo;
	private Long stockMinimo;
	private Long stockActual;
	private BigDecimal precioUnitario;
	private BigDecimal precioPonderado;
	private long cantidad;
	private double cantPredecidaMinimosCuadrados;
	private Long numeroArticulo;
	private BigDecimal subtotal;
	private BigDecimal promedioVendidoAnosAnt;
	private BigDecimal prediccionVentasAnt;
	
	public DTLineaPedido() {
		
		this.numeroArticulo = new Long(0);
		this.idArticulo = new Long(0);
		this.cantidad = 0;
		this.descripcionArticulo = "";
		this.precioUnitario = new BigDecimal(0);
		this.precioPonderado = new BigDecimal(0);
		this.stockMinimo = new Long(0);
		this.subtotal = new BigDecimal(0);
	}
	
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
	public long getCantidad() {
		return cantidad;
	}
	public void setCantidad(long cantidad) {
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
	public void setStockMinimo(long stockMinimo) {
		this.stockMinimo = new Long(stockMinimo);
	}
	public Long getStockActual() {
		return stockActual;
	}
	public void setStockActual(Long stockActual) {
		this.stockActual = stockActual;
	}
	public void setStockActual(int stockActual) {
		this.stockActual = new Long(stockMinimo);
	}
	public void setStockActual(long stockActual) {
		this.stockActual = new Long(stockMinimo);
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

	public double getCantPredecidaMinimosCuadrados() {
		return cantPredecidaMinimosCuadrados;
	}

	public void setCantPredecidaMinimosCuadrados(
			double cantPredecidaMinimosCuadrados) {
		this.cantPredecidaMinimosCuadrados = cantPredecidaMinimosCuadrados;
	}

	public BigDecimal getPromedioVendidoAnosAnt() {
		return promedioVendidoAnosAnt;
	}

	public void setPromedioVendidoAnosAnt(BigDecimal promedioVendidoAnosAnt) {
		this.promedioVendidoAnosAnt = promedioVendidoAnosAnt;
	}

	public BigDecimal getPrediccionVentasAnt() {
		return prediccionVentasAnt;
	}

	public void setPrediccionVentasAnt(BigDecimal prediccionVentasAnt) {
		this.prediccionVentasAnt = prediccionVentasAnt;
	}
}

