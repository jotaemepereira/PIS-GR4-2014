package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import controladores.Excepciones;
import datatypes.DTProveedor;
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
	 * obtengo al usuario
	 */
	public Usuario getUsuario(long idUsuario, String contrasenia) throws Excepciones{
		Usuario usr = null;
		PreparedStatement stmt = null;
		contrasenia = "\'" + contrasenia + "\'";
		String query = "SELECT u.user_id, u.username, u.pwd_hash, u.status " + 
						"FROM USERS u " +
						"WHERE status <> FALSE AND user_id=" + idUsuario +" AND pwd_hash ="+ contrasenia + ";";
		
		try {
			Connection c = Conexion.getConnection();			
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){ 
			    usr = new Usuario();
				usr.setUsuarioId(rs.getInt("user_id"));
				usr.setNombre(rs.getString("username"));
				usr.setPwd_hash(rs.getString("pwd_hash"));
				usr.setEstado(rs.getBoolean("status"));
			}
			
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
		} catch (Exception e){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		
		
		
		Iterator<Rol> it = usr.getRoles().iterator();
		while (it.hasNext()){
			Rol rol = (Rol)it.next();
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

			} catch (Exception e){
				throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
			}
		}
		/**
		 * le agrego los nombres a los roles
		 */
		it = usr.getRoles().iterator();
		while (it.hasNext()){
			Rol rol = (Rol)it.next();
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
			} catch (Exception e){
				throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
			}
		}
		return usr;
}
	public void registrarActividad(Actividad actividad)throws Excepciones{
		
	}


}
