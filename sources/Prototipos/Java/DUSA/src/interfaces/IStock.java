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
	public void generarPedido(Pedido p);
	
	/**
	 * @author Guille
	 * @return Genera un pedido de artículos, de D.U.S.A. La cantidad de cada artículo es igual a la cantidad vendida, del artículo, desde el pedido anterior. 
	 * @throws Excepciones
	 */
	public Pedido generarPedidoEnBaseAPedidoAnterior() throws Excepciones;
}
