package controladores;

import interfaces.IPredictor;
import interfaces.ISeleccionador;
import interfaces.IServicio;
import interfaces.IStock;
import interfaces.IStockPersistencia;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.GeneradorPedido;
import model.LineaPedido;
import model.Pedido;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
import model.Articulo;
import controladores.FabricaPersistencia;

public class StockControlador implements IStock {

	@Override
	public void altaArticulo(Articulo articulo) throws Excepciones {
		
		// Me fijo si ya existe otro articulo con la misma descripcion
		if((articulo.getDescripcion() != "") && (FabricaPersistencia.getStockPersistencia().existeArticulo(articulo.getDescripcion()))){
			throw(new Excepciones(Excepciones.MENSAJE_ART_DUPLICADO, Excepciones.ERROR_DATOS));
		}
		FabricaPersistencia.getStockPersistencia().persistirArticulo(articulo);
	}

	public List<Articulo> buscarArticulo(String descripcion){

		PStockControlador ps = (PStockControlador) FabricaPersistencia.getStockPersistencia();
		return ps.buscarArticulo(descripcion);

	}

	public Articulo obtenerArticulo(Long id){

		Articulo articulo = null;
		
		try {
			
			articulo = FabricaPersistencia.getStockPersistencia().obtenerArticuloConId(id.longValue());
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
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior() throws Excepciones {
		
		IStockPersistencia sp = FabricaPersistencia.getStockPersistencia();
		Date ultimoPedido = sp.obtenerFechaUltimoPedido();
		
		if (ultimoPedido == null){
			//Caso base para el pedido: Se toma las ventas realizadas en el dia de hoy.
			ultimoPedido = new Date(Calendar.getInstance().getTimeInMillis());
		}
		SeleccionarArticulosDesde seleccionarDesde = new SeleccionarArticulosDesde(ultimoPedido);
		PredecirCantidadDesde predecirDesde = new PredecirCantidadDesde(ultimoPedido);
		
		GeneradorPedido gr = new GeneradorPedido(seleccionarDesde, predecirDesde);
		
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
				// TODO: hardcodear id de DUSA
				 DTProveedor dtProveedor = articulo.getProveedores().get(0);
				 dtlPedido.setNumeroArticulo(dtProveedor.getCodigoIdentificador());
				 
				 lPedidos.add(dtlPedido);
			}
		}
		
		return lPedidos;
	}
	
	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda) throws Excepciones{
		List<DTBusquedaArticulo> articulos = new ArrayList<DTBusquedaArticulo>();
		List<DTBusquedaArticuloSolr> encontrados = FabricaPersistencia.getStockPersistencia().buscarArticulosSolr(busqueda);
		
		Iterator<DTBusquedaArticuloSolr> it = encontrados.iterator();
		while (it.hasNext()) {
			DTBusquedaArticuloSolr dtBusquedaArticulo = (DTBusquedaArticuloSolr) it
					.next();
			
			DTBusquedaArticulo articulo = new DTBusquedaArticulo(dtBusquedaArticulo);
			FabricaPersistencia.getStockPersistencia().buscarArticulosId(articulo);

			articulos.add(articulo);
		}
		
		return articulos;
	}

	public List<DTLineaPedido> generarPedidoEnBaseAHistorico(int diasAPredecir) throws Excepciones {
		
		ISeleccionador st = new SeleccionarTodos();
		IPredictor pr = new PredecirEnBaseAHistorico(diasAPredecir);
		GeneradorPedido gp = new GeneradorPedido(st,pr);
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
				// TODO: hardcodear id de DUSA
				DTProveedor dtProveedor = articulo.getProveedores().get(0);
				dtlPedido.setNumeroArticulo(dtProveedor.getCodigoIdentificador());
				
				lPedidos.add(dtlPedido);
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
	public void realizarPedido(Pedido p) throws Excepciones{
		
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
		return FabricaPersistencia.getStockPersistencia().obtenerAccionesTerapeuticas();
	}

	@Override
	public List<DTVenta> buscarArticulosVenta(String busqueda)
			throws Excepciones {
		List<DTVenta> articulos = new ArrayList<DTVenta>();
		List<DTBusquedaArticuloSolr> lista = FabricaPersistencia.getStockPersistencia().buscarArticulosSolr(busqueda);
		
		Iterator<DTBusquedaArticuloSolr> it = lista.iterator();
		
		while (it.hasNext()) {
			DTBusquedaArticuloSolr articuloB = (DTBusquedaArticuloSolr) it.next();
			DTVenta articuloV = FabricaPersistencia.getStockPersistencia().getDatosArticuloVenta(articuloB.getIdArticulo());
			articuloV.setDescripcion(articuloB.getDescripcion());
			articuloV.setProductId(articuloB.getIdArticulo());
			articuloV.setBarcode(articuloB.getCodigoBarras());
			articuloV.setPresentacion(articuloB.getPresentacion());
			articuloV.setPrincipioActivo(articuloB.getDroga());
			articuloV.setLaboratorio(articuloB.getMarca());
			articuloV.setDescuento1(new BigDecimal(25));
			articuloV.setDescuento2(new BigDecimal(40));
			articulos.add(articuloV);
		}
		
		return articulos;
	}
	
	
}
