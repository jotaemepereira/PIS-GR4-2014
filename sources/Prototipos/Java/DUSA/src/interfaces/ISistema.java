package interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import controladores.Excepciones;
import datatypes.DTBusquedaArticuloSolr;
import datatypes.DTBusquedaArticulo;
import datatypes.DTLineaPedido;
import datatypes.DTProveedor;
import datatypes.DTVenta;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Pedido;
import model.Presentacion;
import model.Proveedor;

public interface ISistema {
	
	public void actualizarStock() throws Exception;
	
	public void altaProveedor(Proveedor proveedor) throws Excepciones;

	public void altaArticulo(Articulo articulo) throws Excepciones;

	/**
	 * Retorna todos los proveedores activos existentes en el sistema.
	 * 
	 * @return Devuelve un Map de DTProveedor con todos los proveedores del
	 *         sistema
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author José Aguerre
	 */
	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones;

	/**
	 * @author Jmaguerre
	 * @return Devuelve una lista con todas las drogas del sistema.
	 */
	public List<Droga> obtenerDrogas() throws Excepciones;

	/**
	 * @author Jmaguerre
	 * @return Devuelve una lista con todas las acciones terapéuticas del
	 *         sistema.
	 */
	public List<AccionTer> obtenerAccionesTerapeuticas() throws Excepciones;

	/**
	 * realiza la indexación de todos los articulos
	 * 
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */

	/**
	 * Genera un pedido de artículos de D.U.S.A. La cantidad de cada artículo es
	 * igual a la cantidad vendida, del artículo, desde el pedido anterior.
	 * 
	 * @author Guille
	 * @return Lista de dataType LineaPedido con la informacion del articulo y
	 *         su cantidad
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior() throws Excepciones;

	/**
	 * Genera un pedido de artículos de D.U.S.A. Donde la cantidad de cada
	 * articulo se obtiene de forma predictiva tomando en cuenta mismo periodo
	 * de años anteriores y estimando los próximos días por medio de mínimos
	 * cuadrados.
	 * 
	 * @author Santiago
	 * @author Guille
	 * @param diasAPredecir
	 *            cantidad de días a predecir.
	 * @return Lista de dataType LineaPedido con la información del articulo y
	 *         su cantidad
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> generarPedidoEnBaseAHistorico(int diasAPredecir) throws Excepciones;
	
	/**
	 * Se conecta con D.U.S.A. para realizar el pedido, se persiste el pedido si el envío es realizado con éxito.
	 * @author Guille 
	 * @param pedido
	 * @throws Excepciones
	 * 			ERROR_SIN_CONEXION si hay error al realizar la conexión.
	 * 			ERROR_SISTEMA si hay error con la base de datos.
	 */
	public void realizarPedido(Pedido pedido) throws Excepciones;
	/**
	 * @author santiago
	 * @throws Excepciones 
	 */
	 public void iniciarSesion(String nombreUsuario, String contrasenia ) throws Excepciones;
	 public void cerrarSesion(String nombreUsuario, String contrasenia);

	/**
	 * Busca los articulos que coinciden con el string buscar y devuelve los
	 * datos necesarios para la venta
	 * 
	 * @param busqueda
	 *            - string ingresado
	 * @return List<DTVenta> Lista de articulos encontrados
	 * @throws Excepciones
	 * @author Victoria Diaz
	 */
	public List<DTVenta> buscarArticulosVenta(String busqueda)
			throws Excepciones;

	/**
	 * Retorna todos los proveedores activos existentes en el sistema marcados
	 * como marca o laboratorio.
	 * 
	 * @return Devuelve un List de DTProveedor con todos los proveedores del
	 *         sistema marcados como marca o laboratorio.
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author José Aguerre
	 */
	public List<DTProveedor> obtenerMarcas() throws Excepciones;

	/**
	 * retorna los articulos que coincidan con el string ingresado
	 * 
	 * @param busqueda
	 * @return List<Articulo> lista de los articulos encontrados segun el texto
	 *         ingresado con todos los campos necesarios para el caso de uso
	 *         busqueda articulos
	 * @throws Excepciones
	 * @author Victoria Diaz
	 */
	public List<DTBusquedaArticulo> buscarArticulos(
			String busqueda) throws Excepciones;

}
