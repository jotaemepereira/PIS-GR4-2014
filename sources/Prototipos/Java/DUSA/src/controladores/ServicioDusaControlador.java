package controladores;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import datatypes.DTProveedor;
import uy.com.dusa.ws.DataInfoProducto;
import uy.com.dusa.ws.DataLineaPedidoSimple;
import uy.com.dusa.ws.DataPedidoSimple;
import uy.com.dusa.ws.MensajeError;
import uy.com.dusa.ws.PedidoFormaDePago;
import uy.com.dusa.ws.ResultRealizarPedido;
import uy.com.dusa.ws.WSConsultaStock;
import uy.com.dusa.ws.WSConsultaStockService;
import uy.com.dusa.ws.WSPedidos;
import uy.com.dusa.ws.WSPedidosService;
import model.Articulo;
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
	private static int dusaId = 2;
	private static String userTest			 	= "PIS2014";
	private static String passTest			 	= "uvM4-N39C-Jt01-mc9E-e95b";
	
	private WSConsultaStock getServicioStock() {
		return new WSConsultaStockService().getWSConsultaStockPort();
	}
	
	private Articulo transformarArticulo(DataInfoProducto productoDT) {
		Articulo articulo = new Articulo();
		articulo.setDescripcion(productoDT.getDescripcion());
		articulo.setCostoLista(productoDT.getPrecioVenta());
		articulo.setPrecioUnitario(productoDT.getPrecioPublico());
		articulo.setTipoArticulo(model.Enumerados.tipoArticulo.MEDICAMENTO);
		
		//articulo.setAccionesTer();
		
		articulo.setClave1(productoDT.getClave1());
		articulo.setClave2(productoDT.getClave2());
		articulo.setClave3(productoDT.getClave3());
		articulo.setCodigoBarras(productoDT.getCodigoBarra());
		//articulo.setCodigoVenta();
		//articulo.setCostoOferta();
		//articulo.setCostoPromedio();
		//articulo.setDrogas();
		//articulo.setEsEstupefaciente();
		//articulo.setEsHeladera();
		//articulo.setEsPsicofarmaco();
		articulo.setFechaUltimoPrecio(productoDT.getFechaUlitmoPrecio().toGregorianCalendar().getTime());
		//articulo.setFechaUltimaModificacion(productoDT.getFechaUltimaActualizacion().toGregorianCalendar().getTime());
		//articulo.setIdMarca();
		articulo.setNumero(productoDT.getNumeroArticulo());
		//articulo.setPorcentajePrecioVenta();
		articulo.setPrecioVenta(productoDT.getPrecioVenta());
		//articulo.setPresentacion();
		//articulo.setStatus();
		//articulo.setStock();
		//articulo.setStockMinimo();
		//articulo.setTipoAutorizacion();
		//articulo.setTipoIva();
		//articulo.setUltimoCosto();
		//articulo.setVencimientoMasCercano();
	
		//Proveedores
		DTProveedor proveedor = new DTProveedor();
		proveedor.setIdProveedor(dusaId);
		proveedor.setCodigoIdentificador(productoDT.getNumeroArticulo());
		articulo.agregarProveedor(proveedor);
		
		return articulo;
	}
	
	@Override
	public void realizarPedido(Pedido p) throws Excepciones {
		
		try {
			
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
			System.out.println(error.getMensaje());
		} catch (Exception e) {
			
			throw new Excepciones(Excepciones.MENSAJE_ERROR_CONEXION_WS, Excepciones.ERROR_SIN_CONEXION);
		}
	}
	
	@Override
	public List<Articulo> obtenerActualizacionDeStock(java.util.Date fecha) {
		List<Articulo> articulos = new ArrayList<Articulo>();
		WSConsultaStock servicio = getServicioStock();
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(fecha);
		try {
			XMLGregorianCalendar  fechaXML = DatatypeFactory.newInstance().
					newXMLGregorianCalendar(gCalendar);
	
			List<DataInfoProducto> dataArticulos =
					servicio.getStockUpdates(userTest, passTest, fechaXML).getProductos();
			for (DataInfoProducto dp: dataArticulos) {
				articulos.add(transformarArticulo(dp));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return articulos;
	}
	
	@Override
	public List<Articulo> obtenerArticulos() {
		List<Articulo> articulos = new ArrayList<Articulo>();
		return articulos;	
	}
}