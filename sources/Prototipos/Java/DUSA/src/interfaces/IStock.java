package interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import controladores.Excepciones;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Pedido;
import model.TipoIva;
import datatypes.DTBusquedaArticuloSolr;
import datatypes.DTBusquedaArticulo;
import datatypes.DTLineaPedido;
import datatypes.DTProduct;
import datatypes.DTVenta;

public interface IStock {

	/**
	 * Da de alta un articulo en el sistema
	 * 
	 * @param articulo
	 *            - Articulo
	 * @throws Excepciones
	 *             MENSAJE_ART_DUPLICADO (si ya existe un articulo con esa
	 *             descripción) ERROR_SISTEMA (en caso de error a la hora de
	 *             persistir en la base de datos)
	 * @author Jmaguerre
	 * 
	 */
	public void altaArticulo(Articulo articulo) throws Excepciones;
	
	/**
	 * Modifica un articulo del sistema
	 * 
	 * @param articulo
	 *            - Articulo
	 * @throws Excepciones
	 *             ERROR_SISTEMA (en caso de error a la hora de
	 *             persistir en la base de datos)
	 * @author Jmaguerre
	 * 
	 */
	public void modificarArticulo(Articulo articulo) throws Excepciones;

	/**
	 * Genera un pedido de artículos de D.U.S.A. Donde la cantidad de cada
	 * articulo se obtiene de forma predictiva estimando los próximos días 
	 * por medio de mínimos cuadrados.
	 * 
	 * @author Guille
	 * @author Santiago
	 * @return Una lista de dataType listaPedido con la informacion de articulos
	 *         y cantidades del pedido generado.
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> generarPedidoEnBaseAHistorico(int diasAPredecir)
			throws Excepciones;

	/**
	 * Se realiza conexión con el servidor de D.U.S.A. y se les envía el pedido.
	 * Si no hay error se persiste el pedido.
	 * 
	 * @author Guille, Santiago
	 * @param p
	 *            Pedido a realizar
	 * @throws Excepciones
	 */
	public void realizarPedido(Pedido p) throws Excepciones;

	/**
	 * Genera un pedido de artículos, de D.U.S.A. La cantidad de cada artículo
	 * es igual a la cantidad vendida, del artículo, desde el pedido anterior.
	 * 
	 * @author Guille
	 * @return Una lista de dataType listaPedido con la informacion de articulos
	 *         y cantidades del pedido generado.
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior()
			throws Excepciones;

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
	 * retorna los articulos que coincidan con el string ingresado
	 * 
	 * @param busqueda
	 * @return List<Articulo> lista de los articulos encontrados segun el texto
	 *         ingresado con los datos necesarios para el caso de uso busqueda
	 *         articulo
	 * @throws Excepciones
	 * @author Victoria Diaz
	 */
	public List<DTBusquedaArticulo> buscarArticulos(
			String busqueda) throws Excepciones;

	/**
	 * Retorna los distintos tipos de iva existentes en el sistema.
	 * 
	 * @return List<TipoIva> lista de los distintos tipos de iva existentes en el sistema.
	 * @throws Excepciones
	 * @author José Aguerre
	 */
	public List<TipoIva> obtenerTiposIva() throws Excepciones;
	
	/**
	 * Modifica el stock del articulo con id idArticulo al valor nuevoValor
	 * 
	 * @author Guille
	 * @param idArticulo
	 * @param nuevoValor stock nuevo para el articulo. 
	 * @throws Excepciones
	 */
	public void modificarStock(long idArticulo, long nuevoValor) throws Excepciones;

	
	/**
	 * Modifica el stock del articulo con id idArticulo al valor nuevoValor
	 * 
	 * @author Seba
	 * @param idsArticulo
	 * @param nuevosValores
	 * @throws Excepciones
	 */
	public void modificarStock(long[] idsArticulo, long[] nuevosValores) throws Exception;

	/**
	 * Metodo para efectuar un desarme
	 * 
	 * @author Seba
	 * @param idArticulo
	 * @param idDestino
	 * @param nuevoValorArticulo
	 * @param nuevoValorDestino
	 * @param nuevoValor stock nuevo para el articulo. 
	 * @throws Excepciones
	 */
	public void modificarStock(long idArticulo, long idDestino, long nuevoValorArticulo, long nuevoValorDestino) throws Excepciones;
	
	/**
	 * retorna los articulos que coincidan con el string ingresado
	 * @param busqueda - string a buscar
	 * @param proveedor - buscar articulos de determinado proveedor
	 * @return List<DTBusquedaArticulo> lista de los articulos encontrados segun el texto
	 *         ingresado con todos los campos necesarios para el caso de uso
	 *         busqueda articulos
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda, int proveedor) throws Excepciones;

	/**
	 * Devuelve un artículo con todos sus datos, sus proveedores, drogas y acciones terapéuticas.
	 * @param idArticulo
	 * 			- Identificador del artículo.
	 * @return Articulo
	 * 			- Entidad de modelo con los datos del artículo de id idArticulo. 
	 * @throws Excepciones
	 * @author José Aguerre
	 */
	public Articulo obtenerArticulo(int idArticulo) throws Excepciones;
	
	/**
	 * Se dispara automaticamente, busca los articulos de dusa que sufrieron cambios en 
	 * un periodo de tiempo se chequea el tipo de cambios se actualiza la base de datos 
	 * y se envia un mail a los administradores del sistema informando de los cambios 
	 * @param fecha
	 * @throws Excepciones
	 */
	public void actualizarStock(Date fecha) throws Excepciones;
	
}
