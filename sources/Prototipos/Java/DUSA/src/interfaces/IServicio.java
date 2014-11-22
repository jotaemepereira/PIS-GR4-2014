package interfaces;

import java.util.List;
import controladores.Excepciones;
import model.*;

/**  
* @author Santiago
*
*/
public interface IServicio {
	
	/**
	 * Se realiza el pedido a DUSA.
	 * @author Guille
	 * @param p Pedido 
	 */
	public void realizarPedido (Pedido p) throws Excepciones;
	
	
	
	/**
	 * Obtiene los artículos desde el  Web Service de DUSA. 
	 * @param fecha fecha desde la cual se traerán todos los productos que fueron dados
	 * de alta o sufriron cambios en su estado (diponibilidad precio etc)
	 * @return List<Articulo> Lista con los artículso que devuelve Web Service
	 * @throws Excepciones
	 * @author santiago 
	 * 
	 */
	public List<Articulo> obtenerActualizacionDeStock(java.util.Date fecha);
	
	/**
	 * Obtiene los tipos de IVA desde el Web Service
	 * @return List<TipoIva> Lista con los tipos de IVA que devuelve el Web Service
	 * @throws Excepciones
	 * @author Jose Aguerre
	 */
	public List<TipoIva> obtenerTiposIva() throws Excepciones;
	
	/**
	 * trae las facturas por el servicio de DUSA
	 * 
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public void obtenerFacturasDUSA(String usuario) throws Excepciones;
	
	/**
	 * Trae la información de un determinado producto según su numero de producto
	 * 
	 * @param numeroArticulo
	 * @author Victoria Díaz
	 * @return el artículo devuelto por el ws
	 */
	public Articulo obtenerArticulo(int numeroArticulo);
}
