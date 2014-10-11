package controladores;

import java.util.List;

import datatypes.DTProveedor;
import model.Proveedor;
import interfaces.IProveedores;

public class ProveedoresControlador implements IProveedores {
	
	@Override
	public void altaProveedor(Proveedor proveedor) throws Excepciones {
		Boolean existsNombre = false;
		System.out.print("********* NUEVO PROVEEDOR ************\n");
		System.out.print("RUT:" + proveedor.getRUT() + "\n");
		System.out.print("RAZON SOCIAL:" + proveedor.getRazonSocial() + "fin" + "\n");
		System.out.print("TELEFONO:" + proveedor.getTelefono() + "fin" + "\n");
		System.out.print("DIRECCION:" + proveedor.getDireccion() + "fin" + "\n");
		System.out.print("NOMBRE COMERCIAL:" + proveedor.getNombreComercial() + "fin" + "\n");
		System.out.print("TIPO DOCUMENTO:" + proveedor.getTipoDocumento() + "fin" + "\n");
		/*
		if(proveedor.getNombreComercial() == ""){
			throw(new Excepciones("Faltan datos", Excepciones.ERROR_DATOS));
		}*/
		
		// Me fijo si ya existe otro proveedor con este RUT en caso que se haya ingresado algo en el campo
		if((proveedor.getRUT() != "") && (FabricaPersistencia.getInstanciaProveedoresPersistencia().existeProveedorRUT(proveedor.getRUT()))){
			throw(new Excepciones(Excepciones.MENSAJE_RUT_DUPLICADO, Excepciones.ERROR_DATOS));
		}
		System.out.print("ok");
		// Me fijo si ya existe otro proveedor con este nombre para avisarle al usuario
		existsNombre = FabricaPersistencia.getInstanciaProveedoresPersistencia().existeProveedorNombreComercial(proveedor.getNombreComercial());
		System.out.print(existsNombre);
		
		// si los datos eran correctos, continuo a persistirlos
		FabricaPersistencia.getInstanciaProveedoresPersistencia().persistirProveedor(proveedor);
		
		if(existsNombre){
			throw(new Excepciones(Excepciones.MENSAJE_NOMBRE_COMERCIAL_DUPLICADO, Excepciones.ADVERTENCIA_DATOS));
		}
	}

	@Override
	public List<DTProveedor> obtenerProveedores() throws Excepciones {
		return FabricaPersistencia.getInstanciaProveedoresPersistencia().obtenerProveedores();
	}
	
}
