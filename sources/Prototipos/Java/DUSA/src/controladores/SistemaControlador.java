package controladores;

import java.util.List;
import java.util.Map;

import datatypes.DTBusquedaArticulo;
import datatypes.DTLineaPedido;
import datatypes.DTProveedor;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.LineaPedido;
import model.Pedido;
import model.Proveedor;
import interfaces.ISistema;
import interfaces.IStock;

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
	
	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda) throws Excepciones {
		// TODO chequeo permisos del usuario
		return FabricaLogica.getIStock().buscarArticulos(busqueda);
	}

	
	@Override
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior() throws Excepciones {
		// TODO chequeo permisos del usuario
//		return FabricaLogica.getIStock().generarPedidoEnBaseAPedidoAnterior();
		return null;
	}

	@Override
	public Map<Long, Droga> obtenerDrogas() throws Excepciones {
		// TODO chequeo permisos del usuario
		return FabricaLogica.getIStock().obtenerDrogas();
	}

	@Override
	public Map<Long, AccionTer> obtenerAccionesTerapeuticas() throws Excepciones {
		// TODO chequeo permisos del usuario
		return FabricaLogica.getIStock().obtenerAccionesTerapeuticas();
	}
}
