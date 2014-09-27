package beans;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProveedoresBeanTest {

	@Test
	public void test() {
		ProveedoresBean test = new ProveedoresBean();
		
		// chequeo cada get y set por separado
		test.setDireccion("Direccion");
		assertEquals("Test direccion", "Direccion", test.getDireccion());
		
		test.setNombreComercial("Nombre comercial");
		assertEquals("Test nombre comercial", "Nombre comercial", test.getNombreComercial());
		
		test.setRazonSocial("Razon social");
		assertEquals("Test razon social", "Razon social", test.getRazonSocial());
		
		test.setRUT("25554549");
		assertEquals("Test rut", 3569856, test.getRUT());
		
		test.setTelefono("26043784");
		assertEquals("Test telefono", 26043784, test.getTelefono());
		
		// verifico la funcion de agregarProveedor
		//=> caso de no ingreso de nombre comercial
		test.setNombreComercial("");
		
		
	}

}
