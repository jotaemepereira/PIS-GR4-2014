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
	
	/**
	 * Con el usuario y el password ya en MD5 obtenidos en la web se obtiene el usuario 
	 * de la base de datos 
	 * @author Santiago
	 * @param  nombre 
	 * @param  contrasenia 
	 * @return Articulo con id idArticulo, null si no lo encuentra
	 * @throws Excepciones
	 */
	public Usuario getUsuario( String nombre, String contrasenia) throws Excepciones;
	
	/**
	 * Se registra en la base de datos la actividad del usuario
	 * esto es se vincula una operación del sistema junto al usuario que la realizó 
	 * y la fecha de realizada.
	 * @author Santiago
	 * @param  actividad 
	 * @throws Excepciones
	 */
	public void registrarActividad(Actividad actividad) throws Excepciones;
	
	/**
	 * finalmente esta operación no se utiliza ya que se concideró 
	 * una mejor práctica guardar los mail en archivos .properties
	 * @return List<String> de los mail de los usuarios
	 * @throws Excepciones
	 * @author Santiago
	 */
	public List<String> getAdminisMails() throws Excepciones;

}
