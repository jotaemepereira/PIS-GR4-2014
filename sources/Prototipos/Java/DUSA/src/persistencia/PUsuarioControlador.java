package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import controladores.Excepciones;
import model.Actividad;
import model.Operacion;
import model.Rol;
import model.Usuario;
import interfaces.IUsuarioPersistencia;
/**
 * 
 * @author santiago
 *  
 */
public class PUsuarioControlador implements IUsuarioPersistencia{
	/**
	 * Con el usuario y el password obtenidos en la web se van a obtener el 
	 * usuario de la base de datos y junto con él, los roles asociados y el conjuto de operaciones 
	 * permitidas para cada rol. 
	 * A nivel de lógica se consulta antes de ejecutar una operación, si el usuario tiene el permiso asociado 
	 * correspondiete a la operacion del sistema antes de ejecutar dicha operacion. 
	 * 
	 */
	@Override
	public Usuario getUsuario(String nombre, String contrasenia) throws Excepciones{
		Usuario usr = new Usuario();
		PreparedStatement stmt = null;
		/**
		 * obtengo el usuario de la base de datos 
		 * 
		 * 
		 */


		String query = "SELECT u.user_id, u.username " + 
				"FROM USERS u " +
				"WHERE status <> FALSE AND username=" + "'" + nombre +"'" + ";";


		try {
			Connection c = Conexion.getConnection();			
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){ 
				usr.setUsuarioId(rs.getInt("user_id"));
			}
			stmt.close();
			c.close();


		} catch (Exception e){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}


		long idUsuario =  usr.getUsuarioId();

		contrasenia = "\'" + contrasenia + "\'";
		query = "SELECT u.user_id, u.username, u.pwd_hash, u.status " + 
				"FROM USERS u " +
				"WHERE status <> FALSE AND user_id=" + idUsuario +" AND pwd_hash ="+ contrasenia + ";";

		try {
			Connection c = Conexion.getConnection();			
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){ 
				usr.setUsuarioId(rs.getInt("user_id")); 
				usr.setNombre(rs.getString("username"));
				usr.setPwd_hash(rs.getString("pwd_hash"));
				usr.setEstado(rs.getBoolean("status"));
			}
			stmt.close();
			c.close();


		} catch (Exception e){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		/**
		 * obtengo los roles del usuario
		 * 
		 */
		stmt = null;
		query = null;
		query = "SELECT ur.role_id " + 
				"FROM USER_ROLES ur " +
				"WHERE user_id=" + idUsuario + ";";
		try{
			Connection c = Conexion.getConnection();			
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ArrayList <Rol> roles = new ArrayList<Rol>();
			while(rs.next()){ 
				Rol rol = new Rol();
				rol.setId(rs.getInt("role_id"));
				roles.add(rol);
			}
			usr.setRoles(roles);
			stmt.close();
			c.close();

		} catch (Exception e){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}

		/**
		 * obtengo las operaciones permitidas por cada rol que tiene el usuario
		 */

		Iterator<Rol> it = usr.getRoles().iterator();
		while (it.hasNext()){
			Rol rol = it.next();
			stmt = null;
			query = null;
			query = "SELECT op.operation_id ," + " operation_name " + 
					"FROM OPERATION_PERMISSIONS op " +
					"WHERE role_id=" + rol.getId() + ";";
			try{
				Connection c = Conexion.getConnection();			
				stmt = c.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				ArrayList <Operacion> ops = new ArrayList<Operacion>();
				while(rs.next()){ 
					Operacion op = new Operacion();
					op.setId(rs.getInt("operation_id"));
					op.setNombre(rs.getString("operation_name"));
					ops.add(op);
				}
				rol.setOperaciones(ops);
				stmt.close();
				c.close();


			} catch (Exception e){
				throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
			}
		}
		/**
		 * le agrego los nombres a los roles
		 */
		it = usr.getRoles().iterator();
		while (it.hasNext()){
			Rol rol = it.next();
			stmt = null;
			query = null;
			query = "SELECT rolename " + 
					"FROM ROLES " +
					"WHERE role_id=" + rol.getId() + ";";
			try{
				Connection c = Conexion.getConnection();			
				stmt = c.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()){ 
					rol.setNombre(rs.getString("rolename"));
				}
				stmt.close();
				c.close();

			} catch (Exception e){
				throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
			}
		}
		return usr;
	}
	/*
	 * Se registra la actividad del usuario. 
	 * El tipo actividad vincula a un usuario con una operacion  
	 */
	@Override
	public void registrarActividad(Actividad act)throws Excepciones{
		PreparedStatement stmt = null;
		String query = "INSERT INTO USER_ACTIVITY "+ 
				"(USERNAME, OPERATION_ID, OPERATION_NAME, LOG_DATE) " +
				"VALUES (  '" + act.getUserName() + "' , "+ act.getOpId() +" , '"+ act.getOpName() +"' , " + "LOCALTIMESTAMP );" ;

		try {
			Connection c = Conexion.getConnection();			
			stmt = c.prepareStatement(query);
			stmt.executeUpdate();
			stmt.close();
			c.close();

		} catch (Exception e){
			e.printStackTrace();
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}

	}
	/*
	 * Se leen los correos electronicos de los administradores.
	 * Para enviarle un mail en la operacion actualizarStock()
	 */
	
	
	
	@Override
	public List<String> getAdminisMails()throws Excepciones{
		
        List<String> adminMails = new ArrayList<String>();
		String query = "SELECT u.mail FROM USERS u, USER_ROLES ur WHERE ur.role_id =1 AND u.user_id = ur.user_id;";
		try {
			Connection c = Conexion.getConnection();			
			PreparedStatement stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){ 
				adminMails.add(rs.getString("mail")); 
				 
			}
			stmt.close();
			c.close();

		} catch (Exception e){
			e.printStackTrace();
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		return adminMails; 
		
	}
	


}
