/**
 * Realiza la conexion a la base de datos
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.naming.NamingException;

/**
 * Se probo integrar un pool de conexion, pero se detectaron problemas intermitentes en el uso del sistema. 
 * Se uso BasicDataSource de apache Tomcat.  
 * @author Guille
 */
public class Conexion {
	/**
	 * Se utilizaron atributos de clase para obtener los atributos, 
	 * dado que hilo generado por el scheduler no obtiene el FacesContext.
	 */
	private static String driverParam = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("DRIVER");
	private static String dbuserParam = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("DBUSER");
	private static String dbpwdParam = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("DBPWD");
	private static String dbconnstringParam =FacesContext.getCurrentInstance().getExternalContext().getInitParameter("CONNSTRING");
	
	public static Connection getConnection() throws NamingException, SQLException {
		
		try {
			Class.forName(driverParam);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        return DriverManager.getConnection(dbconnstringParam, dbuserParam, dbpwdParam);

	}

}