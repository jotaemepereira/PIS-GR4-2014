package controladores;

import java.sql.Date;
import java.util.List;

import interfaces.ICompras;
import model.LineaPedido;
import model.LineaVenta;

public class ComprasControlador implements ICompras {
	
	public void pedidoPorVentas() {
		Date fechaPedido;
		List<LineaPedido> articulos;
		
		//Obtengo la fecha del ultimo pedido
		//fechaPedido = ip.getUltimoPedido();
		//Obtengo todas las ventas que se hicieron desde el ultimo pedido
		//articulos = ip.obtenerArticulosDesde(fechaPedido);
		}
}
