package beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Venta;

@ManagedBean
@ViewScoped
public class FacturacionBean {
	
	private List<Venta> ventas;
	
	public FacturacionBean() {
		try{
		
		} catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar la página.", ""));
		}
	}
}
