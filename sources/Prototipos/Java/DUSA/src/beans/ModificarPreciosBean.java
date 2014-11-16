package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import model.Articulo;
import controladores.Excepciones;
import datatypes.DTProveedor;

@ManagedBean
@ViewScoped
public class ModificarPreciosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private long proveedor;
	private List<Articulo> articulosDelProveedor;
	private List<BigDecimal> compararModificados;
	private List<DTProveedor> listaProveedores;
	private Map<Integer, DTProveedor> proveedores;
	private ISistema instanciaSistema;

	public Map<Integer, DTProveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(Map<Integer, DTProveedor> proveedores) {
		this.proveedores = proveedores;
	}

	public List<DTProveedor> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(List<DTProveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}

	public List<Articulo> getArticulosDelProveedor() {
		return articulosDelProveedor;
	}

	public void setArticulosDelProveedor(
			List<Articulo> articulosDelProveedor) {
		this.articulosDelProveedor = articulosDelProveedor;
	}


	public long getProveedor() {
		return proveedor;
	}

	public void setProveedor(long proveedor) {
		this.proveedor = proveedor;
	}

	public void cargarProveedores() throws Excepciones {
		if (listaProveedores.isEmpty()) {

			this.proveedores = this.instanciaSistema.obtenerProveedores();
			this.listaProveedores = new ArrayList<DTProveedor>(
					this.proveedores.values());
		}
	}

	public ModificarPreciosBean() {
		this.listaProveedores = new ArrayList<DTProveedor>();
		this.articulosDelProveedor = new ArrayList<Articulo>();
	}

	public void getArticulosProveedor() {
		try {
				articulosDelProveedor = instanciaSistema.obtenerArticulosDelProveedor(proveedor);
				compararModificados = new ArrayList<BigDecimal>();
				for (int i=0; i < articulosDelProveedor.size(); i++) {
					compararModificados.add(articulosDelProveedor.get(i).getPrecioUnitario());
				}	
		} catch (Excepciones e) {
				e.printStackTrace();
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public void setISistema(ISistema s) {
		this.instanciaSistema = s;

		if (this.instanciaSistema != null) {

			try {
				// Cargo los proveedores de la base de datos
				cargarProveedores();
			} catch (Excepciones ex) {

				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));

			}
		}
	}
	
	private int seModificoElArticulo(int position, BigDecimal precio) {
		return (compararModificados.get(position).compareTo(precio)) ;
	}
	
	public void enviarPrecios() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<Long, BigDecimal> modificacionPrecios = new HashMap<Long, BigDecimal>();
		long cambios = 0;
		
		for (int i=0; i < articulosDelProveedor.size(); i++) {
			Articulo a = articulosDelProveedor.get(i);
			if (seModificoElArticulo(i, a.getPrecioUnitario()) != 0) {
				modificacionPrecios.put(a.getIdArticulo(), a.getPrecioUnitario()); 
				cambios += 1;
			}
		}
		
		try {
			
			this.instanciaSistema.modificarPrecioArticulos(modificacionPrecios);
			this.articulosDelProveedor.clear();
			
			String controlPlural = (cambios == 1) ? " artículo.":" artículos.";
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
					"Se ha realizado el cambio de precio de " + cambios + controlPlural, ""));
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

}
