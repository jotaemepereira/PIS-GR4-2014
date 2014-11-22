/**
 * Interfaz para implementar, distintos tipos de estrategias, al obtener los articulos
 *  para generar pedido.
 *  
 *  Actualmente no se esta usando en la estrategia de generara pedido, para optimizar los tiempos.
 */
package interfaces;

import java.util.List;

import controladores.Excepciones;


/**  
* @author Santiago
*
*/

public interface ISeleccionador {

	/**
	 * @author Guille, Santiago
	 * @return Obtiene los id de artículos según implementación. Para generar pedido automático.
	 * @throws Excepciones
	 */
	public List <Long> getIDArticulos() throws Excepciones;

}
