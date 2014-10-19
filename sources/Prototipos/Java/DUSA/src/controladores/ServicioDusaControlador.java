package controladores;
import java.net.URL;

import javax.xml.namespace.QName;

import uy.com.dusa.ws.PedidoObjectFactory;
import model.Pedido;
import interfaces.IServicio;


/**  
* @author Santiago
*
*/
public class ServicioDusaControlador implements IServicio {
	
	/**
	 * @author Guille
	 */
	private static String dusaStockURL 			= "http://dev.dusa.com.uy/ws_consulta_stock?wsdl";
	private static String dusaComprobantesURL 	= "http://dev.dusa.com.uy/ws_consulta_comprobantes?wsdl";
	private static String dusaPedidosURL	 	= "http://dev.dusa.com.uy/ws_pedidos?wsdl";
	
	private static String userTest			 	= "PIS2014";
	private static String passTest			 	= "uvM4-N39C-Jt01-mc9E-e95b";
	
	@Override
	public void realizarPedido(Pedido p) throws Excepciones {
		
		try {
			
			URL url = new URL(dusaComprobantesURL);
			
//			QName qName = new PedidoObjectFactory();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}