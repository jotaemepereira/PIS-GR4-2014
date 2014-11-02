package datatypes;

import java.math.BigDecimal;

import controladores.Excepciones;
import controladores.FabricaPersistencia;
import uy.com.dusa.ws.DataLineaComprobante;

public class DTLineaFacturaCompra {
	private long idOrden;
    private int numeroLinea;
    private long numeroArticulo;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
    private String descripcionOferta;
    private int indicadorDeFacturacion;
    
    private BigDecimal costoUltimaCompra;
    private String descripcion;
    private long productId;
    private BigDecimal total;
    
    public DTLineaFacturaCompra(){}
    
    public DTLineaFacturaCompra(DataLineaComprobante linea){
    	this.numeroLinea = linea.getNumeroLinea();
    	this.numeroArticulo = linea.getNumeroArticulo();
    	this.cantidad = linea.getCantidad();
    	this.precioUnitario = linea.getPrecioUnitario();
    	this.descuento = linea.getDescuento();
    	this.descripcionOferta = linea.getDescripcionOferta();
    	this.indicadorDeFacturacion = linea.getIndicadorDeFacturacion();
    	
    	try {
			FabricaPersistencia.getInstanciaComprasPersistencia().getDatosArticuloLinea(this);
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
		}
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
	public long getNumeroArticulo() {
		return numeroArticulo;
	}
	public void setNumeroArticulo(long numeroArticulo) {
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
	public BigDecimal getCostoUltimaCompra() {
		return costoUltimaCompra;
	}
	public void setCostoUltimaCompra(BigDecimal costoUltimaCompra) {
		this.costoUltimaCompra = costoUltimaCompra;
	}
}
