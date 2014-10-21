package controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import datatypes.DTBusquedaArticulo;
import datatypes.DTLineaPedido;
import datatypes.DTProveedor;
import datatypes.DTVenta;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Enumerados.casoDeUso;
import model.Usuario;
import model.Proveedor;
import interfaces.ISistema;

public class SistemaControlador implements ISistema {
	
	public Usuario user;

	@Override
	public void altaProveedor(Proveedor proveedor) throws Excepciones{
		// TODO: chequeo permisos del usuario
		//if (user.tienePermiso(casoDeUso.altaProveedor))
			FabricaLogica.getInstanciaProveedores().altaProveedor(proveedor);
		//else
		//	throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
	}

	@Override
	public void altaArticulo(Articulo articulo) throws Excepciones {
		//if (user.tienePermiso(casoDeUso.altaArticulo))
			FabricaLogica.getIStock().altaArticulo(articulo);
		//else
		//	throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		
	}

	@Override
	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones {
		//*este caso de uso no aparece con el mismo nombre en el acta
		//if (user.tienePermiso(casoDeUso.buscarProveedor))
			return FabricaLogica.getInstanciaProveedores().obtenerProveedores();
		//else
		//	throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		
	}
	
	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda) throws Excepciones {
		// TODO chequeo permisos del usuario
		//if (user.tienePermiso(casoDeUso.buscarArticulo))
			return FabricaLogica.getIStock().buscarArticulos(busqueda);
		//else
		//	throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		
	}

	
	@Override
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior() throws Excepciones {
		//if (user.tienePermiso(casoDeUso.generPeEnBaseAPedAnt))
			return FabricaLogica.getIStock().generarPedidoEnBaseAPedidoAnterior();
		//else
		//	throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		
	}
	
	@Override
	public List<DTLineaPedido> generarPedidoEnBaseAHistorico(int diasAPredecir) throws Excepciones {
		//if (user.tienePermiso(casoDeUso.genPedEnBaseAHist))
			return FabricaLogica.getIStock().generarPedidoEnBaseAHistorico(diasAPredecir);
		//else
		//	throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		
	}

	@Override
	public List<Droga> obtenerDrogas() throws Excepciones {
		// ESTE NO ESTA EN EL ACTA 
			return FabricaLogica.getIStock().obtenerDrogas();
	}

	@Override
	public List<AccionTer> obtenerAccionesTerapeuticas() throws Excepciones {
		// TAMPOCO ESTA EN ELACTA
			return FabricaLogica.getIStock().obtenerAccionesTerapeuticas();
	}
	@Override
	public void iniciarSesion(String nombreUsuario, String contrasenia){
		
	}
	@Override
	public void cerrarSesion(String nombreUsuario, String contrasenia){
		
	}
	@Override
	public List<DTVenta> buscarArticulosVenta(String busqueda) throws Excepciones {
		System.out.println("********* BUSCAR ************** " + busqueda);
		//if (user.tienePermiso(casoDeUso.buscarArticulo))
			return FabricaLogica.getIStock().buscarArticulosVenta(busqueda);
		//else
		//	throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		
	}

	@Override
	public List<DTProveedor> obtenerMarcas() throws Excepciones {
		// TAMPOCO ESTA EN EL ACTA 
		return FabricaLogica.getInstanciaProveedores().obtenerMarcas();
	}
}
