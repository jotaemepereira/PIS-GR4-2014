package model;
/**
 * 
 * @author santiago
 *
 */
public class Operacion {

	private long id;
	private String nombre;
	
	public Operacion(long id, String nombre){
		this.id = id;
		this.nombre = nombre;
	}

	public long getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}

}

