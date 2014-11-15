package beans;

import interfaces.ISistema;
import interfaces.IStock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Articulo;
import controladores.Excepciones;
import controladores.FabricaLogica;
import controladores.FabricaServicios;
import controladores.FabricaSistema;
import datatypes.DTBusquedaArticulo;
import datatypes.DTBusquedaArticuloSolr;
import datatypes.DTProveedor;

@ManagedBean
@ViewScoped
public class ModificarPreciosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private long proveedor;
	private List<Articulo> articulosDelProveedor;
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
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public void enviarPrecios() {
		Map<Long, BigDecimal> modificacionPrecios = new HashMap<Long, BigDecimal>();
		IStock sControlador = FabricaLogica.getIStock();
		
		for (Articulo a : articulosDelProveedor) {
			modificacionPrecios.put(a.getIdArticulo(), a.getPrecioUnitario());
		}
		try {
			sControlador.modificarPreciodeArticulos(modificacionPrecios);
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
