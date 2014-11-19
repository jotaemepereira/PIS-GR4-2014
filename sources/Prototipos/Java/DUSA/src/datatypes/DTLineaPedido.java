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

	public Long getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(Long idArticulo) {
		this.idArticulo = idArticulo;
	}

	public String getDescripcionArticulo() {
		return descripcionArticulo;
	}

	public void setDescripcionArticulo(String descripcionArticulo) {
		this.descripcionArticulo = descripcionArticulo;
	}

	public Long getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(Long stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public Long getStockActual() {
		return stockActual;
	}

	public void setStockActual(Long stockActual) {
		this.stockActual = stockActual;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public BigDecimal getPrecioPonderado() {
		return precioPonderado;
	}

	public void setPrecioPonderado(BigDecimal precioPonderado) {
		this.precioPonderado = precioPonderado;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public double getCantPredecidaMinimosCuadrados() {
		return cantPredecidaMinimosCuadrados;
	}

	public void setCantPredecidaMinimosCuadrados(
			double cantPredecidaMinimosCuadrados) {
		this.cantPredecidaMinimosCuadrados = cantPredecidaMinimosCuadrados;
	}

	public Long getNumeroArticulo() {
		return numeroArticulo;
	}

	public void setNumeroArticulo(Long numeroArticulo) {
		this.numeroArticulo = numeroArticulo;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
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

