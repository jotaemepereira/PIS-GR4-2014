package model;

import controladores.Excepciones;

public class Proveedor {
	private String RUT;
	private String razonSocial;
	private String telefono;
	private String direccion;
	private String nombreComercial;
	
	public Proveedor(String nombreComercial) throws Excepciones{
		if(nombreComercial == ""){
			throw(new Excepciones("Faltan datos", Excepciones.ERROR_DATOS));
		}
		this.nombreComercial = nombreComercial;
	}
	
	public String getRUT(){
		return RUT;
	}
	public void setRUT(String RUT){
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
	public String getNombreComercial()  {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) throws Excepciones{
		if(nombreComercial == ""){
			throw(new Excepciones("Faltan datos", Excepciones.ERROR_DATOS));
		}
		this.nombreComercial = nombreComercial;
	}
}
