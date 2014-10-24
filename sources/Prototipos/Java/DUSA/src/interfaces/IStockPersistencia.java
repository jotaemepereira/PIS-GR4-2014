package interfaces;

import java.sql.Date;
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
import model.Pedido;

/**  
* @author Santiago
*
*/
public interface IStockPersistencia {
	
	public void persistirArticulo(Articulo articulo)throws Excepciones;
	
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
	public List<DTBusquedaArticuloSolr> buscarArticulosSolr(String busqueda) throws Excepciones;
	public void fullImportSolr() throws Excepciones;
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
}
