package datatypes;

import java.util.ArrayList;
import java.util.List;

import model.Articulo;

public class DTModificacionArticulo {
	private Articulo articulo;
	private List<DTProveedor> proveedoresNuevos;
	private List<DTProveedor> proveedoresConCambios;
	private List<DTProveedor> proveedoresABorrar;
	private List<Long> drogasNuevas;
	private List<Long> drogasABorrar;
	private List<Long> accionesTerNuevas;
	private List<Long> accionesTerABorrar;
	
	public DTModificacionArticulo(){
		this.proveedoresNuevos = new ArrayList<DTProveedor>();
		this.proveedoresConCambios = new ArrayList<DTProveedor>();
		this.proveedoresABorrar = new ArrayList<DTProveedor>();
		this.drogasNuevas = new ArrayList<Long>();
		this.drogasABorrar = new ArrayList<Long>();
		this.accionesTerNuevas = new ArrayList<Long>();
		this.accionesTerABorrar = new ArrayList<Long>();
	}
	
	public Articulo getArticulo() {
		return articulo;
	}
	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
	public List<DTProveedor> getProveedoresNuevos() {
		return proveedoresNuevos;
	}
	public void setProveedoresNuevos(List<DTProveedor> proveedoresNuevos) {
		this.proveedoresNuevos = proveedoresNuevos;
	}
	public List<DTProveedor> getProveedoresConCambios() {
		return proveedoresConCambios;
	}
	public void setProveedoresConCambios(List<DTProveedor> proveedoresConCambios) {
		this.proveedoresConCambios = proveedoresConCambios;
	}
	public List<DTProveedor> getProveedoresABorrar() {
		return proveedoresABorrar;
	}
	public void setProveedoresABorrar(List<DTProveedor> proveedoresABorrar) {
		this.proveedoresABorrar = proveedoresABorrar;
	}
	public List<Long> getDrogasNuevas() {
		return drogasNuevas;
	}
	public void setDrogasNuevas(List<Long> drogasNuevas) {
		this.drogasNuevas = drogasNuevas;
	}
	public List<Long> getDrogasABorrar() {
		return drogasABorrar;
	}
	public void setDrogasABorrar(List<Long> drogasABorrar) {
		this.drogasABorrar = drogasABorrar;
	}
	public List<Long> getAccionesTerNuevas() {
		return accionesTerNuevas;
	}
	public void setAccionesTerNuevas(List<Long> accionesTerNuevas) {
		this.accionesTerNuevas = accionesTerNuevas;
	}
	public List<Long> getAccionesTerABorrar() {
		return accionesTerABorrar;
	}
	public void setAccionesTerABorrar(List<Long> accionesTerABorrar) {
		this.accionesTerABorrar = accionesTerABorrar;
	}
}
