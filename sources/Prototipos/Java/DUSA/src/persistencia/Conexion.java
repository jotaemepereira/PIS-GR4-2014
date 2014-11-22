/**
 * Realiza la conexion a la base de datos
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.naming.NamingException;

public class Conexion {
	
	private static String driverParam = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("DRIVER");
	private static String dbuserParam = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("DBUSER");
	private static String dbpwdParam = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("DBPWD");
	private static String dbconnstringParam =FacesContext.getCurrentInstance().getExternalContext().getInitParameter("CONNSTRING");
	
	public static Connection getConnection() throws NamingException, SQLException {
		
		try {
			Class.forName(driverParam);//FacesContext.getCurrentInstance().getExternalContext().getInitParameter("DRIVER")
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        return DriverManager.getConnection(dbconnstringParam, dbuserParam, dbpwdParam);

	}

}