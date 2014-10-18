package datatypes;

public class DTBusquedaArticulo {
	private int idArticulo;
	private String descripcion;
	private String marca;
	private String presentacion;
	private String droga;
	private String accionesTerapeuticas;
	private String codigoBarras;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getPresentacion() {
		return presentacion;
	}
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

}
