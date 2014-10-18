package interfaces;

import controladores.Excepciones;
import model.Actividad;
import model.Usuario;

/**
 * 
 * @author santiago
 *
 */
public interface IUsuarioPersistencia {
	
	public Usuario getUsuario(long idUsuario, String contrasenia) throws Excepciones;
	public void registrarActividad(Actividad actividad) throws Excepciones;

}
