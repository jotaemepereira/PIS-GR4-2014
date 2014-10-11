package beans;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.model.UploadedFile;

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
