package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import model.Articulo;
import model.LineaPedido;
import interfaces.IStockPersistencia;

public class PStockControlador implements IStockPersistencia {

	@Override
	public void persistirArticulo(Articulo articulo) {
		try {
			Connection c = Conexion.getConnection();
			
			// TODO Guardar los proveedores
			
			// TODO Guardar las presentaciones
			
			// TODO Guardar las drogas
			
			// TODO Guardar las acciones terapéuticas
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Articulo> buscarArticulo(String descripcion){

		return null;

	}

	@Override
	public Date getUltimoPedido() throws Exception {
		// TODO Auto-generated method stub
		//Codigo en la base para obtener el ultimo pedido
		// TODO Auto-generated method stub
		Connection c = Conexion.getConnection();
		Date ret = null;
		Statement stmt = null;
		String query = "SELECT order_date FROM orders_dusa" +
				" GROUP BY order_date" +
				" ORDER BY order_date DESC" +
				" LIMIT 1;";
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ret = rs.getDate("order_date");
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			throw e;
		}
		return ret;
	}

	@Override
	public List<LineaPedido> obtenerArticulosDesde(Date fechaDesde) throws Exception {
		// TODO Auto-generated method stub
		List<LineaPedido> ret = new ArrayList<LineaPedido>();
		Connection c = Conexion.getConnection();
		Statement stmt = null;
		String query = "SELECT sd.product_id, p.product_number, SUM(sd.quantity)" +
				" FROM sales s" +
				" JOIN sale_details sd ON sd.sale_id = s.sale_id" +
				" JOIN products p ON p.product_id = sd.product_id" +
				" WHERE s.sale_date > " + fechaDesde.toString() +
				" GROUP BY sd.product_id, p.product_number;";
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while ( rs.next() ) {
				int numeroArticulo = rs.getInt("product_id");
				int idArticulo = rs.getInt("product_name");
				int cantidad = rs.getInt("cantidad");
				LineaPedido lp = new LineaPedido(numeroArticulo, idArticulo, cantidad);
				ret.add(lp);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			throw e;
		}
		return ret;
	}

}
