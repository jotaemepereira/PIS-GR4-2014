package model;

import java.math.BigDecimal;

import controladores.Excepciones;

public class OrdenDetalle {
	private long idOrden;
	private int numeroLinea;
	private long numeroArticulo;
	private int cantidad;
	private BigDecimal precioUnitario;
	private BigDecimal descuento;
	private String descripcionOferta;
	private int indicadorDeFacturacion;
	private long productId;

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

	public long getNumeroArticulo() {
		return numeroArticulo;
	}

	public void setNumeroArticulo(long numeroArticulo) {
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

}
