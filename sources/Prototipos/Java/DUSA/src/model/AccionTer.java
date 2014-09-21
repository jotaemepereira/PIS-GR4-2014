package model;

public class AccionTer {
	private long idAccionTer;
	private String descripcion;
	private boolean status;
	public long getIdAccionTer() {
		return idAccionTer;
	}
	public void setIdAccionTer(long idAccionTer) {
		this.idAccionTer = idAccionTer;
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
