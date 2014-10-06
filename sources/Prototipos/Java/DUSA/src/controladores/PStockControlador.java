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

//Deprecated
//	@Override
//	public List<LineaPedido> obtenerArticulosDesde(Date fechaDesde) throws Excepciones {
//		// TODO Auto-generated method stub
//		List<LineaPedido> ret = new ArrayList<LineaPedido>();
//		
//		Statement stmt = null;
//		String query = "SELECT sd.product_id, p.product_number, SUM(sd.quantity)" +
//				" FROM sales s" +
//				" JOIN sale_details sd ON sd.sale_id = s.sale_id" +
//				" JOIN products p ON p.product_id = sd.product_id" +
//				" WHERE s.sale_date > " + fechaDesde.toString() +
//				" GROUP BY sd.product_id, p.product_number;";
//		try {
//			Connection c = Conexion.getConnection();
//			c.setAutoCommit(false);
//			stmt = c.createStatement();
//			ResultSet rs = stmt.executeQuery(query);
//			while ( rs.next() ) {
//				int numeroArticulo = rs.getInt("product_id");
//				int idArticulo = rs.getInt("product_name");
//				int cantidad = rs.getInt("cantidad");
//				LineaPedido lp = new LineaPedido(numeroArticulo, idArticulo, cantidad);
//				ret.add(lp);
//			}
//			rs.close();
//			stmt.close();
//			c.close();
//		} catch ( Exception e ) {
//			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
//		}
//		return ret;
//	}
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
