package model;

import static org.junit.Assert.*;

import org.junit.Test;

import controladores.Excepciones;

public class ProveedorTest {

	@Test
	public void test() {
		Proveedor proveedor;
		
	// NOMBRE PROVEEDOR
		// Creo proveedor con nombre vacio
		try {
			proveedor = new Proveedor("");
			fail("No tiro excepcion new con nombre comercial vacio");
		} catch (Excepciones e) {
		}
		
		// Creo proveedor con nombre null
		try {
			proveedor = new Proveedor(null);
			fail("No tiro excepcion new con nombre comercial null");
		} catch (Excepciones e) {
		}
		
		// Creo proveedor con nombre ok
		try {
			proveedor = new Proveedor("nombre");
		} catch (Excepciones e) {
			fail("Excepcion en new ok");
			return;
		}
		assertEquals("Prueba nombre init", "nombre", proveedor.getNombreComercial());
		
		// Set nombre vacio
		try {
			proveedor.setNombreComercial("");
			fail("No tiro excepcion set con nombre comercial vacio");
		} catch (Excepciones e1) {
		}
		
		// Set nombre null
		try {
			proveedor.setNombreComercial(null);
			fail("No tiro excepcion set con nombre comercial null");
		} catch (Excepciones e1) {
		}
		
		// Set nombre ok
		try {
			proveedor.setNombreComercial("nombre2");
			assertEquals("Prueba nombre set", "nombre2", proveedor.getNombreComercial());
		} catch (Excepciones e) {
			fail("Excepcion en set nombre ok");
		}
	
	// DIRECCION
		// Set direccion vacia
		try {
			proveedor.setDireccion("");
			assertEquals("Direccion string vacio", "", proveedor.getDireccion());
		} catch (Excepciones e) {
			fail("excepcion direccion vacia");
		}
		
		// Set direccion null
		try {
			proveedor.setDireccion(null);
			fail("no lanzo excepcion con direccion null");
		} catch (Excepciones e) {
		}
		
		// Set direccion no vacia
		try {
			proveedor.setDireccion("direccion");
			assertEquals("Direccion string no vacio", "direccion", proveedor.getDireccion());
		} catch (Excepciones e) {
			fail("excepcion direccion no vacia");
		}

	// RAZON SOCIAL	
		// Set razon social vacia
		try {
			proveedor.setRazonSocial("");
			assertEquals("razon social string vacio", "", proveedor.getRazonSocial());
		} catch (Excepciones e) {
			fail("excepcion razon social vacia");
		}
		
		// Set razon social null
		try {
			proveedor.setRazonSocial(null);
			fail("no excepcion razon social null");
		} catch (Excepciones e) {
		}
		
		// Set razon social no vacia
		try {
			proveedor.setRazonSocial("razon social");
			assertEquals("razon social string no vacio", "razon social", proveedor.getRazonSocial());
		} catch (Excepciones e) {
			fail("excepcion razon social no vacia");
		}
	
	// RUT
		// Set rut vacia
		try {
			proveedor.setRUT("");
			assertEquals("rut string vacio", "", proveedor.getRUT());
		} catch (Excepciones e) {
			fail("excepcion rut vacio");
		}
		
		// Set rut null
		try {
			proveedor.setRUT(null);
			fail("no excepcion rut null");
		} catch (Excepciones e) {
		}
		
		// Set rut no vacia
		try {
			proveedor.setRUT("123456");
			assertEquals("rut string no vacio", "123456", proveedor.getRUT());
		} catch (Excepciones e) {
			fail("excepcion rut no vacio");
		}
		
	// TELEFONO
		// Set telefono vacia
		try {
			proveedor.setTelefono("");
			assertEquals("telefono string vacio", "", proveedor.getTelefono());
		} catch (Excepciones e) {
			fail("excepcion telefono vacio");
		}
		
		// Set telefono null
		try {
			proveedor.setTelefono(null);
			fail("no excepcion telefono null");
		} catch (Excepciones e) {
		}
		
		// Set telefono no vacia
		try {
			proveedor.setTelefono("099 02 84 27 - 2604 37 84");
			assertEquals("telefono string no vacio", "099 02 84 27 - 2604 37 84", proveedor.getTelefono());
		} catch (Excepciones e) {
			fail("excepcion no vacia ");
		}
		
	}

}
