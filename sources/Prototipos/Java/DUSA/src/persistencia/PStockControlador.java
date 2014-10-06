package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import controladores.Excepciones;
import model.Articulo;
import model.Enumerados;
import model.LineaPedido;
import model.Pedido;
import interfaces.IStockPersistencia;

public class PStockControlador implements IStockPersistencia {

	@Override
	public void persistirArticulo(Articulo articulo) throws Excepciones{
		PreparedStatement stmt = null;
		
		String query = "INSERT INTO PRODUCTS " +
						"(PRODUCT_TYPE, DESCRIPTION, KEY1, KEY2, KEY3, IS_PSYCHOTROPIC, IS_NARCOTIC, SALE_CODE, AUTHORIZATION_TYPE,"
						+ " UNIT_PRICE, SALE_PRICE, LIST_COST, OFFER_COST, LAST_COST, AVG_COST, TAX_TYPE, BARCODE, LAST_PRICE_DATE"
						+ ", NEAREST_DUE_DATE, STOCK, MINIMUM_STOCK, LAST_MODIFIED, STATUS) " +
						" VALUES " +
						" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, LOCALTIMESTAMP, ?, ?, ?, LOCALTIMESTAMP, ?);";
		
		try {
			Connection c = Conexion.getConnection();
			
			// Seteo los datos a insertar en la bd
			stmt = c.prepareStatement(query);
			stmt.setString(1, String.valueOf(articulo.getTipoArticulo()));//Null
			stmt.setString(2, articulo.getDescripcion());//Not Null
			stmt.setString(3, articulo.getClave1());//Null
			stmt.setString(4, articulo.getClave2());//Null
			stmt.setString(5, articulo.getClave3());//Null
			stmt.setBoolean(6, articulo.isEsPsicofarmaco());//Not Null
			stmt.setBoolean(7, articulo.isEsEstupefaciente());//Not Null
			stmt.setString(8, String.valueOf(articulo.getCodigoVenta()));//Null
			stmt.setString(9, String.valueOf(Enumerados.habilitado.HABILITADO));//Not Null
			stmt.setBigDecimal(10, articulo.getPrecioUnitario());//Not Null
			stmt.setBigDecimal(11, articulo.getPrecioVenta());//Not Null
			stmt.setBigDecimal(12, articulo.getCostoLista());//Not Null
			stmt.setBigDecimal(13, articulo.getCostoOferta());//Null
			stmt.setBigDecimal(14, articulo.getUltimoCosto());//Not Null
			stmt.setBigDecimal(15, articulo.getCostoPromedio());//Null
			stmt.setInt(16, articulo.getTipoIva());//Null
			stmt.setString(17, articulo.getCodigoBarras());//Null
			//stmt.setDate(18, new java.sql.Date(articulo.getFechaUltimoPrecio().getTime()));//Not Null
			stmt.setNull(18, java.sql.Types.TIMESTAMP);//Null Vencimiento Más Cercano
			stmt.setInt(19, articulo.getStock());//Not Null
			stmt.setInt(20, articulo.getStockMinimo());//Null
			stmt.setBoolean(21, true);//Not Null
			
			stmt.executeUpdate();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
	}

	public List<Articulo> buscarArticulo(String descripcion){

		return null;

	}

	@Override
	public Date getUltimoPedido() throws Excepciones {
		// TODO Auto-generated method stub
		//Codigo en la base para obtener el ultimo pedido
		// TODO Auto-generated method stub
		
		Date ret = null;
		Statement stmt = null;
		String query = "SELECT order_date FROM orders_dusa" +
				" GROUP BY order_date" +
				" ORDER BY order_date DESC" +
				" LIMIT 1;";
		try {
			Connection c = Conexion.getConnection();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ret = rs.getDate("order_date");
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
		return ret;
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
	public void persistirPedido(Pedido p) {
		// TODO Auto-generated method stub
		
	}

}
