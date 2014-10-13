package interfaces;

import controladores.Excepciones;

/**  
* @author Santiago
*
*/
public interface IPredictor {
	
	/**
	 * @author Guille, Santiago
	 * @param idArticulo identificador del articulo a predecir
	 * @return Predice la cantidad que se necesite del artículo con id "idArticulo".  
	 * @throws Excepciones
	 */
	public int predecir(Long idArticulo) throws Excepciones;
}
