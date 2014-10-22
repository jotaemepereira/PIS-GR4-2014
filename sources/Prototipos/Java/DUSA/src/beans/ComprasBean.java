/**
 * Clase que implementa el control de la presentación del módulo compras
 * 
 */
package beans;

import java.io.Serializable;
import java.util.List;

import datatypes.DTBusquedaArticulo;
import datatypes.DTProveedor;

public class ComprasBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean disableBotones = false;
	private String hideTable = "hidden";
	
	private List<DTProveedor> busquedaProveedores;
	private List<DTBusquedaArticulo> busquedaArticulos;
	
	
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
	
	
	
	// funciones ingresar compra
	
	public void ingresoManual(){
		disableBotones = true;
		hideTable = "visible";
	}
	
	public void facturaAutomáticaDUSA(){
		disableBotones = true;
		hideTable = "visible";
	}
	
	public void cancelarIngresarCompra(){
		disableBotones = false;
		hideTable = "hidden";
	}

	public void ingresarCompra(){
		
		
		// Reseteo los valores por defecto
		disableBotones = false;
		hideTable = "hidden";
	}
	
	

}
