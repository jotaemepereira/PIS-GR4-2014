/**
 * Se encarga de la comunicación mediante webservices con DUSA
 */

package controladores;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import datatypes.DTProveedor;
import uy.com.dusa.ws.DataComprobante;
import uy.com.dusa.ws.DataIVA;
import uy.com.dusa.ws.DataInfoProducto;
import uy.com.dusa.ws.DataLineaComprobante;
import uy.com.dusa.ws.DataLineaPedidoSimple;
import uy.com.dusa.ws.DataPedidoSimple;
import uy.com.dusa.ws.MensajeError;
import uy.com.dusa.ws.PedidoFormaDePago;
import uy.com.dusa.ws.ResultGetComprobantes;
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

	private static String userTest = "PIS2014";
	private static String passTest = "uvM4-N39C-Jt01-mc9E-e95b";

	private WSConsultaStock getServicioStock() {
		return new WSConsultaStockService().getWSConsultaStockPort();
	}

	/**
	 * Transforma un articulo recibido por el webservice, en el modelo articulo
	 * interno
	 * 
	 * @param productoDT
	 *            - data de producto recibida en el ws
	 * @return Articulo - modelo articulo
	 */
	private Articulo transformarArticulo(DataInfoProducto productoDT) {

		Articulo articulo = new Articulo();
		articulo.setDescripcion(productoDT.getDescripcion());
		articulo.setCostoLista(productoDT.getPrecioVenta());
		articulo.setPrecioUnitario(productoDT.getPrecioPublico());
		articulo.setTipoArticulo(model.Enumerados.tipoArticulo.MEDICAMENTO);
		if (productoDT.getPrecioReceta() != null) {
			articulo.setPrecioConReceta(productoDT.getPrecioReceta()
					.getPrecioReceta());

			BigDecimal porcentaje = productoDT
					.getPrecioReceta()
					.getPrecioReceta()
					.divide(productoDT.getPrecioVenta(), 11, RoundingMode.FLOOR);
			articulo.setPorcentajeDescuentoReceta(porcentaje);
		}

		articulo.setClave1(productoDT.getClave1());
		articulo.setClave2(productoDT.getClave2());
		articulo.setClave3(productoDT.getClave3());
		if (productoDT.isHasCodigoBarra())
			articulo.setCodigoBarras(productoDT.getCodigoBarra());

		articulo.setFechaUltimoPrecio(productoDT.getFechaUlitmoPrecio()
				.toGregorianCalendar().getTime());
		articulo.setPorcentajePrecioVenta(new BigDecimal(0));
		articulo.setPrecioVenta(productoDT.getPrecioVenta());

		char habilitado = Character.toChars(productoDT.getHabilitado())[0];
		articulo.setStatus((habilitado == Enumerados.habilitado.HABILITADO) ? true
				: false);

		TipoIva tipoIva = new TipoIva();
		char c = productoDT.getTipoIVA().charAt(0);
		tipoIva.setTipoIVA(c);
		articulo.setTipoIva(tipoIva);

		// TODO: Crear usuario interno del sistema para ejecuciones automáticas
		Usuario usr = new Usuario();
		usr.setNombre("Admin");
		articulo.setUsuario(usr);

		// Proveedores
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
			// Transformo informacion de linea pedido para enviar.
			for (Iterator<LineaPedido> iterator = p.getLineas().iterator(); iterator
					.hasNext();) {

				LineaPedido lPedido = iterator.next();

				DataLineaPedidoSimple dlPedido = new DataLineaPedidoSimple();
				dlPedido.setNumeroArticulo(lPedido.getNumeroArticulo()
						.intValue());
				dlPedido.setCantidad(lPedido.getCantidad());

				dPedido.getLineas().add(dlPedido);
			}

			WSPedidos wsPedido = new WSPedidosService().getWSPedidosPort();
			ResultRealizarPedido resPedido = wsPedido.realizarPedidoSimple(
					userTest, passTest, dPedido);

			// No se utiliza
			MensajeError error = resPedido.getMensaje();

		} catch (Exception e) {

			e.printStackTrace();
			throw new Excepciones(Excepciones.MENSAJE_ERROR_CONEXION_WS,
					Excepciones.ERROR_SIN_CONEXION);
		}
	}

	@Override
	public List<Articulo> obtenerActualizacionDeStock(java.util.Date fecha) {
		List<Articulo> articulos = new ArrayList<Articulo>();
		WSConsultaStock servicio = getServicioStock();
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(fecha);
		try {
			XMLGregorianCalendar fechaXML = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gCalendar);

			List<DataInfoProducto> dataArticulos = servicio.getStockUpdates(
					userTest, passTest, fechaXML).getProductos();
			for (DataInfoProducto dp : dataArticulos) {
				articulos.add(transformarArticulo(dp));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articulos;
	}

	@Override
	public Articulo obtenerArticulo(int nroArticulo) {
		Articulo articulo = new Articulo();
		WSConsultaStock servicio = getServicioStock();
		try {
			DataInfoProducto articuloWS = servicio.getStock(userTest, passTest,
					nroArticulo).getProducto();

			articulo = transformarArticulo(articuloWS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articulo;
	}


	@Override
	public List<TipoIva> obtenerTiposIva() throws Excepciones {
		List<TipoIva> ret = new ArrayList<TipoIva>();
		WSConsultaStock servicio = getServicioStock();
		try {
			List<DataIVA> lista = servicio.getTiposIVA(userTest, passTest)
					.getIvas();
			for (DataIVA di : lista) {
				ret.add(transformarTipoIVA(di));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

	/**
	 * Transforma un DataIVA recibido por el webservcie en un model TipoIva
	 * 
	 * @param di - data recibida por el ws
	 * @return TipoIva - devuelve los datos recibidos mapeados al modelo interno
	 */
	private TipoIva transformarTipoIVA(DataIVA di) {
		TipoIva ret = new TipoIva();
		ret.setTipoIVA((char) (short) di.getTipoIVA());
		ret.setDescripcion(di.getDescripcion());
		ret.setTipoTasa(di.getTipoTasa());
		ret.setIndicadorFacturacion(di.getIndicadorFacturacion());
		ret.setValorIVA(di.getValorIVA());
		ret.setValorTributo(di.getValorTributo());
		ret.setResguardoIVA(di.getResguardoIVA());
		ret.setResguardoIRAE(di.getResguardoIRAE());
		return ret;
	}

	/**
	 * Transforma un DataComprobante recibido por el webservice en un model Orden
	 * 
	 * @param comprobante - data recibida por el ws
	 * @return Orden - devuelve los datos recibidos mapeados al modelo interno
	 * @throws Excepciones
	 */
	private Orden transformarOrden(DataComprobante comprobante)
			throws Excepciones {
		Orden orden = new Orden();

		// seteo los datos básicos de la órden
		orden.setIdProveedor(1);
		orden.setTipoCFE(comprobante.getTipoCFE());
		orden.setSerieCFE(comprobante.getSerieCFE());
		orden.setNumeroCFE(comprobante.getNumeroCFE());
		orden.setFechaComprobante(comprobante.getFechaComprobante()
				.toGregorianCalendar().getTime());
		orden.setFormaDePago(comprobante.getFormaDePago().name());
		orden.setOrdenDeCompra(comprobante.getOrdenDeCompra());
		orden.setMontoNoGravado(comprobante.getMontoNoGravado());
		orden.setMontoNetoGravadoIvaMinimo(comprobante
				.getMontoNetoGravadoIvaMinimo());
		orden.setMontoNetoGravadoIvaBasico(comprobante
				.getMontoNetoGravadoIvaBasico());
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
			DataLineaComprobante linea = (DataLineaComprobante) it.next();
			OrdenDetalle detalle = new OrdenDetalle();

			detalle.setNumeroLinea(linea.getNumeroLinea());
			detalle.setNumeroArticulo(linea.getNumeroArticulo());
			detalle.setCantidad(linea.getCantidad());
			detalle.setPrecioUnitario(linea.getPrecioUnitario());
			detalle.setDescuento((linea.getDescuento() != null) ? linea
					.getDescuento() : new BigDecimal(0));
			detalle.setDescripcionOferta(linea.getDescripcionOferta());
			detalle.setIndicadorDeFacturacion(linea.getIndicadorDeFacturacion());

			// Obtengo los id todos juntos de los articulos de la factura
			try {
				FabricaPersistencia.getInstanciaComprasPersistencia()
						.getDatosArticulo(detalle);

				/*
				 * En caso que el artículo no exista en el sitema, lo busco en
				 * el ws de stock, lo persisto y luego obtengo el identificador
				 * del mismo
				 */
				if (detalle.getProductId() == 0) {
					FabricaPersistencia
							.getStockPersistencia()
							.persistirArticulo(
									obtenerArticulo(detalle.getNumeroArticulo()));
					FabricaPersistencia.getInstanciaComprasPersistencia()
							.getDatosArticulo(detalle);
				}
			} catch (Excepciones e) {
				e.printStackTrace();
				throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
						Excepciones.ERROR_SISTEMA));
			}

			orden.getDetalle().add(detalle);
		}

		return orden;
	}

	@Override
	public void obtenerFacturasDUSA(String usuario) throws Excepciones {
		List<DataComprobante> listComprobantes = new ArrayList<DataComprobante>();
		try {
			Date ultimaFactura = FabricaPersistencia
					.getInstanciaComprasPersistencia()
					.getFechaUltimaFacturaDUSA();

			WSConsultaComprobantes wscomprobantes = new WSConsultaComprobantesService()
					.getWSConsultaComprobantesPort();
			ResultGetComprobantes resComprobantes = null;

			GregorianCalendar gCalendar = new GregorianCalendar();
			if (ultimaFactura != null) {
				gCalendar.setTime(ultimaFactura);
			} else {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DAY_OF_YEAR, -2);
				Date date = c.getTime();
				gCalendar.setTime(date);
			}

			XMLGregorianCalendar fechaXML = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gCalendar);
			resComprobantes = wscomprobantes.getComprobantesDesdeFecha(
					userTest, passTest, fechaXML);

			listComprobantes = resComprobantes.getComprobantes();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Excepciones(Excepciones.MENSAJE_ERROR_CONEXION_WS,
					Excepciones.ERROR_SIN_CONEXION);
		}

		// Para cada factura obtenida, la guardo en la base de datos como
		// pendiente
		Iterator<DataComprobante> it = listComprobantes.iterator();

		while (it.hasNext()) {
			DataComprobante dataComprobante = (DataComprobante) it.next();

			Orden orden = transformarOrden(dataComprobante);
			orden.setNombreUsuario(usuario);

			FabricaPersistencia.getInstanciaComprasPersistencia()
					.ingresarFacturaCompra(orden);
		}

		// Corro la indexación por si se agregó algun artículo al traer las
		// órdenes
		FabricaPersistencia.getStockPersistencia().deltaImportSolr();
	}
}
