package datatypes;

public class DTBusquedaArticulo {
	private int idArticulo;
	private String descripcion;
	private String proveedores;
	private String presentaciones;
	private String droga;
	private String accionesTerapeuticas;
	private String codigoBarras;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getProveedores() {
		return proveedores;
	}
	public void setProveedores(String proveedores) {
		this.proveedores = proveedores;
	}
	public String getDroga() {
		return droga;
	}
	public void setDroga(String droga) {
		this.droga = droga;
	}
	public int getIdArticulo() {
		return idArticulo;
	}
	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}
	public String getPresentaciones() {
		return presentaciones;
	}
	public void setPresentaciones(String presentaciones) {
		this.presentaciones = presentaciones;
	}
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	public String getAccionesTerapeuticas() {
		return accionesTerapeuticas;
	}
	public void setAccionesTerapeuticas(String accionesTerapeuticas) {
		this.accionesTerapeuticas = accionesTerapeuticas;
	}

}
