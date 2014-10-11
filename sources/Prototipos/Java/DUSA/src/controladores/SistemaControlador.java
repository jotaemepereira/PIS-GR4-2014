package controladores;

import java.util.List;
import java.util.Map;

import datatypes.DTLineaPedido;
import datatypes.DTProveedor;
import model.Articulo;
import model.Proveedor;
import interfaces.ISistema;

public class SistemaControlador implements ISistema {

	@Override
	public void altaProveedor(Proveedor proveedor) throws Excepciones{
		// TODO: chequeo permisos del usuario
		FabricaLogica.getInstanciaProveedores().altaProveedor(proveedor);
	}
	
	public List<DTLineaPedido> pedidoAutomaticoVentas() {
		return null;//FabricaLogica.getIStock().pedidoPorVentas(); Deprecated por ahora
	}

	@Override
	public void altaArticulo(Articulo articulo) throws Excepciones {
		// TODO: chequeo permisos del usuario
		FabricaLogica.getIStock().altaArticulo(articulo);
		
	}

	@Override
	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones {
		// TODO chequeo permisos del usuario
		return FabricaLogica.getInstanciaProveedores().obtenerProveedores();
	}

}
