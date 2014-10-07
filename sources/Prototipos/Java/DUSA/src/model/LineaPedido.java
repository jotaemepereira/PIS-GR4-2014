package model;


//@author Guille
public class LineaPedido {

	Long 	idArticulo;
	int 	cantidad;

	public LineaPedido(Long articulo, int cant) {
		super();
		this.idArticulo = articulo;
		this.cantidad = cant;
	}

	//Getters y Setters
	
	public Long getIdArticulo() {
		return idArticulo;
	}
	
	public void setIdArticulo(Long idArticulo) {
		this.idArticulo = idArticulo;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}