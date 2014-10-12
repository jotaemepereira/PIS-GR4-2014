package model;

public class Presentacion {
	private long idPresentacion;
	private String descripcion;
	private boolean status;
	public long getIdPresentacion() {
		return idPresentacion;
	}
	public void setIdPresentacion(long idPresentacion) {
		this.idPresentacion = idPresentacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
