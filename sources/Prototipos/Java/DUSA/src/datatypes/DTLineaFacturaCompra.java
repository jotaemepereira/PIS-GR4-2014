package datatypes;

import java.math.BigDecimal;

public class DTLineaFacturaCompra {
	private int idOrden;
    private int numeroLinea;
    private int numeroArticulo;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
    private String descripcionOferta;
    private int indicadorDeFacturacion;
    
    private BigDecimal costoUltimaCompra;
    private String descripcion;
    private int productId;
    private BigDecimal total;
    
    
	public int getIdOrden() {
		return idOrden;
	}
	public void setIdOrden(int idOrden) {
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
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getCostoUltimaCompra() {
		return costoUltimaCompra;
	}
	public void setCostoUltimaCompra(BigDecimal costoUltimaCompra) {
		this.costoUltimaCompra = costoUltimaCompra;
	}
}