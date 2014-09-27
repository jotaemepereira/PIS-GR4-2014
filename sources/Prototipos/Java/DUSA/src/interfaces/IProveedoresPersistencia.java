package interfaces;

import controladores.Excepciones;
import model.Proveedor;

public interface IProveedoresPersistencia {
	
	public void persistirProveedor(Proveedor proveedor) throws Excepciones;
	public boolean existeProveedorNombreComercial(String nombreComercial) throws Excepciones;
	public boolean existeProveedorRUT(String RUT) throws Excepciones;
}
