package controladores;

import static org.junit.Assert.*;
import model.Proveedor;

import org.junit.Test;

public class ProveedoresControladorTest {

	@Test
	public void test() {
		Proveedor proveedor;
		try {
			proveedor = new Proveedor("nombreComercial");
		} catch (Excepciones e) {
			fail("Excepcion al crear el proveedor (1)");
			return;
		}
		
		// Pruebo dar de alta un proveedor solo con la información basica
		try {
			FabricaSistema.getISistema().altaProveedor(proveedor);
		} catch (Excepciones e) {
			fail("Excepcion altaProveedor (1)");
		}
		
		// Seteo las otras opciones y doy de alta al proveedor
		try {
			proveedor.setDireccion("direccion");
		} catch (Excepciones e1) {
			fail("error set en proveedor");
		}
		try {
			proveedor.setRazonSocial("razonSocial");
		} catch (Excepciones e1) {
			fail("error set en proveedor");
		}
		try {
			proveedor.setNombreComercial("nombreComercial2");
		} catch (Excepciones e) {
			fail("Excepcion setNombreComercial (1)");
		}
		try {
			proveedor.setRUT("123456");
		} catch (Excepciones e1) {
			fail("error set en proveedor");
		}
		try {
			proveedor.setTelefono("099028427");
		} catch (Excepciones e1) {
			fail("error set en proveedor");
		}
		
		try {
			FabricaSistema.getISistema().altaProveedor(proveedor);
		} catch (Excepciones e) {
			fail("Excepcion altaProveedor (2)");
		}
		
		// Pruebo ingresar otro proveedor con el mismo nombre comercial y distinto rut y me tiene que lanzar una excepcion por el nombre
		try {
			proveedor.setRUT("654321");
		} catch (Excepciones e1) {
			fail("error set en proveedor");
		}
		try {
			FabricaSistema.getISistema().altaProveedor(proveedor);
			fail("No lanzo la excepcion de nombre comercial");
		} catch (Excepciones e) {
			if(e.getMessage() != Excepciones.MENSAJE_NOMBRE_COMERCIAL_DUPLICADO){
				fail("Excepcion equivocada, lanzo: " + e.getMessage());
			}
		}
		
		// Pruebo ingresar el mismo proveedor y me tiene que lanzar excepcion por el rut
		try {
			FabricaSistema.getISistema().altaProveedor(proveedor);
			fail("No lanzo la excepcion de rut");
		} catch (Excepciones e) {
			if(e.getMessage() != Excepciones.MENSAJE_RUT_DUPLIACADO){
				fail("Excepcion equivocada, lanzo: " + e.getMessage());
			}
		}
	}

}
