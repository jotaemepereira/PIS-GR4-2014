package controladores;
import java.net.URL;

import javax.xml.namespace.QName;

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
	static String dusaStockURL 			= "http://dev.dusa.com.uy/ws_consulta_stock?wsdl";
	static String dusaComprobantesURL 	= "http://dev.dusa.com.uy/ws_consulta_comprobantes?wsdl";
	static String dusaPedidosURL	 	= "http://dev.dusa.com.uy/ws_pedidos?wsdl";
	@Override
	public void realizarPedido(Pedido p) throws Excepciones {
		
		try {
			
			URL url = new URL(dusaComprobantesURL);
			
			QName qName = new QName("http://ws.dusa.com.uy/", "WSConsultaComprobantesService");
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}