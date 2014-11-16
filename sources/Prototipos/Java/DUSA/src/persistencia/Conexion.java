package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class Conexion {
	
	private static BasicDataSource basicDataSource;
	
	public static Connection getConnection() throws NamingException, SQLException {
		
		try {
			Class.forName(FacesContext.getCurrentInstance()
					.getExternalContext().getInitParameter("DRIVER"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//        String user = FacesContext.getCurrentInstance().getExternalContext()
		//                .getInitParameter("DBUSER");
		//        String password = FacesContext.getCurrentInstance()
		//                .getExternalContext().getInitParameter("DBPWD");
		//        String connection = FacesContext.getCurrentInstance()
		//                .getExternalContext().getInitParameter("CONNSTRING");
		//        return DriverManager.getConnection(connection, user, password);
		
		
		/*

        try {
             Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
        }

        String user = "root";
        String password = "root";
        String connection = "jdbc:postgresql://localhost:5432/pisgr4";
        return DriverManager.getConnection(connection, user, password);
        
        */

		//	return DriverManager.getConnection("jdbc:postgresql://localhost:5432/pisgr4", "root", "root");

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
		
		Connection conexion = basicDataSource.getConnection();
		//Estado inicial que se espera da la conexion.
		
		conexion.setAutoCommit(true);
		
		return conexion;
	}

}