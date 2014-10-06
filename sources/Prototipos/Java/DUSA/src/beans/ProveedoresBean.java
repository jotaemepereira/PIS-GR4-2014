package beans;

import java.io.Serializable;
import java.nio.charset.Charset;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.Proveedor;
import controladores.Excepciones;
import controladores.FabricaSistema;

@ManagedBean
@SessionScoped
/**
 * Clase que controla la presentación relacionada con los proveedores
 */
public class ProveedoresBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Charset UTF_8 = Charset.forName("UTF-8");

	private String RUT;
	private String razonSocial;
	private String telefono;
	private String direccion;
	private String nombreComercial;

	/**
	 * @return String que identifica al RUT
	 * @author Victoria Díaz
	 */
	public String getRUT() {
		return RUT;
	}

	/**
	 * @param RUT
	 *            - String
	 * @author Victoria Díaz
	 */
	public void setRUT(String RUT) {
		this.RUT = RUT;
	}

	/***
	 * @return String que identifica la razón social
	 * @author Victoria Díaz
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial
	 *            - String
	 * @author Victoria Díaz
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return String que identifica el teléfono
	 * @author Victoria Díaz
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            - String
	 * @author Victoria Díaz
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return String que identifica la direcció
	 * @author Victoria Díaz
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            - String
	 * @author Victoria Díaz
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return String que identifica el nombre comercial
	 * @author Victoria Díaz
	 */
	public String getNombreComercial() {
		return nombreComercial;
	}

	/**
	 * @param nombreComercial
	 *            - String
	 * @author Victoria Díaz
	 */
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	/**
	 * Método encargado de crear el objeto proveedor, en caso que haya algun
	 * error lo muestra en pantalla al usuario y se comunica con la l�gica para
	 * dar de alta definitivamente al proveedor
	 * @author Victoria Díaz
	 */
	public void altaProveedor() {
		Proveedor proveedor;
		FacesContext context = FacesContext.getCurrentInstance();

		// Creo el proveedor y en caso de error aviso al usuario y cancelo la operaci�n
		try {
			proveedor = new Proveedor(nombreComercial);
			proveedor.setRUT(RUT);
			proveedor.setRazonSocial(razonSocial);
			proveedor.setTelefono(telefono);
			proveedor.setDireccion(direccion);
		} catch (Excepciones e1) {
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							e1.getMessage(),
							""));
			return;
		}

		/* Llamo a la l�gica para que se de de alta el proveedor en el sistema y
		 en caso de error lo muestro */
		try {
			FabricaSistema.getISistema().altaProveedor(proveedor);
		} catch (Excepciones e) {
			if (e.getErrorCode() == Excepciones.ADVERTENCIA_DATOS) {
				context.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_WARN,
								e.getMessage(),
								""));
			} else {
				byte ptext[] = e.getMessage().getBytes(UTF_8); 
				context.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								new String(ptext),
								""));
				return;
			}
		}

		// si todo bien aviso y vacio el formulario
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				Excepciones.MENSAJE_OK_ALTA, ""));

		this.direccion = "";
		this.nombreComercial = "";
		this.razonSocial = "";
		this.RUT = "";
		this.telefono = "";
	}

	/**
	 * En caso que el usuario cancele la alta, se vac�a el formulario
	 */
	public void cancelarAltaProveedor() {
		this.direccion = "";
		this.nombreComercial = "";
		this.razonSocial = "";
		this.RUT = "";
		this.telefono = "";
	}
}
