package beans;

import interfaces.ISistema;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import controladores.Excepciones;
import controladores.FabricaSistema;
import model.Enumerados;
import model.Usuario;
import model.Enumerados.casoDeUso;

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
		if (instanciaSistema != null) {
			if ((instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaArticulo))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarArticulo))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarStock))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.generPeEnBaseAPedAnt))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.genPedEnBaseAHist))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.bajaArticulo))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarArticulo))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.alertaVencimiento))) {
				return ""; }
			else return "display : none";}
		return "";
	}

	public String getVentasPermiso() {
		if (instanciaSistema != null) {
			if ((instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.registrarNuevaVenta))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.registrarVentaPerdida))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.facturarVentaPendiente))){
				return "";
			}
			else return "display : none"; }
		return "";
	}

	public String getClientesPermiso() {
		if (instanciaSistema != null) {
			if ((instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarCliente))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaCliente))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarCliente))) {
				return ""; 
			}
			else return "display : none"; }
		return "";
	}

	public String getProveedoresPermiso() {
		if (instanciaSistema != null) { 
			if ((instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarProveedor))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaProveedor))
					//TODO: Se comenta buscarProveedor ya que aun no se encuentra implementado
					/*|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarProveedor))*/) {
				return ""; 
			}
			else return "display : none"; }
		return "";
	}

	public String getPedidoPermiso() {
		if (instanciaSistema != null) {
			if ((instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.generPeEnBaseAPedAnt))
					|| (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.genPedEnBaseAHist))) {
				return ""; 
			}
			else return "display : none"; }
		return "";
	}

	public String getRegistrarNuevaVentaRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.registrarNuevaVenta))
				return ""; 
			else return "display : none"; }
		return "";
	}

	public String getRegistrarVentaPerdidaRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.registrarVentaPerdida))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getFacturarVentaPendienteRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.facturarVentaPendiente))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getCancelarVentaPendienteRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.cancelarVentaPendiente))
				return "";
			else return "display : none"; }
		return null;
	}

	public String getListarVentasPendientesRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.listarVentasPendientes))
				return "";
			else return "display : none";	}
		return "";
	}

	public String getNuevaCompraRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.nuevaCompra))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getListarComprasDusaRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.listarComprasDusa))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getAltaArticuloRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaArticulo))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getBajaArticuloRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.registrarVentaPerdida))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getObtenerArticuloRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.obtenerArticulo))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getModificarArticuloRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarArticulo))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getModificarStockRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarStock))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getBuscarArticuloRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarArticulo))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getGenerPeEnBaseAPedAntRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.generPeEnBaseAPedAnt))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getGenPedEnBaseAHistRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.genPedEnBaseAHist))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getRealizarPedidoRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.realizarPedido))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getAltaClienteRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaCliente))
				return "";
			else return "display : none";}
		return "";
	}

	public String getModificarClienteRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarCliente))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getBuscarClienteRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarCliente))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getAltaProveedorRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.altaProveedor))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getModificarProveedorRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.modificarProveedor))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getBuscarProveedorRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.buscarProveedor))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getGenerarEstadisticaRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.generarEstadistica))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getIniciarSesionRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.iniciarSesion))
				return "";
			else return "display : none"; }
		return "";
	}

	public String getCerrarSesionRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.cerrarSesion))
				return "";
			else return "display : none"; }
		return "";
	}
	
	public String getAlertaVencimientoRet() {
		if (instanciaSistema != null) {
			if (instanciaSistema.obtenerUsuarioLogueado().tienePermiso(Enumerados.casoDeUso.alertaVencimiento))
				return "";
			else return "display : none"; }
		return "";
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
		// se transforma la contrasenia de texto plano a md5
		String plaintext = contrasenia;
		MessageDigest m=null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		m.reset();
		m.update(plaintext.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1,digest);
		String hashtext = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		while(hashtext.length() < 32 ){
		  hashtext = "0"+hashtext;
		}

		this.contrasenia = hashtext;
	}

	public void iniciarSesion() {
		instanciaSistema = FabricaSistema.getISistema();
		try {

			instanciaSistema.iniciarSesion(nomUsuario, contrasenia);
			FacesContext.getCurrentInstance().getExternalContext().redirect("stock/busquedaArticulo.jsf");

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
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ha ocurrido un error al cargar la página.", ""));
		}
	}

	public void redirectIfLoggedIn() {
		try {
			if (instanciaSistema != null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/DUSA/stock/busquedaArticulo.jsf");
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ha ocurrido un error al cargar la página.", ""));
		}
	}

	public void redirectIfNotLoggedIn() {
		try {
			if (instanciaSistema == null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../login.jsf");
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ha ocurrido un error al cargar la página.", ""));
		}
	}
	
	public String indexVentasPorPermisos() {
		
		/*Por defecto se va a busqueda de articulo*/
		String paginaIndex = "../stock/busquedaArticulo.jsf";
		
		if (this.instanciaSistema != null) {
			
			Usuario user = this.instanciaSistema.obtenerUsuarioLogueado();
			if (user.tienePermiso(casoDeUso.registrarNuevaVenta)){

				paginaIndex = "../ventas/nuevaVenta.jsf";
			} else if (user.tienePermiso(casoDeUso.facturarVentaPendiente)) {
				
				paginaIndex = "../ventas/facturacion.jsf";
			}  
		}
		
		return paginaIndex;
	}

	public LoginBean() {
		instanciaSistema = null;
	}

}
