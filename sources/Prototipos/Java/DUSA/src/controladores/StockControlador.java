/**
 * Controla las operaciones del modulo stock del sistema
 */
package controladores;

import interfaces.IPredictor;
import interfaces.ISeleccionador;
import interfaces.IServicio;
import interfaces.IStock;
import interfaces.IStockPersistencia;
import model.AccionTer;
import model.Articulo;
import model.Cambio;
import model.Droga;
import model.GeneradorPedido;
import model.Mail;
import model.Pedido;
import model.TipoIva;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;
import persistencia.PStockControlador;
import datatypes.DTBusquedaArticuloSolr;
import datatypes.DTBusquedaArticulo;
import datatypes.DTLineaPedido;
import datatypes.DTModificacionArticulo;
import datatypes.DTProducto;
import datatypes.DTVencimiento;
import controladores.FabricaPersistencia;

public class StockControlador implements IStock {

	@Override
	public void altaArticulo(Articulo articulo) throws Excepciones {

		// Me fijo si ya existe otro articulo con la misma descripcion
		if ((articulo.getDescripcion() != "")
				&& (FabricaPersistencia.getStockPersistencia()
						.existeArticulo(articulo.getDescripcion()))) {
			throw (new Excepciones(Excepciones.MENSAJE_ART_DUPLICADO,
					Excepciones.ADVERTENCIA_DATOS));
		}
		FabricaPersistencia.getStockPersistencia().persistirArticulo(articulo);
		// indexacion de solr del producto nuevo
		FabricaPersistencia.getStockPersistencia().deltaImportSolr();
	}

	public List<Articulo> buscarArticulo(String descripcion) {

		PStockControlador ps = (PStockControlador) FabricaPersistencia
				.getStockPersistencia();
		return ps.buscarArticulo(descripcion);

	}

	public Articulo obtenerArticulo(Long id) {

		Articulo articulo = null;

		try {

			articulo = FabricaPersistencia.getStockPersistencia()
					.obtenerArticuloConId(id.longValue());
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return articulo;
	}

	/**
	 * 
	 * @author Guille
	 */
	@Override
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior()
			throws Excepciones {

		IStockPersistencia sp = FabricaPersistencia.getStockPersistencia();
		Date ultimoPedido = sp.obtenerFechaUltimoPedido();

		if (ultimoPedido == null) {
			// Caso base para el pedido: Se toma las ventas realizadas en el dia
			// de hoy.
			Calendar cal = Calendar.getInstance();
			// Trunc la fecha de hoy.
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			ultimoPedido = new Date(cal.getTimeInMillis());
		}
		SeleccionarArticulosDesde seleccionarDesde = new SeleccionarArticulosDesde(
				ultimoPedido);
		PredecirCantidadDesde predecirDesde = new PredecirCantidadDesde(
				ultimoPedido);

		GeneradorPedido gr = new GeneradorPedido(seleccionarDesde,
				predecirDesde);

		List<DTLineaPedido> pedidoGenerado = gr.generar();
		return pedidoGenerado;
	}

	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda)
			throws Excepciones {
		IStockPersistencia isp = FabricaPersistencia.getStockPersistencia();
		List<DTBusquedaArticuloSolr> encontrados = isp.buscarArticulosSolr(busqueda);

		return isp.getDatosArticulosBuscados(encontrados);
	}

	@Override
	public List<DTLineaPedido> generarPedidoEnBaseAHistorico(int diasAPredecir)
			throws Excepciones {

		ISeleccionador st = new SeleccionarTodos();
		IPredictor pr = new PredecirEnBaseAHistorico(diasAPredecir);
		GeneradorPedido gp = new GeneradorPedido(st, pr);
		List<DTLineaPedido> pedidoGenerado = gp.generar();
		return pedidoGenerado;

	}

	/**
	 * 
	 * @author Santiago
	 * @author Guille
	 */
	@Override
	public void realizarPedido(Pedido p) throws Excepciones {

		IServicio is = FabricaServicios.getIServicios();
		is.realizarPedido(p);

		IStockPersistencia isp = FabricaPersistencia.getStockPersistencia();
		isp.persistirPedido(p);
	}

	@Override
	public List<Droga> obtenerDrogas() throws Excepciones {
		return FabricaPersistencia.getStockPersistencia().obtenerDrogas();
	}

	@Override
	public List<AccionTer> obtenerAccionesTerapeuticas() throws Excepciones {
		return FabricaPersistencia.getStockPersistencia()
				.obtenerAccionesTerapeuticas();
	}

