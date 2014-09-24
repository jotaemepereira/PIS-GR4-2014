package persistencia;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import model.Proveedor;
import interfaces.IProveedoresPersistencia;

public class PProveedoresControlador implements IProveedoresPersistencia {

	@Override
	public void persistirProveedor(Proveedor proveedor) {
		PreparedStatement stmt = null;
		
		String query = "INSERT INTO suppliers " +
						"(supplier_id, rut, companyname, phone, supplier_address, comercialname, last_modified, status) " +
						" VALUES " +
						" (?, ?, ?, ?, ?, ?, ?, ?);";
		String query_id =	"SELECT MAX(supplier_id) as id FROM suppliers";
		
		try {
			Connection c = Conexion.getConnection();
			
			Statement stmt_id = c.createStatement();
			
			// Traigo el ultimo id insertado en la bd y le sumo uno
			ResultSet rs = stmt_id.executeQuery(query_id);
			int id = 1;
			while(rs.next()){
				id = rs.getInt("id") + 1;
			}
			System.out.print("id: " + id + "\n");
			Date hoy = new Date();
			Long time = hoy.getTime();
			
			// Seteo los datos a insertar en la bd
			stmt = c.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.setString(2, proveedor.getRUT());
			stmt.setString(3, proveedor.getRazonSocial());
			stmt.setString(4, proveedor.getTelefono());
			stmt.setString(5, proveedor.getDireccion());
			stmt.setString(6, proveedor.getNombreComercial());
			stmt.setDate(7, new java.sql.Date(time));
			stmt.setBoolean(8, true);
			
			stmt.executeUpdate();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
	}

	@Override
	public boolean existeProveedor(String nombreComercial) {
		int cant = 0;
		PreparedStatement stmt = null;
		String query = "SELECT COUNT(*) AS cant FROM suppliers " +
						"WHERE comercialname = ?";
		try {
			Connection c = Conexion.getConnection();
			
			stmt = c.prepareStatement(query);
			stmt.setString(1, nombreComercial);
			ResultSet rs = stmt.executeQuery();
			//Obtengo la cantidad de proveedores con ese rut
			while(rs.next()){
				cant = rs.getInt("cant");
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
		return (cant > 0);
	}
	
}
