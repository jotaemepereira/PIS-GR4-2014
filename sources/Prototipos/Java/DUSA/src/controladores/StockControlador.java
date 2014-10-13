package controladores;

import interfaces.IPredictor;
import interfaces.ISeleccionador;
import interfaces.IServicio;
import interfaces.IStock;
import interfaces.IStockPersistencia;
import model.Articulo;
import model.GeneradorPedido;
import model.LineaPedido;
import model.Pedido;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import persistencia.PStockControlador;
import datatypes.DTLineaPedido;
import datatypes.DTProduct;
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

		return null;

	}
	
	
//Deprecated
//	public List<DTLineaPedido> pedidoPorVentas() {
//		IStockPersistencia isp = FabricaPersistencia.getIStockPersistencia();
//		
//		Date fechaPedido;
//		List<LineaPedido> articulos = new ArrayList<LineaPedido>();
//		List<DTLineaPedido> ret = new ArrayList<DTLineaPedido>();
//
//		try {
//			fechaPedido = isp.getUltimoPedido();
//			articulos = isp.obtenerArticulosDesde(fechaPedido); 
//		}
//		catch (Exception e) {
//			//Que hago con las exceptions?
//		}
//		
//		if (!articulos.isEmpty())
//			for (LineaPedido p : articulos) {
//				DTLineaPedido dt = new DTLineaPedido();
//				dt.setCantidad(p.getCantidad());
//				dt.setIdArticulo(p.getIdArticulo());
//				dt.setNumeroArticulo(p.getNumeroArticulo());
//				ret.add(dt);
//			}
//		return ret;
//		
//	}
	
	public void generarPedido(Pedido p){
		
	}
	
	/**
	 * 
	 * @author Guille
	 */
	@Override
	public Pedido generarPedidoEnBaseAPedidoAnterior() throws Excepciones {
		
		IStockPersistencia sp = FabricaPersistencia.getStockPersistencia();
		Date ultimoPedido = sp.obtenerFechaUltimoPedido();
		SeleccionarArticulosDesde seleccionarDesde = new SeleccionarArticulosDesde(ultimoPedido);
		PredecirCantidadDesde predecirDesde = new PredecirCantidadDesde(ultimoPedido);
		
		GeneradorPedido gr = new GeneradorPedido(seleccionarDesde, predecirDesde);
		
		Pedido pedidoGenerado = gr.generar();
		
		for (LineaPedido lPedido : pedidoGenerado.getLineas()) {
			
			DTLineaPedido dtlPedido = new DTLineaPedido();
			
			 Articulo articulo = this.obtenerArticulo(lPedido.getIdArticulo());
			 
			 dtlPedido.setIdArticulo(lPedido.getIdArticulo());
			 dtlPedido.setDescripcionArticulo(articulo.getDescripcion());
			 dtlPedido.setStockMinimo(articulo.getStockMinimo());
			 dtlPedido.setPrecioUnitario(articulo.getPrecioUnitario());
			 dtlPedido.setCantidad(lPedido.getCantidad());
			 
		}
		pedidoGenerado.getLineas();
		return null;
	}
	
	@Override
	public List<Articulo> buscarArticulos(String busqueda) throws Excepciones{
		return FabricaPersistencia.getStockPersistencia().buscarArticulos(busqueda);
	}

	public Pedido generarPedidoEnBaseAHistorico(int diasAPredecir) {
		
		ISeleccionador st = new SeleccionarTodos();
		IPredictor pr = (IPredictor) new PredecirEnBaseAHistorico(diasAPredecir);
		GeneradorPedido gp = new GeneradorPedido(st,pr);
		Pedido pedidoGenerado = null;
		try {
			pedidoGenerado = gp.generar();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//Generar las excepciones personalizadas para "mostrar" en pantalla
		}
		return pedidoGenerado;

	}
	/**
	 * 
	 * @author Santiago
	 */
	public void realizarPedido(Pedido p){
		IServicio is = FabricaServicios.getIServicios();
		is.realizarPedido(p);
		IStockPersistencia isp = FabricaPersistencia.getStockPersistencia();
		isp.persistirPedido(p);
		
	}
	
	
}
