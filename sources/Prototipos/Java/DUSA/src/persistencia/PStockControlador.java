package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import model.Articulo;
import model.LineaPedido;
import interfaces.IStockPersistencia;

public class PStockControlador implements IStockPersistencia {

	@Override
	public void persistirArticulo(Articulo articulo) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Articulo> buscarArticulo(String descripcion){
		
		return null;
		
	}

	@Override
	public Date getUltimoPedido() throws Exception {
		// TODO Auto-generated method stub
		//Codigo en la base para obtener el ultimo pedido
				// TODO Auto-generated method stub
						Date ret = null;
						Connection c = null;
						Statement stmt = null;
						String query = "SELECT order_date FROM orders_dusa" +
										" GROUP BY order_date" +
										" ORDER BY order_date DESC" +
										" LIMIT 1;";
						try {
							Class.forName("org.postgresql.Driver");
							c = DriverManager
									.getConnection("jdbc:postgresql://localhost:5432/dusa",
											"postgres", "root");
							c.setAutoCommit(false);
							stmt = c.createStatement();
							ResultSet rs = stmt.executeQuery(query);
							//Obtengo la fecha
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
	public List<LineaPedido> obtenerArticulosDesde(Date fechaDesde) {
		// TODO Auto-generated method stub
		return null;
	}

}
