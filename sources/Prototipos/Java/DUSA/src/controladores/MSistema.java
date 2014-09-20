package controladores;

import model.Proveedor;
import interfaces.ISistema;

public class MSistema implements ISistema {

	@Override
	public void altaProveedor(Proveedor proveedor) {
		// TODO: chequear permisos de usuario
		
		FabricaLogica.getInstanciaProveedores().altaProveedor(proveedor);
	}

}
