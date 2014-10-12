package interfaces;

import java.util.List;

import controladores.Excepciones;
import model.Articulo;
import model.Pedido;
import datatypes.DTLineaPedido;
import datatypes.DTProduct;

public interface IStock {

	public void altaArticulo(Articulo articulo) throws Excepciones;

	// public List<DTLineaPedido> pedidoPorVentas();//Deprecated
	public Pedido generarPedidoEnBaseAHistorico(int diasAPredecir)	throws Excepciones;

	public void generarPedido(Pedido p);

	public Pedido generarPedidoEnBaseAPedidoAnterior() throws Excepciones;

	/**
	 * retorna los articulos que coincidan con el string ingresado
	 * 
	 * @param busqueda
	 * @return List<Articulo> lista de los articulos encontrados segun el texto
	 *         ingresado
	 * @throws Excepciones
	 * @author Victoria Diaz
	 */
	public List<Articulo> buscarArticulos(String busqueda) throws Excepciones;
}
