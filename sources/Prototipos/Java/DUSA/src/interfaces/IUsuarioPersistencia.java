package interfaces;

import java.util.List;

import controladores.Excepciones;
import model.Actividad;
import model.Usuario;

/**
 * 
 * @author santiago
 *
 */
public interface IUsuarioPersistencia {
	
	public Usuario getUsuario( String nombre, String contrasenia) throws Excepciones;
	public void registrarActividad(Actividad actividad) throws Excepciones;
	public List<String> getAdminisMails() throws Excepciones;

}
