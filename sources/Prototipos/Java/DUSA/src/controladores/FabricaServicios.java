package controladores;

import interfaces.ISistema;
import interfaces.IServicio;
import controladores.ServicioDusaControlador;

/**  
* @author Santiago
*
*/


public class FabricaServicios {
	
	public static IServicio getIServicios(){
		return (IServicio) new ServicioDusaControlador();
	}

}
