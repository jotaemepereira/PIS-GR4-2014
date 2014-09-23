package controladores;

import interfaces.IStock;
import interfaces.IStockPersistencia;
import model.Articulo;
import model.LineaPedido;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import persistencia.PStockControlador;
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

	public void pedidoPorVentas() {
		IStockPersistencia isp = FabricaPersistencia.getIStockPersistencia();
		Date fechaPedido;
		List<LineaPedido> articulos;

		try {
			//Obtengo la fecha del ultimo pedido
			fechaPedido = isp.getUltimoPedido();
			//Obtengo todas las ventas que se hicieron desde el ultimo pedido
			articulos = isp.obtenerArticulosDesde(fechaPedido); 
		}
		catch (Exception e) {
		}

	}
}
