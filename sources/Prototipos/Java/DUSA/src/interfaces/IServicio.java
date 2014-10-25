package interfaces;

import java.sql.Date;
import java.util.List;

import controladores.Excepciones;
import model.*;

/**  
* @author Santiago
*
*/
public interface IServicio {
	
	/**
	 * @author Guille
	 * @param p Pedido 
	 */
	public void realizarPedido (Pedido p) throws Excepciones;
	public List<Articulo> obtenerArticulos() throws Excepciones;
	public List<Articulo> obtenerActualizacionDeStock(java.util.Date fecha);
}
