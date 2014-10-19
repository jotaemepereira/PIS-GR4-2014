package model;
/**
 * 
 * @author santiago
 *
 */
public class Operacion {

	private long id;
	private String nombre;
	
	public Operacion(){
	
		
	}

	public void setId(long id){
		this.id =id;
	}
	public long getId() {
		return id;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}

