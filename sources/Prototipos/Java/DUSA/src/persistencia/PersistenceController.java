package persistencia;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.Articulo;
import model.LineaPedido;
import model.LineaVenta;
import model.Pedido;
import model.Venta;
import interfaces.IPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.faces.context.FacesContext;

public class PersistenceController implements IPersistence {

	public static void connectToDatabase(String args[]) {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://localhost:5432/dusa",
							"postgres", "root");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	@Override
	public List<Articulo> getArticulos(String description) {
		List<Articulo> ret = new ArrayList<Articulo>();
		for (Articulo p : Database.getInstance().getProducts()) {
			if (p.getDescripcion().toLowerCase()
					.contains(description.toLowerCase())) {
				ret.add(p);
			}
		}
		return ret;
	}

	@Override
	public Articulo getArticulo(String barcode){
		for (Articulo p : Database.getInstance().getProducts()) {
			if (p.getCodigoBarras().toLowerCase()
					.equals(barcode.toLowerCase())) {
				return p;
			}
		}
		return null;
	}

	@Override
	public void insertSale(Venta sale) {
		Database.getInstance().getSales().add(sale);
	}

	public Date getUltimoPedido() {
		//Codigo en la base para obtener el ultimo pedido
		
		return new Date(1410912000);
	}

	@Override
	public List<LineaPedido> obtenerArticulosDesde(Date fechaPedido) {
		// TODO Auto-generated method stub
		List<LineaPedido> ret = new ArrayList<LineaPedido>();
		//Consulta para obtener todos los articulos vendidos desde una fecha
		  {
		       Connection c = null;
		       Statement stmt = null;
		       String query = "SELECT sd.product_id, p.product_number, SUM(sd.quantity)" +
		    		   		  " FROM sales s" +
		    		   		  " JOIN sale_details sd ON sd.sale_id = s.sale_id" +
		    		   		  " JOIN products p ON p.product_id = sd.product_id" +
		    		   		  " WHERE s.sale_date > " + fechaPedido.toString() +
		       				  " GROUP BY sd.product_id, p.product_number;";
		       
		       try {
		       Class.forName("org.postgresql.Driver");
		         c = DriverManager
		            .getConnection("jdbc:postgresql://localhost:5432/dusa",
		            "postgres", "root");
		         c.setAutoCommit(false);
		         System.out.println("Opened database successfully");

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
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		       }
		       System.out.println("Operation done successfully");
		     }
		  return ret;
	}



}
