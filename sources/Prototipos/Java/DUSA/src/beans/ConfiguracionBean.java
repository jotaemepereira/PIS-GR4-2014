package beans;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ApplicationScoped
public class ConfiguracionBean implements Serializable {

	private String rutaLogo;
	//@ManagedProperty("#{loginBean.nomUsuario}")
    //private LoginBean nomUsuario;
	
	public ConfiguracionBean() {
		setRutaLogo(FacesContext.getCurrentInstance().getExternalContext()
				.getInitParameter("IMAGEN"));
	}

	/*public void setActiveUser(LoginBean login) {
        this.nomUsuario = login;
    }*/
	
	
	public String getRutaLogo() {
		return rutaLogo;
	}

	public void setRutaLogo(String rutaLogo) {
		this.rutaLogo = rutaLogo;
	}
}
