package interfaces;

import java.util.List;

import controladores.Excepciones;
import model.Articulo;
import model.Pedido;
import datatypes.DTLineaPedido;
import datatypes.DTProduct;

public interface IStock {

	public void altaArticulo(Articulo articulo) throws Excepciones;
//	public List<DTLineaPedido> pedidoPorVentas();//Deprecated
	public Pedido generarPedido01UltimoPedido();
	public void generarPedido(Pedido p);
}
