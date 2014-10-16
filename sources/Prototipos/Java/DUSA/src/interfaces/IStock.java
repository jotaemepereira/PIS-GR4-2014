package interfaces;

import java.util.List;
import java.util.Map;

import controladores.Excepciones;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Pedido;
import datatypes.DTBusquedaArticulo;
import datatypes.DTLineaPedido;
import datatypes.DTProduct;

public interface IStock {

	/**
	 * Da de alta un articulo en el sistema
	 * 
	 * @param articulo
	 *            - Articulo
	 * @throws Excepciones
	 *             MENSAJE_ART_DUPLICADO (si ya existe un articulo con esa descripción)
	 *             ERROR_SISTEMA (en caso de error a la hora de persistir en la base de datos)
	 * @author Jmaguerre
	 *      
	 */
	public void altaArticulo(Articulo articulo) throws Excepciones;

	public List<DTLineaPedido> generarPedidoEnBaseAHistorico(int diasAPredecir)	throws Excepciones;

	public void generarPedido(Pedido p);

	/**
	 * Genera un pedido de artículos, de D.U.S.A. La cantidad de cada artículo es igual a la cantidad vendida, del artículo, desde el pedido anterior.
	 * 
	 * @author Guille
	 * @return Una lista de dataType listaPedido con la informacion de articulos y cantidades del pedido generado.    
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior() throws Excepciones;

	/**
	 * retorna los articulos que coincidan con el string ingresado
	 * 
	 * @param busqueda
	 * @return List<Articulo> lista de los articulos encontrados segun el texto
	 *         ingresado
	 * @throws Excepciones
	 * @author Victoria Diaz
	 */
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda) throws Excepciones;

	/**
	 * @author Jmaguerre
	 * @return Devuelve todas las drogas del sistema en un Map para un acceso rápido.
	 */
	public Map<Long, Droga> obtenerDrogas() throws Excepciones;

	/**
	 * @author Jmaguerre
	 * @return Devuelve todas las acciones terapéuticas del sistema en un Map para un acceso rápido.
	 */
	public Map<Long, AccionTer> obtenerAccionesTerapeuticas() throws Excepciones;
}
