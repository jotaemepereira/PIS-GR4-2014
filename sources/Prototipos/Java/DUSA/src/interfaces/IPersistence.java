package interfaces;

import java.util.List;

import model.Articulo;
import model.Venta;

public interface IPersistence {
	
	public List<Articulo> getArticulos(String description);
	public Articulo getArticulo(String barcode);
	
	public void insertSale(Venta venta);
	
}
