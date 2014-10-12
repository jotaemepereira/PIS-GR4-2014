package persistencia;

import static org.junit.Assert.*;
import model.Proveedor;

import org.junit.Test;

import controladores.Excepciones;
import controladores.FabricaPersistencia;

public class PProveedoresControladorTest {

	@Test
	public void test() {
		Proveedor proveedor;
		
		// Inicializo el proveedor
		try {
			proveedor = new Proveedor("farmacia");
		} catch (Excepciones e) {
			fail("Excepcion al crear proveedor");
			return;
		}
		
		// seteo los valores iniciales vacios
		try {
			proveedor.setDireccion("");
		} catch (Excepciones e) {
			fail("Excepcion setDireccion (1)");
			return;
		}
		try {
			proveedor.setRazonSocial("");
		} catch (Excepciones e) {
			fail("Excepcion setRazonSocial (1)");
			return;
		}
		try {
			proveedor.setRUT("");
		} catch (Excepciones e) {
			fail("Excepcion setRUT (1)");
			return;
		}
		try {
			proveedor.setTelefono("");
		} catch (Excepciones e) {
			fail("Excepcion setTelefono (1)");
			return;
		}
		
		// Guardo el proveedor
		try {
			FabricaPersistencia.getInstanciaProveedoresPersistencia().persistirProveedor(proveedor);
		} catch (Excepciones e1) {
			fail("Excepcion persistencia proveedor");
			return;
		}
		
		// Verifico que exista
		try {
			boolean existe = FabricaPersistencia.getInstanciaProveedoresPersistencia().existeProveedorNombreComercial(proveedor.getNombreComercial());
			if(!existe){
				fail("No existe nombre comercial (1)");
				return;
			}
		} catch (Excepciones e) {
			fail("Excepcion existe proveedor (1)");
			return;
		}
		
		
		// Seteo nuevos valores a los campos
		try {
			proveedor.setNombreComercial("proveedor");
		} catch (Excepciones e1) {
			fail("Excepcion setNombreComercial (2)");
			return;
		}
		try {
			proveedor.setDireccion("Herrera y Reissig");
		} catch (Excepciones e) {
			fail("Excepcion setDireccion (2)");
			return;
		}
		try {
			proveedor.setRazonSocial("fing");
		} catch (Excepciones e) {
			fail("Excepcion setRazonSocial (2)");
			return;
		}
		try {
			proveedor.setRUT("123456");
		} catch (Excepciones e) {
			fail("Excepcion setRUT (2)");
			return;
		}
		try {
			proveedor.setTelefono("26013485 - 24003698");
		} catch (Excepciones e) {
			fail("Excepcion setTelefono (2)");
			return;
		}
		
		// Persisto el nuevo proveedor
		try {
			FabricaPersistencia.getInstanciaProveedoresPersistencia().persistirProveedor(proveedor);
		} catch (Excepciones e) {
			fail("Excepcion persistir proveedor (2)");
			return;
		}
		
		// Verifico que el nuevo proveedor exista
		try {
			boolean existe = FabricaPersistencia.getInstanciaProveedoresPersistencia().existeProveedorNombreComercial(proveedor.getNombreComercial());
			if(!existe){
				fail("No existe nombre comercial (2)");
				return;
			}
		} catch (Excepciones e) {
			fail("Excepcion existe nombre comercial (2)");
			return;
		}
		
		// verifico que exista el RUT ingresado
		try {
			boolean existe = FabricaPersistencia.getInstanciaProveedoresPersistencia().existeProveedorRUT(proveedor.getRUT());
			if(!existe){
				fail("No existe RUT");
				return;
			}
		} catch (Excepciones e) {
			fail("Excepcion existe RUT");
		}
		
	}

}
