package datatypes;

import java.math.BigDecimal;

import controladores.Excepciones;
import controladores.FabricaPersistencia;
import uy.com.dusa.ws.DataLineaComprobante;

public class DTLineaFacturaCompra {
	private long idOrden;
	private int numeroLinea;
	private int numeroArticulo;
	private int cantidad;
	private BigDecimal precioUnitario;
	private BigDecimal descuento;
	private String descripcionOferta;
	private int indicadorDeFacturacion;

	private BigDecimal costoListaArticulo;
	private String descripcion;
	private long productId;
	private BigDecimal total;

	public DTLineaFacturaCompra() {
	}

	public long getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(long idOrden) {
		this.idOrden = idOrden;
	}

	public int getNumeroLinea() {
		return numeroLinea;
	}

	public void setNumeroLinea(int numeroLinea) {
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

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getCostoListaArticulo() {
		return costoListaArticulo;
	}

	public void setCostoListaArticulo(BigDecimal costoListaArticulo) {
		this.costoListaArticulo = costoListaArticulo;
	}
}
