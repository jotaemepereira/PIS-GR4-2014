package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import controladores.Excepciones;
import model.Proveedor;
import interfaces.IProveedoresPersistencia;

public class PProveedoresControlador implements IProveedoresPersistencia {

	@Override
	public void persistirProveedor(Proveedor proveedor) throws Excepciones {
		PreparedStatement stmt = null;
		
		String query = "INSERT INTO suppliers " +
						"(document_type, document, companyname, phone, supplier_address, comercialname, last_modified, status) " +
						" VALUES " +
						" (?, ?, ?, ?, ?, ?, ?, ?);";
		
		try {
			Connection c = Conexion.getConnection();
			
			Date hoy = new Date();
			Long time = hoy.getTime();
			
			// Seteo los datos a insertar en la bd
			stmt = c.prepareStatement(query);
			
			stmt.setString(1, proveedor.getTipoDocumento());
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
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
	}

	@Override
	public boolean existeProveedorNombreComercial(String nombreComercial) throws Excepciones{
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
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		return (cant > 0);
	}
	
	@Override
	public boolean existeProveedorRUT(String RUT) throws Excepciones{
		int cant = 0;
		PreparedStatement stmt = null;
		String query = "SELECT COUNT(*) AS cant FROM suppliers " +
						"WHERE document = ?";
		try {
			Connection c = Conexion.getConnection();
			
			stmt = c.prepareStatement(query);
			stmt.setString(1, RUT);
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
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		return (cant > 0);
	}
	
}
