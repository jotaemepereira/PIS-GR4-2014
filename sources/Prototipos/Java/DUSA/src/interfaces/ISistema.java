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

	
	/**
	 * Retorna todos los proveedores activos existentes en el sistema.
	 * 
	 * @return Devuelve un Map de DTProveedor con todos los proveedores del sistema
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author José Aguerre
	 */
	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones;
	
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
	 * Genera un pedido de artículos de D.U.S.A. La cantidad de cada artículo es igual a la cantidad vendida, del artículo, desde el pedido anterior.
	 * 
	 * @author Guille
	 * @return Lista de dataType LineaPedido con la informacion del articulo y su cantidad  
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior() throws Excepciones;
	
	/**
	 * Genera un pedido de artículos de D.U.S.A. Donde la cantidad de cada articulo se obtiene de forma predictiva tomando 
	 * en cuenta mismo periodo de años anteriores y estimando los próximos días por medio de mínimos cuadrados.
	 * 
	 * @author Santiago, Guille
	 * @param diasAPredecir cantidad de días a predecir.
	 * @return Lista de dataType LineaPedido con la información del articulo y su cantidad
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> generarPedidoEnBaseAHistorico(int diasAPredecir) throws Excepciones;
	
	/**
	 * @author santiago
	 */
	 public void iniciarSesion(long idUsuario, String contrasenia );
	 public void cerrarSesion(long idUsuario, String contrasenia);
	 
}
