package controladores;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import datatypes.DTComprobanteFactura;
import datatypes.DTProveedor;
import uy.com.dusa.ws.DataComprobante;
import uy.com.dusa.ws.DataIVA;
import uy.com.dusa.ws.DataInfoProducto;
import uy.com.dusa.ws.DataLineaComprobante;
import uy.com.dusa.ws.DataLineaPedidoSimple;
import uy.com.dusa.ws.DataPedidoSimple;
import uy.com.dusa.ws.MensajeError;
import uy.com.dusa.ws.PedidoFormaDePago;
import uy.com.dusa.ws.ResultGetComprobante;
import uy.com.dusa.ws.ResultGetComprobantes;
import uy.com.dusa.ws.ResultGetStock;
import uy.com.dusa.ws.ResultRealizarPedido;
import uy.com.dusa.ws.WSConsultaComprobantes;
import uy.com.dusa.ws.WSConsultaComprobantesService;
import uy.com.dusa.ws.WSConsultaStock;
import uy.com.dusa.ws.WSConsultaStockService;
import uy.com.dusa.ws.WSPedidos;
import uy.com.dusa.ws.WSPedidosService;
import model.Articulo;
import model.Enumerados;
import model.Enumerados.TipoFormaDePago;
import model.LineaPedido;
import model.Orden;
import model.OrdenDetalle;
import model.Pedido;
import model.TipoIva;
import model.Usuario;
import interfaces.IServicio;


/**  
* @author Santiago
*
*/
public class ServicioDusaControlador implements IServicio {
	
	/**
	 * @author Guille
	 */

//	private static int dusaId = 2; Ahora esta en enum infoDUSA
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
		articulo.setPorcentajePrecioVenta(new BigDecimal(0));
		articulo.setPrecioVenta(productoDT.getPrecioVenta());
		//articulo.setPresentacion();
		//articulo.setStatus();
		//articulo.setStock();
		//articulo.setStockMinimo();
		//articulo.setTipoAutorizacion();
		//articulo.setTipoIva();
		
		//TODO ver que pasa con esto Jaguerre
		TipoIva tipoIva = new TipoIva();
		tipoIva.setTipoIVA('3');
		articulo.setTipoIva(tipoIva);
		Usuario usr = new Usuario();
		usr.setNombre("Admin");
		articulo.setUsuario(usr);
		//articulo.setUltimoCosto();
		//articulo.setVencimientoMasCercano();
	
