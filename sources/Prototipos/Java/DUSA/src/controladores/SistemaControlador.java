package controladores;

import java.util.List;

import datatypes.DTLineaPedido;
import model.Proveedor;
import interfaces.ISistema;

public class SistemaControlador implements ISistema {

	@Override
	public void altaProveedor(Proveedor proveedor) throws Excepciones{
		// TODO: chequeo permisos del usuario
		FabricaLogica.getInstanciaProveedores().altaProveedor(proveedor);
	}
	
	public List<DTLineaPedido> pedidoAutomaticoVentas() {
		return FabricaLogica.getIStock().pedidoPorVentas();
	}

}
