package persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class Conexion {
	private static BasicDataSource basicDataSource;

	/**
	 * Devuelve una conexion disponible del pool de conexiones. 
	 * Esta el liberada y devuelta al pool cuando se invoca su operacion close().
	 * 
	 * 
	 * @return Una conexion disonible del pool
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws NamingException,
			SQLException {

		if (basicDataSource == null) {
			String driverClassName = FacesContext.getCurrentInstance()
					.getExternalContext().getInitParameter("DRIVER");
			String user = FacesContext.getCurrentInstance()
					.getExternalContext().getInitParameter("DBUSER");
			String password = FacesContext.getCurrentInstance()
					.getExternalContext().getInitParameter("DBPWD");
			String connection = FacesContext.getCurrentInstance()
					.getExternalContext().getInitParameter("CONNSTRING");

			// Pool de conexiones a la base de datos para ganar performance
			basicDataSource = new BasicDataSource();
			basicDataSource.setMinIdle(1);
			basicDataSource.setInitialSize(1);
			basicDataSource.setDriverClassName(driverClassName);
			basicDataSource.setUsername(user);
			basicDataSource.setPassword(password);
			basicDataSource.setUrl(connection);
			basicDataSource.setValidationQuery("select 1");
		}

		// Devuelve una conexion disponible del pool. Al invocar close() en esa
		// conexion, en realidad se le está indicando al pool que esa conexion 
		//queda libre y la vuelve a tomar.
		return basicDataSource.getConnection();
	}

}
