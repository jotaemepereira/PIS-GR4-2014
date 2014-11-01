package controladores;

import interfaces.IPredictor;
import interfaces.ISeleccionador;
import interfaces.IServicio;
import interfaces.IStock;
import interfaces.IStockPersistencia;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Enumerados;
import model.GeneradorPedido;
import model.LineaPedido;
import model.Pedido;
import model.TipoIva;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import persistencia.PStockControlador;
import datatypes.DTBusquedaArticuloSolr;
import datatypes.DTBusquedaArticulo;
import datatypes.DTLineaPedido;
import datatypes.DTProduct;
import datatypes.DTProveedor;
import datatypes.DTVenta;
import controladores.FabricaPersistencia;

public class StockControlador implements IStock {

	@Override
	public void altaArticulo(Articulo articulo) throws Excepciones {

		// Me fijo si ya existe otro articulo con la misma descripcion
		if ((articulo.getDescripcion() != "")
				&& (FabricaPersistencia.getStockPersistencia()
						.existeArticulo(articulo.getDescripcion()))) {
			throw (new Excepciones(Excepciones.MENSAJE_ART_DUPLICADO,
					Excepciones.ERROR_DATOS));
		}
		FabricaPersistencia.getStockPersistencia().persistirArticulo(articulo);
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
			// TODO: handle exception
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

		Pedido pedidoGenerado = gr.generar();

		List<DTLineaPedido> lPedidos = new ArrayList<DTLineaPedido>();

		for (LineaPedido lPedido : pedidoGenerado.getLineas()) {

			DTLineaPedido dtlPedido = new DTLineaPedido();

			Articulo articulo = this.obtenerArticulo(lPedido.getIdArticulo());

			if (articulo != null) {

				dtlPedido.setIdArticulo(lPedido.getIdArticulo());
				dtlPedido.setDescripcionArticulo(articulo.getDescripcion());
				dtlPedido.setStockMinimo(articulo.getStockMinimo());
				dtlPedido.setPrecioUnitario(articulo.getPrecioUnitario());
				dtlPedido.setCantidad(lPedido.getCantidad());
				dtlPedido.setPrecioPonderado(articulo.getCostoPromedio());
				dtlPedido.setSubtotal(lPedido.getCantidad()
						* articulo.getPrecioUnitario().floatValue());

				DTProveedor dtProveedor = articulo.getProveedores().get(
						Enumerados.infoDUSA.proveedorID);

				if (dtProveedor != null) {
					// Preventivo control si no es de DUSA no se ingresa
					dtlPedido.setNumeroArticulo(dtProveedor
							.getCodigoIdentificador());
					lPedidos.add(dtlPedido);
				}
			}
		}

		return lPedidos;
	}

