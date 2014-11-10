package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import controladores.Excepciones;
import datatypes.DTBusquedaArticulo;


@ManagedBean
@ViewScoped
public class BusquedaArticuloBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private ISistema instanciaSistema;
	
	private String busqueda = "";
	private List<DTBusquedaArticulo> resBusqueda = new ArrayList<DTBusquedaArticulo>();
	
	//Setters y getters
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public ISistema getInstanciaSistema() {
		return instanciaSistema;
	}
	
	public void setISistema(ISistema s) {
		
		this.instanciaSistema = s;
	}
	
	public String getBusqueda() {
		return busqueda;
	}
	
	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}
	
	public List<DTBusquedaArticulo> getResBusqueda() {
		return resBusqueda;
	}
	
	public void setResBusqueda(List<DTBusquedaArticulo> resBusqueda) {
		this.resBusqueda = resBusqueda;
	}
	
	public void buscarArticulos() {
		resBusqueda = new ArrayList<DTBusquedaArticulo>();

		if (busqueda.equals("")) {
			return;
		}

		try {
			//Se realiza la busqueda
			resBusqueda = this.instanciaSistema.buscarArticulos(busqueda);
			System.out.println("CANTIDAD ENCONTRADA: " + resBusqueda.size());
		} catch (Excepciones e) {
			//Se imprime y notifica del error
			e.printStackTrace();
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}
	
	
}
