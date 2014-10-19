package controladores;
import java.net.URL;
import java.util.Iterator;

import javax.xml.namespace.QName;

import uy.com.dusa.ws.DataLineaPedidoSimple;
import uy.com.dusa.ws.DataPedidoSimple;
import uy.com.dusa.ws.MensajeError;
import uy.com.dusa.ws.PedidoFormaDePago;
import uy.com.dusa.ws.PedidoObjectFactory;
import uy.com.dusa.ws.ResultRealizarPedido;
import uy.com.dusa.ws.WSPedidos;
import uy.com.dusa.ws.WSPedidosService;
import model.Enumerados.TipoFormaDePago;
import model.LineaPedido;
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
			
//			URL url = new URL(dusaComprobantesURL);
//			QName qName = new PedidoObjectFactory();
			DataPedidoSimple dPedido = new DataPedidoSimple();
			if (p.getFormaDePago() == TipoFormaDePago.CONTADO) {
				
				dPedido.setFormaDePago(PedidoFormaDePago.CONTADO);
			} else {

				dPedido.setFormaDePago(PedidoFormaDePago.CREDITO);
			}
			
			for (Iterator<LineaPedido> iterator = p.getLineas().iterator(); iterator.hasNext();) {
				
				LineaPedido lPedido = iterator.next();
				
				DataLineaPedidoSimple dlPedido = new DataLineaPedidoSimple();
				dlPedido.setNumeroArticulo(lPedido.getNumeroArticulo().intValue());
				dlPedido.setCantidad(lPedido.getCantidad());
				
				dPedido.getLineas().add(dlPedido);
			}
			
			WSPedidos wsPedido = new WSPedidosService().getWSPedidosPort();
			ResultRealizarPedido resPedido = wsPedido.realizarPedidoSimple(userTest, passTest, dPedido);
			
			MensajeError error = resPedido.getMensaje();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}