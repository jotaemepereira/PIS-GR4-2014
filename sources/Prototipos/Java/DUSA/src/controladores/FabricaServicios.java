package controladores;

import interfaces.IServicio;
import controladores.ServicioDusaControlador;

/**  
* @author Santiago
*
*/


public class FabricaServicios {
	
	public static IServicio getIServicios(){
		return new ServicioDusaControlador();
	}
	

}
