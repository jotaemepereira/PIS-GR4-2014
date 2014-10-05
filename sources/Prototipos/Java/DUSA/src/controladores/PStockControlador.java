package controladores;
import java.sql.Date;
import java.util.List;

import datatypes.DTLineaPedido;
import model.Articulo;
import model.LineaPedido;
import model.Pedido;
import interfaces.IStock;

/**  
* @author Santiago
*
*/

public class PStockControlador implements IStock{
	
	@Override
	public void altaArticulo(Articulo articulo) throws Excepciones {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<DTLineaPedido> pedidoPorVentas() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Pedido generarPedido01UltimoPedido() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void generarPedido(Pedido p) {
		// TODO Auto-generated method stub
		
	}
}
