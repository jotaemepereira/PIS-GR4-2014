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
	public List<Articulo> obtenerArticulos() throws Excepciones;
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
