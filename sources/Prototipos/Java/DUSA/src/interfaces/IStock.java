package interfaces;

import java.util.List;

import model.Articulo;
import datatypes.DTLineaPedido;
import datatypes.DTProduct;

public interface IStock {

	public void altaArticulo(Articulo articulo);
	public List<DTLineaPedido> pedidoPorVentas();
}
