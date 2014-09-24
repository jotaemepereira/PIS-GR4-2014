package beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.Proveedor;
import controladores.Excepciones;
import controladores.FabricaSistema;

@ManagedBean
@SessionScoped
public class ProveedoresBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String RUT;
	private String razonSocial;
	private String telefono;
	private String direccion;
	private String nombreComercial;
	
	public String getRUT() {
        return RUT;
    }
    public void setRUT(String RUT) {
        this.RUT = RUT;
    }
 
    public String getRazonSocial() {
        return razonSocial;
    }
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getNombreComercial() {
        return nombreComercial;
    }
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }
	    
	public void altaProveedor(){
		Proveedor proveedor = new Proveedor();
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(nombreComercial.length() < 4){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre Comercial: Error de validación: se necesita un valor.", ""));
			return;
		}
		
		proveedor.setRUT(RUT);
		proveedor.setRazonSocial(razonSocial);
		proveedor.setTelefono(telefono);
		proveedor.setDireccion(direccion);
		proveedor.setNombreComercial(nombreComercial);
		
		try {
			FabricaSistema.getISistema().altaProveedor(proveedor);
		} catch (Excepciones e) {
			if(e.getErrorCode() == Excepciones.ERROR_DATOS){
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre Comercial: Error de validación: se necesita un valor.", ""));
				return;
			}else{
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe un proveedor en el sistema con ese nombre comercial", ""));
			}
		}
		// si todo bien aviso y vacio el formulario
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El proveedor ha sido dado de alta correctamente", ""));
		this.direccion = "";
		this.nombreComercial = "";
		this.razonSocial = "";
		this.RUT = "";
		this.telefono = "";
	}
}
