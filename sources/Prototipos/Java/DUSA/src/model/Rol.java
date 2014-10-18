package model;

import java.util.ArrayList;
/**
 * 
 * @author santiago
 *
 */
public class Rol {
	
	private long id;
	private String nombre;
	private ArrayList<Operacion> operaciones;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Operacion> getOperaciones() {
		return operaciones;
	}
	public void setOperaciones(ArrayList<Operacion> operaciones) {
		this.operaciones = operaciones;
	}
	

}