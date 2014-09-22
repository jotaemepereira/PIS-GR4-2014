package beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.Proveedor;
import controladores.FabricaSistema;

@ManagedBean
@SessionScoped
public class ProveedoresBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int RUT;
	private String razonSocial;
	private int telefono;
	private String direccion;
	private String nombreComercial;
	
	public int getRUT() {
        return RUT;
    }
    public void setRUT(int RUT) {
        this.RUT = RUT;
    }
 
    public String getRazonSocial() {
        return razonSocial;
    }
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    
    public int getTelefono() {
        return telefono;
    }
    public void setTelefono(int telefono) {
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
		
		if(String.valueOf(RUT).length() < 5){ // FIXME: averiguar largo ok
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "RUT: Error de validación: se necesita un valor.", ""));
			return;
		}
		
		if(nombreComercial.length() < 4){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre Comercial: Error de validación: se necesita un valor.", ""));
			return;
		}
		
		proveedor.setRUT(RUT);
		proveedor.setRazonSocial(razonSocial);
		proveedor.setTelefono(telefono);
		proveedor.setDireccion(direccion);
		proveedor.setNombreComercial(nombreComercial);

		FabricaSistema.getISistema().altaProveedor(proveedor);
	}
}
