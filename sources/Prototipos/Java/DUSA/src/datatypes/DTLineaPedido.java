package datatypes;

public class DTLineaPedido {
	private int numeroArticulo;
	private int idArticulo;
	private int cantidad;
	private String nombreArticulo;
	private int precioUnitario;
	private int precioPonderado;
	private int stockMinimo;
	private int subtotal;
	
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
	public String getNombreArticulo() {
		return nombreArticulo;
	}
	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}
	public int getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(int precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public int getPrecioPonderado() {
		return precioPonderado;
	}
	public void setPrecioPonderado(int precioPonderado) {
		this.precioPonderado = precioPonderado;
	}
	public int getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
	public int getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}
}