	private List<DTBusquedaArticulo> getDatosArticulosBuscados(
			List<DTBusquedaArticuloSolr> encontrados) throws Excepciones {
		List<DTBusquedaArticulo> articulos = new ArrayList<DTBusquedaArticulo>();

		Iterator<DTBusquedaArticuloSolr> it = encontrados.iterator();
		while (it.hasNext()) {
			DTBusquedaArticuloSolr dtBusquedaArticulo = (DTBusquedaArticuloSolr) it
					.next();

			DTBusquedaArticulo articulo = new DTBusquedaArticulo(
					dtBusquedaArticulo);
			FabricaPersistencia.getStockPersistencia().buscarArticulosId(
					articulo);

			articulos.add(articulo);
		}

		return articulos;
	}

	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda)
			throws Excepciones {
		List<DTBusquedaArticuloSolr> encontrados = FabricaPersistencia
				.getStockPersistencia().buscarArticulosSolr(busqueda);

		return getDatosArticulosBuscados(encontrados);
	}

	@Override
	public List<DTLineaPedido> generarPedidoEnBaseAHistorico(int diasAPredecir)
			throws Excepciones {

		ISeleccionador st = new SeleccionarTodos();
		IPredictor pr = new PredecirEnBaseAHistorico(diasAPredecir);
		GeneradorPedido gp = new GeneradorPedido(st, pr);
		Pedido pedidoGenerado = gp.generar();

		List<DTLineaPedido> lPedidos = new ArrayList<DTLineaPedido>();

		for (LineaPedido lPedido : pedidoGenerado.getLineas()) {

			DTLineaPedido dtlPedido = new DTLineaPedido();
			Articulo articulo = this.obtenerArticulo(lPedido.getIdArticulo());

			if (articulo != null) {

				dtlPedido.setIdArticulo(lPedido.getIdArticulo());
				dtlPedido.setDescripcionArticulo(articulo.getDescripcion());
				dtlPedido.setStockMinimo(articulo.getStockMinimo());
				dtlPedido.setPrecioUnitario(articulo.getPrecioUnitario());
				dtlPedido.setCantidad(lPedido.getCantidad());
				dtlPedido.setPrecioPonderado(articulo.getCostoPromedio());
				dtlPedido.setSubtotal(lPedido.getCantidad()
						* articulo.getPrecioUnitario().floatValue());

				DTProveedor dtProveedor = articulo.getProveedores().get(
						Enumerados.infoDUSA.proveedorID);
				if (dtProveedor != null) {
					// Preventivo control si no es de DUSA no se ingresa
					dtlPedido.setNumeroArticulo(dtProveedor
							.getCodigoIdentificador());
					lPedidos.add(dtlPedido);
				}
			}
		}

		return lPedidos;

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
		// grabarTiposIVA();
		return FabricaPersistencia.getStockPersistencia()
				.obtenerAccionesTerapeuticas();
	}

	private void grabarTiposIVA() {
		try {
			List<TipoIva> lista = FabricaServicios.getIServicios()
					.obtenerTiposIva();
			FabricaPersistencia.getStockPersistencia().persistirTiposIva(lista);
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<DTVenta> buscarArticulosVenta(String busqueda)
			throws Excepciones {
		List<DTVenta> articulos = new ArrayList<DTVenta>();
		List<DTBusquedaArticuloSolr> lista = FabricaPersistencia
				.getStockPersistencia().buscarArticulosSolr(busqueda);

		Iterator<DTBusquedaArticuloSolr> it = lista.iterator();

		while (it.hasNext()) {
			DTBusquedaArticuloSolr articuloB = (DTBusquedaArticuloSolr) it
					.next();
			DTVenta articuloV = FabricaPersistencia.getStockPersistencia()
					.getDatosArticuloVenta(articuloB.getIdArticulo());
			articuloV.setDescripcion(articuloB.getDescripcion());
			articuloV.setProductId(articuloB.getIdArticulo());
			articuloV.setBarcode(articuloB.getCodigoBarras());
			articuloV.setPresentacion(articuloB.getPresentacion());
			articuloV.setPrincipioActivo(articuloB.getDroga());
			articuloV.setLaboratorio(articuloB.getMarca());
			articulos.add(articuloV);
		}

		return articulos;
	}

	public void actualizarStock() throws Excepciones {
		System.out.println("actualizarStock controlador");
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_MONTH, -36);
		calendario.add(Calendar.DAY_OF_WEEK, -2);
		java.util.Date fecha = calendario.getTime();
		List<Articulo> articulos = FabricaServicios.getIServicios()
				.obtenerActualizacionDeStock(fecha);

		// Se tendrian que recorrer todos los articulos y checkear si el
		// artículo ya existe o no
		// En caso de que exista, se actualiza el precio y el estado del
		// artículo
		// Caso contrario, el artículo es nuevo y se almacena en la base de
		// datos.

		IStockPersistencia sp = FabricaPersistencia.getStockPersistencia();
		for (Articulo a : articulos) {
			sp.persistirArticulo(a);
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
	public void modificarStock(long[] idsArticulo, long[] nuevosValores) throws Excepciones{

		FabricaPersistencia.getStockPersistencia().modificarStock(idsArticulo,
				nuevosValores);
	}

	@Override
	public void modificarArticulo(Articulo articulo) throws Excepciones {
		FabricaPersistencia.getStockPersistencia().modificarArticulo(articulo);

	}

	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda,
			int proveedor) throws Excepciones {
		List<DTBusquedaArticuloSolr> encontrados = FabricaPersistencia
				.getStockPersistencia()
				.buscarArticulosSolr(busqueda, proveedor);

		return getDatosArticulosBuscados(encontrados);
	}

}
