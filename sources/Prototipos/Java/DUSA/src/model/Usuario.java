package model;

public class Usuario {
	int usuarioId;
	String nombre;
	String pwd_hash;
	boolean estado;

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPwd_hash() {
		return pwd_hash;
	}

	public void setPwd_hash(String pwd_hash) {
		this.pwd_hash = pwd_hash;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
