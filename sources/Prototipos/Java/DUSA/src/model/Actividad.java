package model;
/**
 * 
 * @author santiago
 *
 */
public class Actividad {
	
	private long id;
	private String nombre;

	public Actividad(long id, String nombre){
		this.id = id;
		this.nombre =nombre;
	}

	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
	
	
}
