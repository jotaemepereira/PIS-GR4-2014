package model;

/**
 * 
 * @author Guille
 *
 */
public class LineaPedido {

	Long 	idArticulo;
	Long	numeroArticulo;
	int 	cantidad;
	
	public LineaPedido() {
		
		this.idArticulo = new Long(0);
		this.numeroArticulo = new Long(0);
		this.cantidad = 0;
	}
	
	public LineaPedido(Long articulo, Long numArticulo,int cant) {
		
		this.idArticulo = articulo;
		this.numeroArticulo = numArticulo;
		this.cantidad = cant;
	}

	//Getters y Setters
	
	public Long getIdArticulo() {
		return idArticulo;
	}
	
	public void setIdArticulo(Long idArticulo) {
		this.idArticulo = idArticulo;
	}
	
	public Long getNumeroArticulo() {
		return numeroArticulo;
	}
	
	public void setNumeroArticulo(Long numeroArticulo) {
		this.numeroArticulo = numeroArticulo;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}