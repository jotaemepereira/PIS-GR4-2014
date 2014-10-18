package beans;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
@ApplicationScoped
public class ConfiguracionBean implements Serializable {

	private String rutaLogo;

	public ConfiguracionBean() {
		setRutaLogo(FacesContext.getCurrentInstance().getExternalContext()
				.getInitParameter("IMAGEN"));
	}


	public String getRutaLogo() {
		return rutaLogo;
	}

	public void setRutaLogo(String rutaLogo) {
		this.rutaLogo = rutaLogo;
	}
}
