package model;

import controladores.Excepciones;

public class Proveedor {
	private String RUT = "";
	private String razonSocial = "";
	private String telefono = "";
	private String direccion = "";
	private String nombreComercial = "";
	private String tipoDocumento = "";
	
	public Proveedor(String nombreComercial) throws Excepciones{
		if((nombreComercial == null) || (nombreComercial.trim().isEmpty())){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_DATOS, Excepciones.ERROR_DATOS));
		}
		this.nombreComercial = nombreComercial.trim();
	}
	
	public String getRUT(){
		return RUT;
	}
	public void setRUT(String RUT) throws Excepciones{
		if(RUT == null){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		this.RUT = RUT.trim();
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) throws Excepciones{
		if(razonSocial == null){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		this.razonSocial = razonSocial.trim();
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) throws Excepciones{
		if(telefono == null){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		this.telefono = telefono.trim();
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) throws Excepciones{
		if(direccion == null){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		this.direccion = direccion.trim();
	}
	public String getNombreComercial()  {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) throws Excepciones{
		if((nombreComercial == null) || (nombreComercial.trim().isEmpty())){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_DATOS, Excepciones.ERROR_DATOS));
		}
		this.nombreComercial = nombreComercial.trim();
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) throws Excepciones {
		if(tipoDocumento == null){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		this.tipoDocumento = tipoDocumento;
	}
}
