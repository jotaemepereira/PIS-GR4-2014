/**
 * Clase que implementa el control de la presentación del módulo compras
 * 
 */
package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import controladores.Excepciones;
import controladores.FabricaSistema;
import datatypes.DTBusquedaArticulo;
import datatypes.DTComprobanteFactura;
import datatypes.DTLineaFacturaCompra;
import datatypes.DTProveedor;
import datatypes.DTTiposDGI;

@ManagedBean
@SessionScoped
public class ComprasBean implements Serializable {

	private ISistema instanciaSistema;

	private static final long serialVersionUID = 1L;

	private Boolean disableBotones = false;
	private Boolean facturaAutomatica = false;
	private String hideTable = "hidden";
	private String selectFacturaDUSA = "hidden";
	private String selectProveedores = "hidden";

	private List<DTProveedor> proveedores;
	private int proveedorSeleccionado;

	private List<DTBusquedaArticulo> busquedaArticulos;
	private String busqueda;

	private List<DTComprobanteFactura> facturasDUSA = new ArrayList<DTComprobanteFactura>();
	private long ordenDeCompraDUSA;
	private DTComprobanteFactura factura = new DTComprobanteFactura();

	private List<DTTiposDGI> tiposDGI = new ArrayList<DTTiposDGI>();

	// getters y setters
	public Boolean getDisableBotones() {
		return disableBotones;
	}

	public void setDisableBotones(Boolean disableBotones) {
		this.disableBotones = disableBotones;
	}

	public String getHideTable() {
		return hideTable;
	}

	public void setHideTable(String hideTable) {
		this.hideTable = hideTable;
	}

