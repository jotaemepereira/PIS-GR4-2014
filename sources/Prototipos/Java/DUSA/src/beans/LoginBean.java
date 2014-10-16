package beans;

import java.io.Serializable;

/**
 * @author santiago
 */
public class LoginBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	long idUsuario;
	String contrasenia;
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public void iniciarSesion(){
		
	}
	public void cerrarSesion(){
		
	}
	
	
}
