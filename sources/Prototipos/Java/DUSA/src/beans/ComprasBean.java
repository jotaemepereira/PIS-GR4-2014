/**
 * Clase que implementa el control de la presentación del módulo compras
 * 
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import datatypes.DTBusquedaArticulo;
import datatypes.DTComprobanteFactura;
import datatypes.DTProveedor;

@ManagedBean
@SessionScoped
public class ComprasBean implements Serializable {
	private static final long serialVersionUID = 3L;

	private Boolean disableBotones = false;
	private String hideTable = "hidden";
	private String selectFacturaDUSA = "hidden";
	private String selectProveedores = "hidden";
	
	private List<DTProveedor> proveedores;
	private String selectedProveedor;
	private List<DTBusquedaArticulo> busquedaArticulos;
	private List<DTComprobanteFactura> facturasDUSA = new ArrayList<DTComprobanteFactura>();
	private long ordenDeCompraDUSA;
	private DTComprobanteFactura factura = new DTComprobanteFactura();
	
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

	public String getSelectedProveedor() {
		return selectedProveedor;
	}

	public void setSelectedProveedor(String selectedProveedor) {
		this.selectedProveedor = selectedProveedor;
	}
	
	// funciones ingresar compra
	
	public void ingresoManual(){
		disableBotones = true;
		hideTable = "visible";
		selectFacturaDUSA = "hidden";
		selectProveedores = "visible";
	}
	
	public void facturaAutomaticaDUSA(){
		disableBotones = true;
		hideTable = "visible";
		selectFacturaDUSA = "visible";
		selectProveedores = "hidden";
	}
	
	public void cancelarIngresarCompra(){
		disableBotones = false;
		hideTable = "hidden";
		selectFacturaDUSA = "hidden";
		selectProveedores = "hidden";
	}

	public void ingresarCompra(){
		// TODO guardar la compra
		
		// Reseteo los valores por defecto
		disableBotones = false;
		hideTable = "hidden";
		selectFacturaDUSA = "hidden";
		selectProveedores = "hidden";
	}
	
	public void actualizarProveedores(){
		// TODO traer proveedores
	}

	public void agregarArticulo(){
		// TODO  agregar articulo a la lista
	}

}
