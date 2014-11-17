package interfaces;

import java.util.List;

import controladores.Excepciones;
import datatypes.DTLineaPedido;

/**  
* @author Santiago
*
*/
public interface IPredictor {
	
	/**
	 * @author Guille, Santiago, Jmaguerre
	 * @return Predice la cantidad que se necesita de todos los artículos de DUSA  
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> predecir() throws Excepciones;
}
