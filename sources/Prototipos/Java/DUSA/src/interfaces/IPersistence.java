package interfaces;

import java.sql.Date;
import java.util.List;

import model.Articulo;
import model.LineaPedido;
import model.LineaVenta;
import model.Venta;

public interface IPersistence {
	
	public List<Articulo> getArticulos(String description);
	public Articulo getArticulo(String barcode);
	
	public void insertSale(Venta venta);
	
	public Date getUltimoPedido();
	public List<LineaPedido> obtenerArticulosDesde(Date fechaPedido);
	
}
