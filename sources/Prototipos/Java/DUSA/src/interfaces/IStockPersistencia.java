package interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import controladores.Excepciones;
import datatypes.DTBusquedaArticulo;
import datatypes.DTBusquedaArticuloSolr;
import datatypes.DTProveedor;
import datatypes.DTVenta;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.LineaPedido;
import model.OrdenDetalle;
import model.Pedido;
import model.TipoIva;

public interface IStockPersistencia {
	
	/**
	 * Da de alta un articulo en el sistema
	 * 
	 * @param articulo
	 *            - Articulo
	 * @throws Excepciones
	 *             ERROR_SISTEMA (en caso de error a la hora de
	 *             persistir en la base de datos)
	 * @author Jmaguerre
	 * 
	 */
	public void persistirArticulo(Articulo articulo)throws Excepciones;
	
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
	 * Retorna la fecha del ultimo pedido realizado a D.U.S.A.
	 * @author Juan Manuel
	 * @author Guille
	 * @return Date, fecha del ultimo pedido.
	 * @throws Excepciones
	 */
	public Date obtenerFechaUltimoPedido() throws Excepciones;
	
	/**
	 * Persiste el pedido realizado a D.U.S.A. con todas las LineasPedidos asignadas.
	 * @author Guille
	 * @param p Pedido a persistir.
	 * @throws Excepciones
	 */
	public void persistirPedido(Pedido p) throws Excepciones;
	public boolean existeArticulo(String descripcion) throws Excepciones;
	
	/**
	 * función encargada de realizar la busqueda en solr
	 * @param busqueda - string a buscar
	 * @return lista con los articulos encontrados
	 * @throws Excepciones
	 */
	public List<DTBusquedaArticuloSolr> buscarArticulosSolr(String busqueda) throws Excepciones;
	
	/**
	 * función encargada de realizar la busqueda en solr
	 * @param busqueda - string a buscar
	 * @param proveedor - filtrar por proveedor
	 * @return lista de articulos encontrados
	 * @throws Excepciones
	 */
	List<DTBusquedaArticuloSolr> buscarArticulosSolr(String busqueda, int proveedor) throws Excepciones;
	
	/**
	 * realiza la reindexacion total de solr
	 * @throws Excepciones
	 */
	public void fullImportSolr() throws Excepciones;
	
	/**
	 * actualiza los indices modificados a partir de la última vez que se realizó una importacion
	 * @throws Excepciones
	 */
	public void deltaImportSolr() throws Excepciones;
	
	/**
	 * @author Guille
	 * @param idArticulo
	 * @return Stock minimo del articulo con id idArticulo, caso de nulo retorna cero.
	 * @throws Excepciones
	 */
	public long getStockMinimo(long idArticulo) throws Excepciones;
	
	/**
	 * @author Santiago
	 * @param idArticulo
	 * @return cantidad de Stock del articulo con id articulo
	 * @throws Excepciones
	 */
	public int getStock(long idArticulo) throws Excepciones;
	
	/**
	 * Retorna una lista de ids de articulos, SOLO de D.U.S.A. y no borrados.
	 * @return Lista de ids de articulos de D.U.S.A.
	 * @throws Excepciones
	 * @author Santiago
	 * @author Guille
	 */
	public List<Long> obtenerIdTodosLosArticulos() throws Excepciones;
	
	/**
	 * @deprecated
	 * @author Guille
	 * @param idArticulo
	 * @return true si D.U.S.A. maneja el articulo con id "idArticulo"
	 */
	public boolean existeArticuloDeDUSA(Long idArticulo) throws Excepciones;
	
	/**
	 * @author Jmaguerre
	 * @return Devuelve una lista con todas las drogas del sistema.
	 */
	public List<Droga> obtenerDrogas() throws Excepciones;
	
	/**
	 * @author Jmaguerre
	 * @return Devuelve una lista con todas las acciones terapéuticas del sistema.
	 */
	public List<AccionTer> obtenerAccionesTerapeuticas() throws Excepciones;
	
	/**
	 * Obtener el articulo con id "idArticulo" y su informacion basica de proveedores (DTProveedores)
	 * @author Guille
	 * @param idArticulo 
	 * @return Articulo con id idArticulo, null si no lo encuentra
	 * @throws Excepciones
	 */
	public Articulo obtenerArticuloConId(long idArticulo) throws Excepciones;

	public DTVenta getDatosArticuloVenta(int idArticulo) throws Excepciones;

	/**
	 * En base a lo encontrado usando solr, complementa los datos para ese artícuo
	 * @param articulo - los datos parciales del articulo
	 * @author Victoria Díaz
	 * @throws Excepciones 
	 */
	void buscarArticulosId(DTBusquedaArticulo articulo) throws Excepciones;

	/**
	 * Retorna los distintos tipos de iva existentes en el sistema.
	 * 
	 * @return List<TipoIva> lista de los distintos tipos de iva existentes en el sistema.
	 * @throws Excepciones
	 * @author José Aguerre
	 */
	public List<TipoIva> obtenerTiposIva() throws Excepciones;

	/**
	 * Persiste los tipos de iva en el sistema.
	 * @param lista
	 * @author Jose Aguerre
	 */
	public void persistirTiposIva(List<TipoIva> lista) throws Excepciones;
	
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
	 * Modifica el stock de los articulos en idsArticulo con los nuevos Valores en nuevosValores
	 * 
	 * @author Guille
	 * @param idArticulo
	 * @param nuevoValor stock nuevo para el articulo. 
	 * @throws Excepciones
	 */
	public void modificarStock(long[] idsArticulo, long[] nuevosValores) throws Exception;
	
	/**
	 * Persiste el movimiento de stock del articulo 'articuloID' realizado por el usuario 'usuario' 
	 * 
	 * @author Guille
	 * @param usuario
	 * @param productoID
	 * @param cantidad Cantidad relacionada al tipo de movimiento
	 * @param char tipoMovimientoDeStock indicando si es un aumento, baja o desarme de stock
	 * @param motivo Motivo del cambio de stock
	 * @see model.Enumerados.tipoMovimientoDeStock
	 * @throws Exception
	 */
	public void movimientoStock(String usuario, long aticuloID, long cantidad, char tipoMovimiento, String motivo) throws Exception;

	/**
	 * Aumenta el valor del stock segun lo comprado
	 * 
	 * @param detalles
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public void actualizarStockCompra(List<OrdenDetalle> detalles) throws Excepciones;
}