		//Proveedores
		DTProveedor proveedor = new DTProveedor();
		proveedor.setIdProveedor(Enumerados.infoDUSA.proveedorID);
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
			System.out.println("Realizar pedido respuesta: " + error.getMensaje());
		} catch (Exception e) {
			
			throw new Excepciones(Excepciones.MENSAJE_ERROR_CONEXION_WS, Excepciones.ERROR_SIN_CONEXION);
		}
	}
	
	@Override
	public List<Articulo> obtenerActualizacionDeStock(java.util.Date fecha) {
		System.out.println("obtenerActualizacionDeStock");
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
	public Articulo obtenerArticulo(int nroArticulo) {
		System.out.println("obtenerActualizacionDeStock");
		Articulo articulo = new Articulo();
		WSConsultaStock servicio = getServicioStock();
		try {
			DataInfoProducto articuloWS =
					servicio.getStock(userTest, passTest, nroArticulo).getProducto();
			
			articulo = transformarArticulo(articuloWS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return articulo;
	}
	
	@Override
	public List<Articulo> obtenerArticulos() {
		List<Articulo> articulos = new ArrayList<Articulo>();
		return articulos;	
	}

	@Override
	public List<TipoIva> obtenerTiposIva() throws Excepciones {
		List<TipoIva> ret = new ArrayList<TipoIva>();
		WSConsultaStock servicio = getServicioStock();
		try{
			List<DataIVA> lista = servicio.getTiposIVA(userTest, passTest).getIvas();
			for (DataIVA di: lista){
				ret.add(transformarTipoIVA(di));
			}
		}catch (Exception e){
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

	private TipoIva transformarTipoIVA(DataIVA di) {
		TipoIva ret = new TipoIva();
		ret.setTipoIVA((char)(short)di.getTipoIVA());
		ret.setDescripcion(di.getDescripcion());
		ret.setTipoTasa(di.getTipoTasa());
		ret.setIndicadorFacturacion(di.getIndicadorFacturacion());
		ret.setValorIVA(di.getValorIVA());
		ret.setValorTributo(di.getValorTributo());
		ret.setResguardoIVA(di.getResguardoIVA());
		ret.setResguardoIRAE(di.getResguardoIRAE());		
		return ret;
	}
	
	private Orden transformarOrden(DataComprobante comprobante) throws Excepciones{
		Orden orden = new Orden();
		
		// seteo los datos básicos de la órden
		orden.setIdProveedor(1);
    	orden.setTipoCFE(comprobante.getTipoCFE());
    	orden.setSerieCFE(comprobante.getSerieCFE());
    	orden.setNumeroCFE(comprobante.getNumeroCFE());
    	orden.setFechaComprobante(comprobante.getFechaComprobante().toGregorianCalendar().getTime());
    	orden.setFormaDePago(comprobante.getFormaDePago().name());
    	orden.setOrdenDeCompra(comprobante.getOrdenDeCompra());
    	orden.setMontoNoGravado(comprobante.getMontoNoGravado());
    	orden.setMontoNetoGravadoIvaMinimo(comprobante.getMontoNetoGravadoIvaMinimo());
    	orden.setMontoNetoGravadoIvaBasico(comprobante.getMontoNetoGravadoIvaBasico());
    	orden.setTotalIvaMinimo(comprobante.getTotalIvaMinimo());
    	orden.setTotalIvaBasico(comprobante.getTotalIvaBasico());
    	orden.setMontoTotal(comprobante.getMontoTotal());
    	orden.setMontoRetenidoIVA(comprobante.getMontoRetenidoIVA());
    	orden.setMontoRetenidoIRAE(comprobante.getMontoRetenidoIRAE());
    	orden.setMontoNoFacturable(comprobante.getMontoNoFacturable());
    	orden.setMontoTotalAPagar(comprobante.getMontoTotalAPagar());
    	orden.setMontoTributoIvaMinimo(comprobante.getMontoTributoIvaMinimo());
    	orden.setMontoTributoIvaBasico(comprobante.getMontoTributoIvaBasico());
    	orden.setProcesada(false);
    	orden.setCantidadLineas(comprobante.getCantidadLineas());
    	
    	// Obtengo todos los detalles de la factura 
    	Iterator<DataLineaComprobante> it = comprobante.getDetalle().iterator();
    	while (it.hasNext()) {
			DataLineaComprobante linea = (DataLineaComprobante) it
					.next();
			OrdenDetalle detalle = new OrdenDetalle();
			
			detalle.setNumeroLinea(linea.getNumeroLinea());
	    	detalle.setNumeroArticulo(linea.getNumeroArticulo());
	    	detalle.setCantidad(linea.getCantidad());
	    	detalle.setPrecioUnitario(linea.getPrecioUnitario());
	    	detalle.setDescuento((linea.getDescuento() != null) ? linea.getDescuento() : new BigDecimal(0));
	    	detalle.setDescripcionOferta(linea.getDescripcionOferta());
	    	detalle.setIndicadorDeFacturacion(linea.getIndicadorDeFacturacion());
	    	
	    	// Obtengo los id todos juntos de los articulos de la factura
	    	try {
				FabricaPersistencia.getInstanciaComprasPersistencia().getDatosArticulo(detalle);
				
				/* En caso que el artículo no exista en el sitema, lo busco en el ws de stock, lo persisto
				 * y luego obtengo el identificador del mismo
				 */
				if(detalle.getProductId() == 0){
					FabricaPersistencia.getStockPersistencia().persistirArticulo(obtenerArticulo(detalle.getNumeroArticulo()));
					FabricaPersistencia.getInstanciaComprasPersistencia().getDatosArticulo(detalle);
				}
			} catch (Excepciones e) {
				e.printStackTrace();
				throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
			}

	    	orden.getDetalle().add(detalle);
		}
		
		return orden;
	}

	@Override
	public void obtenerFacturasDUSA() throws Excepciones {
		List<DataComprobante> listComprobantes = new ArrayList<DataComprobante>();
		try {
			Date ultimaFactura = FabricaPersistencia.getInstanciaComprasPersistencia().getFechaUltimaFacturaDUSA();
			
			WSConsultaComprobantes wscomprobantes = new WSConsultaComprobantesService().getWSConsultaComprobantesPort();
			ResultGetComprobantes resComprobantes = null;
			
			Calendar c = Calendar.getInstance(); 
			c.add(Calendar.DAY_OF_YEAR, -50);  
			Date date = c.getTime();
			
			GregorianCalendar gCalendar = new GregorianCalendar();
			if(ultimaFactura != null){
				gCalendar.setTime(ultimaFactura);
			}else{
				gCalendar.setTime(date);
			}
			
			
			System.out.println("FACTURAS DESDE FECHA " + date);
			
			XMLGregorianCalendar  fechaXML = DatatypeFactory.newInstance().
					newXMLGregorianCalendar(gCalendar);
			resComprobantes = wscomprobantes.getComprobantesDesdeFecha(userTest, passTest, fechaXML);
			
			listComprobantes = resComprobantes.getComprobantes();
			System.out.println("SE ENCONTRAROR CANT COMPROBANTES: " + listComprobantes.size());
			
		} catch (Exception e) {
			e.printStackTrace();
			//throw new Excepciones(Excepciones.MENSAJE_ERROR_CONEXION_WS, Excepciones.ERROR_SIN_CONEXION);
		}
		
		// Para cada factura obtenida, la guardo en la base de datos como pendiente	
		Iterator<DataComprobante> it = listComprobantes.iterator();
		
		while (it.hasNext()) {
			DataComprobante dataComprobante = (DataComprobante) it.next();
			
			Orden orden = transformarOrden(dataComprobante);
			
			FabricaPersistencia.getInstanciaComprasPersistencia().ingresarFacturaCompra(orden);
			
		}
	}
}
