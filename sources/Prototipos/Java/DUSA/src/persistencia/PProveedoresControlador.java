package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Proveedor;
import interfaces.IProveedoresPersistencia;

public class PProveedoresControlador implements IProveedoresPersistencia {

	@Override
	public void persistirProveedor(Proveedor proveedor) {
		Connection c = null;
		PreparedStatement stmt = null;
		String query = "INSERT INTO SUPPLIERS " +
						"(RUC, COMPANYNAME, PHONE, SUPPLIER_ADDRESS, COMERCIALNAME " +
						" VALUES " +
						" (?, ?, ?, ?, ?);";
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://localhost:5432/dusa",
							"postgres", "root");
			c.setAutoCommit(false);
			
			stmt = c.prepareStatement(query);
			stmt.setInt(1, proveedor.getRUT());
			stmt.setString(2, proveedor.getRazonSocial());
			stmt.setInt(3, proveedor.getTelefono());
			stmt.setString(4, proveedor.getDireccion());
			stmt.setString(5, proveedor.getNombreComercial());
			
			stmt.executeUpdate();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
	}

	@Override
	public boolean existeProveedor(int RUT) {
		int cant = 0;
		Connection c = null;
		PreparedStatement stmt = null;
		String query = "SELECT COUNT(*) AS cant FROM SUPPLIERS " +
						"WHERE RUT = ?";
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://localhost:5432/dusa",
							"postgres", "root");
			c.setAutoCommit(false);
			stmt = c.prepareStatement(query);
			stmt.setInt(1, RUT);
			ResultSet rs = stmt.executeQuery(query);
			//Obtengo la cantidad de proveedores con ese rut
			cant = rs.getInt("cant");
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
