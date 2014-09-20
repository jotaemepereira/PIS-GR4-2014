package model;

public class LineaPedido {

	int numeroArticulo;
	int idArticulo;
	int cantidad;
	
	public LineaPedido(int numeroArticulo, int idArticulo, int cantidad) {
		super();
		this.numeroArticulo = numeroArticulo;
		this.idArticulo = idArticulo;
		this.cantidad = cantidad;
	}
	public int getNumeroArticulo() {
		return numeroArticulo;
	}
	public void setNumeroArticulo(int numeroArticulo) {
		this.numeroArticulo = numeroArticulo;
	}
	public int getIdArticulo() {
		return idArticulo;
	}
	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}


