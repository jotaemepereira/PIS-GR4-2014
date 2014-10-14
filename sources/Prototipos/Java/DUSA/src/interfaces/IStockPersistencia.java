package interfaces;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import controladores.Excepciones;
import datatypes.DTProveedor;
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
	public Date obtenerFechaUltimoPedido() throws Excepciones;
	public void persistirPedido(Pedido p);
	public boolean existeArticulo(String descripcion) throws Excepciones;
	public List<Articulo> buscarArticulos(String busqueda) throws Excepciones;
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
}
