package interfaces;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import controladores.Excepciones;
import datatypes.DTBusquedaArticulo;
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
	 * @author Juan Manuel, Guille
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
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda) throws Excepciones;
	public void fullImportSolr() throws Excepciones;
	public void deltaImportSolr() throws Excepciones;
	public int getStock(long idArticulo) throws Excepciones;
	public List<Long> obtenerIdTodosLosArticulos() throws Excepciones;
	
	/**
	 * @author Guille
	 * @param idArticulo
	 * @return true si D.U.S.A. maneja el articulo con id "idArticulo"
	 */
	public boolean existeArticuloDeDUSA(Long idArticulo) throws Excepciones;
	
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
	 * Obtener el articulo con id "idArticulo" y su informacion basica de proveedores (DTProveedores)
	 * @param idArticulo 
	 * @return Articulo con id idArticulo, null si no lo encuentra
	 * @throws Excepciones
	 */
	public Articulo obtenerArticuloConId(long idArticulo) throws Excepciones;

	public DTVenta getDatosArticuloVenta(int idArticulo) throws Excepciones;
}
