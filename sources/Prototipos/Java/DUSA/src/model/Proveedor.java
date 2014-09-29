package model;

import controladores.Excepciones;

public class Proveedor {
	private String RUT = "";
	private String razonSocial = "";
	private String telefono = "";
	private String direccion = "";
	private String nombreComercial = "";
	
	public Proveedor(String nombreComercial) throws Excepciones{
		if((nombreComercial == null) || (nombreComercial.trim().isEmpty())){
			throw(new Excepciones("Faltan datos", Excepciones.ERROR_DATOS));
		}
		this.nombreComercial = nombreComercial;
	}
	
	public String getRUT(){
		return RUT;
	}
	public void setRUT(String RUT) throws Excepciones{
		if(RUT == null){
			throw(new Excepciones("Faltan datos", Excepciones.ERROR_DATOS));
		}
		this.RUT = RUT;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) throws Excepciones{
		if(razonSocial == null){
			throw(new Excepciones("Faltan datos", Excepciones.ERROR_DATOS));
		}
		this.razonSocial = razonSocial;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) throws Excepciones{
		if(telefono == null){
			throw(new Excepciones("Faltan datos", Excepciones.ERROR_DATOS));
		}
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) throws Excepciones{
		if(direccion == null){
			throw(new Excepciones("Faltan datos", Excepciones.ERROR_DATOS));
		}
		this.direccion = direccion;
	}
	public String getNombreComercial()  {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) throws Excepciones{
		if((nombreComercial == null) || (nombreComercial.trim().isEmpty())){
			throw(new Excepciones("Faltan datos", Excepciones.ERROR_DATOS));
		}
		this.nombreComercial = nombreComercial;
	}
}
