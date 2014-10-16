package interfaces;

import model.Actividad;
import model.Usuario;

/**
 * 
 * @author santiago
 *
 */
public interface IUsuarioPersistencia {
	
	public Usuario getUsuario(long idUsuario, String contrasenia);
	public void registrarActividad(Actividad actividad);

}
