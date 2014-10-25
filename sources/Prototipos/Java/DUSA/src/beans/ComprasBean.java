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
	
	private List<DTProveedor> busquedaProveedores;
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

	public List<DTProveedor> getBusquedaProveedores() {
		return busquedaProveedores;
	}

	public void setBusquedaProveedores(List<DTProveedor> busquedaProveedores) {
		this.busquedaProveedores = busquedaProveedores;
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
	
	// funciones ingresar compra
	
	public void ingresoManual(){
		disableBotones = true;
		hideTable = "visible";
		selectFacturaDUSA = "hidden";
	}
	
	public void facturaAutomaticaDUSA(){
		disableBotones = true;
		hideTable = "visible";
		selectFacturaDUSA = "visible";
	}
	
	public void cancelarIngresarCompra(){
		disableBotones = false;
		hideTable = "hidden";
		selectFacturaDUSA = "hidden";
	}

	public void ingresarCompra(){
		
		
		// Reseteo los valores por defecto
		disableBotones = false;
		hideTable = "hidden";
		selectFacturaDUSA = "hidden";
	}


}
