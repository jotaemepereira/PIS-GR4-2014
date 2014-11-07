package datatypes;

import java.util.List;

import model.Articulo;

public class DTModificacionArticulo {
	private Articulo articulo;
	private List<DTProveedor> proveedoresNuevos;
	private List<DTProveedor> proveedoresConCambios;
	private List<DTProveedor> proveedoresABorrar;
	private long[] drogasNuevas;
	private long[] drogasABorrar;
	private long[] accionesTerNuevas;
	private long[] accionesTerABorrar;
	
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
	public long[] getDrogasNuevas() {
		return drogasNuevas;
	}
	public void setDrogasNuevas(long[] drogasNuevas) {
		this.drogasNuevas = drogasNuevas;
	}
	public long[] getDrogasABorrar() {
		return drogasABorrar;
	}
	public void setDrogasABorrar(long[] drogasABorrar) {
		this.drogasABorrar = drogasABorrar;
	}
	public long[] getAccionesTerNuevas() {
		return accionesTerNuevas;
	}
	public void setAccionesTerNuevas(long[] accionesTerNuevas) {
		this.accionesTerNuevas = accionesTerNuevas;
	}
	public long[] getAccionesTerABorrar() {
		return accionesTerABorrar;
	}
	public void setAccionesTerABorrar(long[] accionesTerABorrar) {
		this.accionesTerABorrar = accionesTerABorrar;
	}
}
