package model;

import java.util.ArrayList;
import java.util.Iterator;

import model.Enumerados.casoDeUso;

public class Usuario {
	
	long usuarioId;
	String nombre;
	String pwd_hash;
	boolean estado;
    private ArrayList<Rol> roles;
    
	public long getUsuarioId() { 
		return usuarioId;
	}
	public boolean tienePermiso(casoDeUso cu){
		
		Iterator<Rol> it = this.roles.iterator();
		while (it.hasNext()){
			Rol r = it.next();
			if (r.tienePermiso(cu))
				return true;
		}
		return false;
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

	public ArrayList<Rol> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<Rol> roles) {
		this.roles = roles;
	}
	           
	

}
