package controladores;

import model.Proveedor;
import interfaces.IProveedores;

public class ProveedoresControlador implements IProveedores {
	
	@Override
	public void altaProveedor(Proveedor proveedor) {
		if(proveedor.getRUT() == 0){
			//TODO: error y salir
		}
		if(proveedor.getNombreComercial() == ""){
			//TODO: error y salir
		}
		
		// si los datos eran correctos, continuo a persistirlos
		FabricaPersistencia.getInstanciaProveedoresPersistencia().persistirProveedor(proveedor);
	}
	
}
