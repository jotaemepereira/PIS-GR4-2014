package interfaces;
import controladores.Excepciones;
import model.Pedido;

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
}
