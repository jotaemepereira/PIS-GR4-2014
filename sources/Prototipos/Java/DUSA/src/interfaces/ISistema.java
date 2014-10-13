package interfaces;

import java.util.List;
import java.util.Map;

import controladores.Excepciones;
import datatypes.DTLineaPedido;
import datatypes.DTProveedor;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Pedido;
import model.Presentacion;
import model.Proveedor;

public interface ISistema {

	public void altaProveedor(Proveedor proveedor) throws Excepciones;

	public List<DTLineaPedido> pedidoAutomaticoVentas();

	public void altaArticulo(Articulo articulo) throws Excepciones;

	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones;

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

	/**
	 * realiza la indexación de todos los articulos
	 * 
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */

	/**
	 * @author Guille
	 * @return Genera un pedido de artículos, de D.U.S.A. La cantidad de cada artículo es igual a la cantidad vendida, del artículo, desde el pedido anterior. 
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior() throws Excepciones;
}
