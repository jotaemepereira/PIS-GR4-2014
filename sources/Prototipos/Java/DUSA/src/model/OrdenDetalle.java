package model;

import java.math.BigDecimal;

import controladores.Excepciones;

public class OrdenDetalle {
	private long idOrden;
	private int numeroLinea;
	private int numeroArticulo;
	private int cantidad;
	private BigDecimal precioUnitario;
	private BigDecimal descuento;
	private String descripcionOferta;
	private int indicadorDeFacturacion;
	private long productId;
	
	private BigDecimal avg_cost;
	private long stock;
	private TipoIva tipoIVA;
	
	public OrdenDetalle() {}

	public long getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(long idOrden) {
		this.idOrden = idOrden;
	}

	public int getNumeroLinea() {
		return numeroLinea;
	}

	public void setNumeroLinea(int numeroLinea) throws Excepciones {
		if (numeroLinea == 0) {
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		this.numeroLinea = numeroLinea;
	}

	public int getNumeroArticulo() {
		return numeroArticulo;
	}

	public void setNumeroArticulo(int numeroArticulo) {
		this.numeroArticulo = numeroArticulo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) throws Excepciones {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) throws Excepciones {
		if ((precioUnitario == null) || (precioUnitario == (new BigDecimal(0)))) {
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_DATOS,
					Excepciones.ERROR_DATOS));
		}
		this.precioUnitario = precioUnitario;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		if(descuento == null){
			descuento = new BigDecimal(0);
		}
		this.descuento = descuento;
	}

	public String getDescripcionOferta() {
		return descripcionOferta;
	}

	public void setDescripcionOferta(String descripcionOferta) {
		this.descripcionOferta = descripcionOferta;
	}

	public int getIndicadorDeFacturacion() {
		return indicadorDeFacturacion;
	}

	public void setIndicadorDeFacturacion(int indicadorDeFacturacion) {
		this.indicadorDeFacturacion = indicadorDeFacturacion;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) throws Excepciones {
		if(productId == 0){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		this.productId = productId;
	}

	public BigDecimal getAvg_cost() {
		return avg_cost;
	}

	public void setAvg_cost(BigDecimal avg_cost) {
		this.avg_cost = (avg_cost != null) ? avg_cost : new BigDecimal(0);
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public TipoIva getTipoIVA() {
		return tipoIVA;
	}

	public void setTipoIVA(TipoIva tipoIva) {
		this.tipoIVA = tipoIva;
	}

}
