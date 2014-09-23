package controladores;

import interfaces.IStock;
import interfaces.IStockPersistencia;
import model.Articulo;
import model.LineaPedido;

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
	public void altaArticulo(Articulo articulo) {
		// TODO Auto-generated method stub

	}

	public List<Articulo> buscarArticulo(String descripcion){

		PStockControlador ps = (PStockControlador) FabricaPersistencia.getIStockPersistencia();

		return ps.buscarArticulo(descripcion);

	}

	public Articulo obtenerArticulo(String id){

		return null;

	}

	public List<DTLineaPedido> pedidoPorVentas() {
		IStockPersistencia isp = FabricaPersistencia.getIStockPersistencia();
		
		Date fechaPedido;
		List<LineaPedido> articulos = new ArrayList<LineaPedido>();
		List<DTLineaPedido> ret = new ArrayList<DTLineaPedido>();

		try {
			fechaPedido = isp.getUltimoPedido();
			articulos = isp.obtenerArticulosDesde(fechaPedido); 
		}
		catch (Exception e) {
			//Que hago con las exceptions?
		}
		
		if (!articulos.isEmpty())
			for (LineaPedido p : articulos) {
				DTLineaPedido dt = new DTLineaPedido();
				dt.setCantidad(p.getCantidad());
				dt.setIdArticulo(p.getIdArticulo());
				dt.setNumeroArticulo(p.getNumeroArticulo());
				ret.add(dt);
			}
		return ret;
		
	}
}