	public List<DTProveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<DTProveedor> proveedores) {
		this.proveedores = proveedores;
	}

	public List<DTBusquedaArticulo> getBusquedaArticulos() {
		return busquedaArticulos;
	}

	public void setBusquedaArticulos(List<DTBusquedaArticulo> busquedaArticulos) {
		this.busquedaArticulos = busquedaArticulos;
	}

	public List<DTComprobanteFactura> getFacturasDUSA() {
		return facturasDUSA;
	}

	public void setFacturasDUSA(List<DTComprobanteFactura> facturasDUSA) {
		this.facturasDUSA = facturasDUSA;
	}

	public DTComprobanteFactura getFactura() {
		return factura;
	}

	public void setFactura(DTComprobanteFactura factura) {
		this.factura = factura;
	}

	public long getOrdenDeCompraDUSA() {
		return ordenDeCompraDUSA;
	}

	public void setOrdenDeCompraDUSA(long ordenDeCompraDUSA) {
		this.ordenDeCompraDUSA = ordenDeCompraDUSA;
	}

	public String getSelectFacturaDUSA() {
		return selectFacturaDUSA;
	}

	public void setSelectFacturaDUSA(String selectFacturaDUSA) {
		this.selectFacturaDUSA = selectFacturaDUSA;
	}

	public String getSelectProveedores() {
		return selectProveedores;
	}

	public void setSelectProveedores(String selectProveedores) {
		this.selectProveedores = selectProveedores;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public void setISistema(ISistema s) {
		this.instanciaSistema = s;

		if (this.instanciaSistema != null) {
			actualizarProveedores();
		}
	}

	public List<DTTiposDGI> getTiposDGI() {
		return tiposDGI;
	}

	public void setTiposDGI(List<DTTiposDGI> tiposDGI) {
		this.tiposDGI = tiposDGI;
	}
	

	public int getProveedorSeleccionado() {
		return proveedorSeleccionado;
	}

	public void setProveedorSeleccionado(int proveedorSeleccionado) {
		this.proveedorSeleccionado = proveedorSeleccionado;
	}

	public Boolean getFacturaAutomatica() {
		return facturaAutomatica;
	}

	public void setFacturaAutomatica(Boolean facturaAutomatica) {
		this.facturaAutomatica = facturaAutomatica;
	}

	// funciones ingresar compra
	public void ingresoManual() {
		facturaAutomatica = false;
		disableBotones = true;
		hideTable = "visible";
		selectFacturaDUSA = "hidden";
		selectProveedores = "visible";
	}

	public void facturaAutomaticaDUSA() {
		// TODO Traigo factura
		
		
		facturaAutomatica = true;
		disableBotones = true;
		hideTable = "visible";
		selectFacturaDUSA = "visible";
		selectProveedores = "hidden";
	}

	public void cancelarIngresarCompra() {
		disableBotones = false;
		hideTable = "hidden";
		selectFacturaDUSA = "hidden";
		selectProveedores = "hidden";
		factura = new DTComprobanteFactura();
	}

	public void ingresarCompra() {
		// TODO guardar la compra

		
		// Reseteo los valores por defecto
		disableBotones = false;
		hideTable = "hidden";
		selectFacturaDUSA = "hidden";
		selectProveedores = "hidden";
		factura = new DTComprobanteFactura();
	}

	public void actualizarProveedores() {
		Map<Integer, DTProveedor> proveedoresLista;
		try {
			proveedoresLista = this.instanciaSistema.obtenerProveedores();
			this.proveedores = new ArrayList<DTProveedor>(
					proveedoresLista.values());
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void agregarArticulo(DTBusquedaArticulo articulo) {
		DTLineaFacturaCompra linea = new DTLineaFacturaCompra();

		linea.setDescripcion(articulo.getDescripcion());
		linea.setNumeroArticulo(articulo.getNumeroProducto_proveedor());
		linea.setProductId(articulo.getIdArticulo());
		linea.setCantidad(1);
		linea.setCostoUltimaCompra(articulo.getCostoReal());
		linea.setDescripcionOferta("");
		linea.setDescuento(new BigDecimal(0));
		linea.setTotal(new BigDecimal(0));
		linea.setPrecioUnitario(new BigDecimal(0));

		List<DTLineaFacturaCompra> detalle = factura.getDetalle();
		detalle.add(linea);

	}

	public void buscarArticulos() {
		busquedaArticulos = new ArrayList<DTBusquedaArticulo>();

		if (busqueda.equals("")) {
			return;
		}

		try {
			if(factura.getIdProveedor() != 0) {
				busquedaArticulos = this.instanciaSistema.buscarArticulos(busqueda, this.proveedorSeleccionado);
			}else{
				busquedaArticulos = this.instanciaSistema.buscarArticulos(busqueda);
			}
			System.out.println("CANTIDAD ENCONTRADA: "
					+ busquedaArticulos.size());
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * frente a un cambio en el precio, cantidad o descuento, calcula el total
	 * de ese articulo
	 */
	public void calcularTotalArticulo(DTLineaFacturaCompra detalle) {
		BigDecimal precio = detalle.getPrecioUnitario();
		BigDecimal descuento = detalle.getDescuento()
				.subtract(new BigDecimal(100)).abs()
				.divide(new BigDecimal(100));
		BigDecimal cantidad = new BigDecimal(detalle.getCantidad());

		detalle.setTotal(precio.multiply(cantidad).multiply(descuento));
	}

	/**
	 * frente a un cambio en los montos de iva o retenciones, recalcula el total
	 * de cada iva y el total
	 */
	public void actualizarDatos() {

		factura.setTotalIvaBasico(factura.getMontoNetoGravadoIvaBasico().add(
				factura.getMontoTributoIvaBasico()));
		factura.setTotalIvaMinimo(factura.getMontoNetoGravadoIvaMinimo().add(
				factura.getMontoTributoIvaMinimo()));

		factura.setMontoTotalAPagar(factura.getTotalIvaBasico()
				.add(factura.getTotalIvaMinimo())
				.add(factura.getMontoRetenidoIRAE())
				.add(factura.getMontoRetenidoIVA())
				.add(factura.getMontoNoGravado())
				.add(factura.getMontoNoFacturable()));

	}
	
	public void saveProveedor(){

		this.proveedorSeleccionado = factura.getIdProveedor();

	}

}
