package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import controladores.Excepciones;
import datatypes.DTBusquedaArticuloSolr;

@ManagedBean
@ViewScoped
public class ModificarPreciosBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long proveedor;
	private List<DTBusquedaArticuloSolr> articulosDelProveedor;
	
	private ISistema instanciaSistema;
	
	public long getProveedor() {
		return proveedor;
	}

	public void setProveedor(long proveedor) {
		this.proveedor = proveedor;
	}

	public ModificarPreciosBean() {
		// TODO Auto-generated constructor stub
	}
	
	public void getArticulosProveedor() {
		
	}
	
	public void setISistema(ISistema s) {
		this.instanciaSistema = s;
	}
	
}