	/**
	 * Trae los tipos de iva desde el webservice de dusa y los guarda en la base de datos
	 * 
	 * No se utiliza la funcion en el sistema, fue utilizada en el momento de desarrollar
	 */
	private void grabarTiposIVA() {
		try {
			List<TipoIva> lista = FabricaServicios.getIServicios()
					.obtenerTiposIva();
			FabricaPersistencia.getStockPersistencia().persistirTiposIva(lista);
		} catch (Excepciones e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<DTProducto> buscarArticulosVenta(String busqueda)
			throws Excepciones {
		List<DTBusquedaArticuloSolr> lista = FabricaPersistencia
				.getStockPersistencia().buscarArticulosSolr(busqueda);

		return FabricaPersistencia.getStockPersistencia()
				.getDatosArticuloVenta(lista);
	}
	
	@Override
	public DTProducto buscarArticulosVentaPorCodigo(String codigo)
			throws Excepciones {
			return FabricaPersistencia.getStockPersistencia()
					.getDatosArticuloVentaPorCodigo(codigo);
	}

	@Override
	public void actualizarStock(Date fecha) throws Excepciones {
		

		Mail m;

		/**
		 * se traen todos los artículos que tuvieron cambios desde la fecha que
		 * se recibe por  parámetro hasta el día de hoy
		 */
		List<Articulo> articulos = FabricaServicios.getIServicios().obtenerActualizacionDeStock(fecha);
		/**
		 * si un artículo bajó su precio o cambió su estado de dado de baja a dejar de estarlo
		 * se agrega a la lista de cambios para ser enviado por mail
		 */
		List<Cambio> cambios = FabricaPersistencia.getStockPersistencia().obtenerCambios(articulos);
		
		
		/**
		 * Se lee el mail y password del correo emisor
		 * y mails de los receptores en caso de que no existan 
		 * del archivo .properties
		 * en caso de que no existan estas propiedades se crean
		 * Finalmente se actualiza al fecha de fechaUltimaActualizacion
		 * al día de hoy
		 * 
		 */
		
		OutputStream output;
		try {
			FileInputStream in = new FileInputStream("alertaStock.properties");
			Properties prop = new Properties();
			prop.load(in);
			String mailEmisor = prop.getProperty("mailEmisor");
			String passEmisor = prop.getProperty("passEmisor");
			String receptores = prop.getProperty("mailReceptores");

			in.close();
			if (mailEmisor==null)
				mailEmisor = "dusapis";
			if (passEmisor ==null)
				passEmisor = "grupo4grupo4";
			if (receptores == null)
				receptores = "dusapis@gmail.com";

			output = new FileOutputStream("alertaStock.properties");
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
			String sDate = sdf.format(date);
			prop.setProperty("fechaUltimaActualizacion", sDate);
			prop.setProperty("mailEmisor",mailEmisor);
			prop.setProperty("passEmisor",passEmisor);
			prop.setProperty("mailReceptores",receptores);
			prop.store(output, null);


			if (cambios.size()!=0){

				m = new Mail();
				m.setAsunto("cambio en productos de DUSA");   
				m.setContenido(cambios);
				m.setEmisor(mailEmisor, passEmisor);
				m.setDestinatarios(receptores);
				m.Enviar();
			}
			FabricaPersistencia.getStockPersistencia().fullImportSolr();

		} catch (IOException | MessagingException e) {
			
			e.printStackTrace();
		}

	}                      

	@Override
	public List<TipoIva> obtenerTiposIva() throws Excepciones {
		return FabricaPersistencia.getStockPersistencia().obtenerTiposIva();
	}

	/**
	 * @author Guille
	 */
	@Override
	public void modificarStock(long idArticulo, long nuevoValor)
			throws Excepciones {

		FabricaPersistencia.getStockPersistencia().modificarStock(idArticulo,
				nuevoValor);
	}

	/**
	 * @author Seba
	 */
	@Override
	public void modificarStock(long idArticulo, long idDestino,
			long nuevoValorArticulo, long nuevoValorDestino) throws Excepciones {

		FabricaPersistencia.getStockPersistencia().modificarStock(idArticulo,
				nuevoValorArticulo);
		
		FabricaPersistencia.getStockPersistencia().modificarStock(idDestino,
				nuevoValorDestino);
	}
	
	/**
	 * @author Seba
	 */
	@Override
	public void modificarStock(long[] idsArticulo, long[] nuevosValores) throws Exception{

		FabricaPersistencia.getStockPersistencia().modificarStock(idsArticulo,
				nuevosValores);
	}

	@Override
	public void modificarArticulo(DTModificacionArticulo articulo) throws Excepciones {
		FabricaPersistencia.getStockPersistencia().modificarArticulo(articulo);
		// indexacion de solr del producto modificado
		FabricaPersistencia.getStockPersistencia().deltaImportSolr();
	}

	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda,
			long proveedor) throws Excepciones {
		IStockPersistencia isp = FabricaPersistencia.getStockPersistencia();
		List<DTBusquedaArticuloSolr> encontrados = isp.buscarArticulosSolr(busqueda, proveedor);

		return isp.getDatosArticulosBuscados(encontrados);
	}

	@Override
	public Articulo obtenerArticulo(int idArticulo) throws Excepciones {
		return FabricaPersistencia.getStockPersistencia().obtenerArticulo(idArticulo);
	}

	@Override
	public List<Articulo> obtenerArticulosDelProveedor(long idProveedor)
			throws Excepciones {
		return FabricaPersistencia.getStockPersistencia().
				obtenerArticulosDelProveedor(idProveedor);
		
	}

	@Override
	public void modificarPreciodeArticulos(Map<Long, BigDecimal> preciosModificados)
			throws Excepciones {
		FabricaPersistencia.getStockPersistencia().
			modificarPreciosDeArticulo(preciosModificados);
		
	}

	@Override
	public List<DTVencimiento> articulosQueSeVencenEnPeriodo(Date desde,
			Date hasta) throws Excepciones {
		return FabricaPersistencia.getStockPersistencia().articulosQueSeVencenEnPeriodo(desde, hasta);
	}

	@Override
	public void modificarVencimientosDeArticulos(Map<Long, Date> cambios) throws Excepciones {
		FabricaPersistencia.getStockPersistencia().modificarVencimientosDeArticulos(cambios);
		FabricaPersistencia.getStockPersistencia().deltaImportSolr();
		
	}
	
}
