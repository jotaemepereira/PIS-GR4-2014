package beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import controladores.Excepciones;
import controladores.FabricaSistema;

/**
 * @author santiago, juan
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {


	private static final long serialVersionUID = 1L;

	private long idUsuario;
	private String nomUsuario;
	private String contrasenia;

	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getContrasenia() {
		return contrasenia;
	}

	public String getNomUsuario() {
		return nomUsuario;
	}
	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}
	
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public void iniciarSesion() {
		try {
			FabricaSistema.getISistema().iniciarSesion(nomUsuario, contrasenia);
		} catch (Excepciones e) {			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							e.getMessage(),
							""));
		}
	}
	
	public void cerrarSesion(){

	}
	
	public LoginBean() {
		
	}

}
