package controladores;

import model.Proveedor;
import interfaces.IProveedores;

public class ProveedoresControlador implements IProveedores {
	
	@Override
	public void altaProveedor(Proveedor proveedor) {
		System.out.print("********* NUEVO PROVEEDOR ************\n");
		System.out.print("RUT: " + proveedor.getRUT() + "\n");
		System.out.print("RAZON SOCIAL: " + proveedor.getRazonSocial() + "\n");
		System.out.print("TELEFONO: " + proveedor.getTelefono() + "\n");
		System.out.print("DIRECCION: " + proveedor.getDireccion() + "\n");
		System.out.print("NOMBRE COMERCIAL: " + proveedor.getNombreComercial() + "\n");
		
		if(String.valueOf(proveedor.getRUT()).length() < 5){ //FIXME: averiguar largo ok
			//TODO: excepcion
			return;
		}
		if(proveedor.getNombreComercial() == ""){
			//TODO: excepcion
			return;
		}
		
		// si los datos eran correctos, continuo a persistirlos
		//FabricaPersistencia.getInstanciaProveedoresPersistencia().persistirProveedor(proveedor);
	}
	
}
