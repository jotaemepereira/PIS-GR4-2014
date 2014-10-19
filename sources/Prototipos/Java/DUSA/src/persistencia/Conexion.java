package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.naming.NamingException;

public class Conexion {
	public static Connection getConnection() throws NamingException,
			SQLException {
//		try {
//			Class.forName(FacesContext.getCurrentInstance()
//					.getExternalContext().getInitParameter("DRIVER"));
//		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//		}

//		String user = "root";
//		FacesContext.getCurrentInstance().getExternalContext().getInitParameter("DBUSER");
//		String password = "root";
//		FacesContext.getCurrentInstance().getExternalContext().getInitParameter("DBPWD");
//		String connection = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("CONNSTRING");
		
		
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/pisgr4", "root", "root");
		
		/*
		
		try {
 			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		}

		String user = "root";
		String password = "root";
		String connection = "jdbc:postgresql://localhost:5432/pisgr4";
		return DriverManager.getConnection(connection, user, password);*/
	}

}
