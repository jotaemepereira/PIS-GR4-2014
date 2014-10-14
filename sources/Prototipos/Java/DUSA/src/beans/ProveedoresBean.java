package beans;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;

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

	private String RUT;
	private String razonSocial;
	private String telefono;
	private String direccion;
	private String nombreComercial;
	private String tipoDocumento = "R";

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
		this.RUT = RUT.trim().replace("_", "");
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
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento
	 *            the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * Método encargado de crear el objeto proveedor, en caso que haya algun
	 * error lo muestra en pantalla al usuario y se comunica con la lógica para
	 * dar de alta definitivamente al proveedor
	 * 
	 * @author Victoria Díaz
	 */
	public void altaProveedor() {
		Proveedor proveedor;
		FacesContext context = FacesContext.getCurrentInstance();

		// Verifico que el documento sea correcto en caso de rut y ci
		if (tipoDocumento.equals("C")) { // en caso de ci
			System.out.println("SELECCIONO CI");
			// Inicializo los coefcientes en el orden correcto
			int[] arrCoefs = { 2, 9, 8, 7, 6, 3, 4, 1 };
			int suma = 0;
			// Para el caso en el que la CI tiene menos de 8 digitos
			// calculo cuantos coeficientes no voy a usar
			int difCoef = arrCoefs.length - RUT.length();
			// recorro cada digito empezando por el de más a la derecha
			// o sea, el digito verificador, el que tiene indice mayor en el
			// array
			for (int i = (RUT.length() - 1); i > -1; i--) {
				// Obtengo el digito correspondiente de la ci recibida
				String dig = RUT.substring(i, i + 1);
				// Lo tenía como caracter, lo transformo a int para poder operar
				int digInt = Integer.parseInt(dig);
				// Obtengo el coeficiente correspondiente al ésta posición del
				// digito
				int coef = arrCoefs[i + difCoef];
				// Multiplico dígito por coeficiente y lo acumulo a la suma
				// total''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''                        
				suma = suma + digInt * coef;
			}
			// si la suma es múltiplo de 10 es una ci válida
			if (((suma % 10) != 0) || ((RUT.length() != 8) && (RUT.length() != 7))) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, Excepciones.MENSAJE_RUT_ERRONEO, ""));
				return;
			}
			
		} else if (tipoDocumento.equals("R") ){ // en caso de rut
			System.out.println("SELECCIONO RUT");
			int[] digitos = new int[RUT.length()];
			int factor;
			int suma = 0;
			int modulo = 0;
			int digitoVerificador = -1;
			try {
				factor = 2;
				int total = digitos.length-2;
				for (int i = total; i >= 0 ; i--) {
					digitos[i] = Integer.parseInt("" + RUT.charAt(i));
					suma = suma + (digitos[i]*factor);
					factor = factor==9? 2 : (factor+1); 
				}
				//calculo el modulo 11 de la suma
				modulo = suma % 11;
				digitoVerificador = 11 - modulo;
				if(digitoVerificador==11){
					digitoVerificador = 0;
				}
				if(digitoVerificador==10){
					digitoVerificador = 1;
				}
			} catch (Exception e) {
					digitoVerificador = -1;
			}
			char verificadorRUT = RUT.charAt(RUT.length()-1);
			System.out.println("digito ingresado: " + verificadorRUT + " calculado: " + digitoVerificador);
			if(Integer.parseInt("" + verificadorRUT) != digitoVerificador){
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, Excepciones.MENSAJE_RUT_ERRONEO, ""));
				return;
			}
			
		}

		/* Creo el proveedor y en caso de error aviso al usuario y cancelo la
		 * operación 
		 */
		try {
			proveedor = new Proveedor(nombreComercial);
			proveedor.setTipoDocumento(tipoDocumento);
			proveedor.setRUT(RUT);
			proveedor.setRazonSocial(razonSocial);
			proveedor.setTelefono(telefono);
			proveedor.setDireccion(direccion);
		} catch (Excepciones e1) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e1.getMessage(), ""));
			return;
		}

		/*
		 * Llamo a la lógica para que se de de alta el proveedor en el sistema y
		 * en caso de error lo muestro
		 */
		try {
			FabricaSistema.getISistema().altaProveedor(proveedor);
		} catch (Excepciones e) {
			if (e.getErrorCode() == Excepciones.ADVERTENCIA_DATOS) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, e.getMessage(), ""));
			} else {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
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
		this.tipoDocumento = "R";
	}

	/**
	 * En caso que el usuario cancele la alta, se vacía el formulario
	 */
	public void cancelarAltaProveedor() {
		System.out.println("********** CANCELAR PROVEEDOR ************");
		this.direccion = "";
		this.nombreComercial = "";
		this.razonSocial = "";
		this.RUT = "";
		this.telefono = "";
		this.tipoDocumento = "R";
	}

}
