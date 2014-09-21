package controladores;

import model.Proveedor;
import interfaces.ISistema;

public class SistemaControlador implements ISistema {

	@Override
	public void altaProveedor(Proveedor proveedor) {
		// TODO: chequeo permisos del usuario
		FabricaLogica.getInstanciaProveedores().altaProveedor(proveedor);
	}

}
