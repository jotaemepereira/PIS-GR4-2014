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
	private ISistema instanciaSistema = null;


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

	}

}
