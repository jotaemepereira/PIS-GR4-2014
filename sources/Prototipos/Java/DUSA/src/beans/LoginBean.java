package beans;

import interfaces.ISistema;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import controladores.Excepciones;
import controladores.FabricaSistema;
import model.Enumerados;

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
	private ISistema instanciaSistema;

	public String getStockPermiso() {
		if ((instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaArticulo))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarArticulo))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarStock))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.generPeEnBaseAPedAnt))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.genPedEnBaseAHist))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.bajaArticulo))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarArticulo))) {
			return ""; }
		else return "display : none";
	}

	public String getVentasPermiso() {
		if ((instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.registrarNuevaVenta))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.registrarVentaPerdida))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.facturarVentaPendiente))){
			return "";
		}
		else return "display : none";
	}

	public String getClientesPermiso() {
		if ((instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarCliente))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaCliente))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarCliente))) {
			return ""; 
		}
		else return "display : none";
	}

	public String getProveedoresPermiso() {
		if ((instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarProveedor))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaProveedor))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarProveedor))) {
			return ""; 
		}
		else return "display : none";
	}
	
	public String getPedidoPermiso() {
		if ((instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.generPeEnBaseAPedAnt))
				|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.genPedEnBaseAHist))) {
			return ""; 
		}
		else return "display : none";
	}

	public String getRegistrarNuevaVentaRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.registrarNuevaVenta))
			return ""; 
		else return "display : none"; 
	}

	public String getRegistrarVentaPerdidaRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.registrarVentaPerdida))
			return "";
		else return "display : none";
	}

	public String getFacturarVentaPendienteRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.facturarVentaPendiente))
			return "";
		else return "display : none";
	}

	public String getCancelarVentaPendienteRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.cancelarVentaPendiente))
			return "";
		else return "display : none";	
	}

	public String getListarVentasPendientesRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.listarVentasPendientes))
			return "";
		else return "display : none";	
	}

	public String getNuevaCompraRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.nuevaCompra))
			return "";
		else return "display : none";		
	}

	public String getListarComprasDusaRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.listarComprasDusa))
			return "";
		else return "display : none";
	}

	public String getAltaArticuloRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaArticulo))
			return "";
		else return "display : none";
	}

	public String getBajaArticuloRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.registrarVentaPerdida))
			return "";
		else return "display : none";
	}

	public String getObtenerArticuloRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.obtenerArticulo))
			return "";
		else return "display : none";
	}

	public String getModificarArticuloRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarArticulo))
			return "";
		else return "display : none";
	}

	public String getModificarStockRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarStock))
			return "";
		else return "display : none";
	}

	public String getBuscarArticuloRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarArticulo))
			return "";
		else return "display : none";
	}

	public String getGenerPeEnBaseAPedAntRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.generPeEnBaseAPedAnt))
			return "";
		else return "display : none";
	}

	public String getGenPedEnBaseAHistRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.genPedEnBaseAHist))
			return "";
		else return "display : none";
	}

	public String getRealizarPedidoRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.realizarPedido))
			return "";
		else return "display : none";
	}

	public String getAltaClienteRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaCliente))
			return "";
		else return "display : none";	
	}

	public String getModificarClienteRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarCliente))
			return "";
		else return "display : none";
	}

	public String getBuscarClienteRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarCliente))
			return "";
		else return "display : none";
	}

	public String getAltaProveedorRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaProveedor))
			return "";
		else return "display : none";
	}

	public String getModificarProveedorRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarProveedor))
			return "";
		else return "display : none";
	}

	public String getBuscarProveedorRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarProveedor))
			return "";
		else return "display : none";
	}

	public String getGenerarEstadisticaRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.generarEstadistica))
			return "";
		else return "display : none";
	}

	public String getIniciarSesionRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.iniciarSesion))
			return "";
		else return "display : none";
	}

	public String getCerrarSesionRet() {
		if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.cerrarSesion))
			return "";
		else return "display : none";
	}

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
		instanciaSistema = FabricaSistema.getISistema();
		try {

			instanciaSistema.iniciarSesion(nomUsuario, contrasenia);
			FacesContext.getCurrentInstance().getExternalContext().redirect("stock/altaArticulo.jsf");

		} catch (Excepciones e) {			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							e.getMessage(),
							""));
			this.instanciaSistema = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public ISistema getInstanciaSistema() {
		return instanciaSistema;
	}
	public void setInstanciaSistema(ISistema instanciaSistema) {
		this.instanciaSistema = instanciaSistema;
	}
	public void cerrarSesion(){
		this.instanciaSistema = null;
		this.nomUsuario = "";
		this.contrasenia = "";
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../login.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void redirectIfLoggedIn() {
		try {
			if (instanciaSistema != null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/DUSA/stock/altaArticulo.jsf");
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ha ocurrido un error al cargar la página.", ""));
		}
	}

	public void redirectIfNotLoggedIn() {
		try {
			if (instanciaSistema == null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../login.jsf");
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ha ocurrido un error al cargar la página.", ""));
		}
	}

	public LoginBean() {
		instanciaSistema = null;
	}

}
