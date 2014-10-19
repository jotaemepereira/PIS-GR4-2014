package beans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * @author santiago, juan
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	long idUsuario;
	String nomUsuario;
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
	
	public String getNomUsuario() {
		return nomUsuario;
	}
	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public void iniciarSesion(){
		
		if ((nomUsuario.compareTo("admin") == 0) && (contrasenia.compareTo("1234") == 0)) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/DUSA/stock/altaArticulo.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void cerrarSesion(){
		
	}
	
	
}
