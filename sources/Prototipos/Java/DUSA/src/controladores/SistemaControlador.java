package controladores;

import java.util.List;
import java.util.Map;

import datatypes.DTBusquedaArticulo;
import datatypes.DTComprobanteFactura;
import datatypes.DTLineaPedido;
import datatypes.DTModificacionArticulo;
import datatypes.DTProveedor;
import datatypes.DTTiposDGI;
import datatypes.DTVenta;
import model.AccionTer;
import model.Actividad;
import model.Articulo;
import model.Droga;
import model.Operacion;
import model.Orden;
import model.Pedido;
import model.Enumerados.casoDeUso;
import model.TipoIva;
import model.Usuario;
import model.Proveedor;
import model.Venta;
import interfaces.ISistema;

public class SistemaControlador implements ISistema {

	public Usuario user;

	@Override
	public void altaProveedor(Proveedor proveedor) throws Excepciones{
		
		if (user.tienePermiso(casoDeUso.altaProveedor)){
			
			try {
				
				FabricaLogica.getInstanciaProveedores().altaProveedor(proveedor);
				//Registro actividad del usuario
				
				Operacion operacion = user.getOperacion(casoDeUso.altaProveedor);
				Actividad act = new Actividad(operacion, user.getNombre());
				
				FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
			} catch (Excepciones e) {
				// TODO: handle exception
				
				if (e.getErrorCode() == Excepciones.ADVERTENCIA_DATOS) {
					//Dado que es una advertencia, la actividad fue efectuada y se debe persistir.
					
					Operacion operacion = user.getOperacion(casoDeUso.altaProveedor);
					Actividad act = new Actividad(operacion, user.getNombre());
					
					FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
				}
				
				throw e;
			}
		}else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
	}

	@Override
	public void altaArticulo(Articulo articulo) throws Excepciones {
		if (user.tienePermiso(casoDeUso.altaArticulo)){
			FabricaLogica.getIStock().altaArticulo(articulo);
		}
		else{
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		}

	}

	@Override
	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones {
		//*este caso de uso no aparece con el mismo nombre en el acta
		//if (user.tienePermiso(casoDeUso.buscarProveedor))
		return FabricaLogica.getInstanciaProveedores().obtenerProveedores();
		//else
		//	throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));

	}

//	public void actualizarStock(Date fecha) {
//		System.out.println("actualizarStock");
//		try {
//			FabricaLogica.getIStock().actualizarStock(fecha);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda) throws Excepciones {
		// TODO chequeo permisos del usuario
		//if (user.tienePermiso(casoDeUso.buscarArticulo))
		return FabricaLogica.getIStock().buscarArticulos(busqueda);
		//else
		//	throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));

	}

	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda, int proveedor) throws Excepciones {
		// TODO chequeo permisos del usuario
		//if (user.tienePermiso(casoDeUso.buscarArticulo))
		return FabricaLogica.getIStock().buscarArticulos(busqueda, proveedor);
		//else
		//	throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));

	}
	
	@Override
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior() throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.generPeEnBaseAPedAnt))
			
			return FabricaLogica.getIStock().generarPedidoEnBaseAPedidoAnterior();
		else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
	}

	@Override
	public void realizarPedido(Pedido pedido) throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.realizarPedido)) {
			
			FabricaLogica.getIStock().realizarPedido(pedido);
		} else {
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		}
	}

	@Override
	public List<DTLineaPedido> generarPedidoEnBaseAHistorico(int diasAPredecir) throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.genPedEnBaseAHist))
			
			return FabricaLogica.getIStock().generarPedidoEnBaseAHistorico(diasAPredecir);
		else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
	}

	@Override
	public List<Droga> obtenerDrogas() throws Excepciones {
		// NO ESTA EN EL ACTA 
		return FabricaLogica.getIStock().obtenerDrogas();
	}

	@Override
	public List<AccionTer> obtenerAccionesTerapeuticas() throws Excepciones {
		// NO ESTA EN ELACTA
		return FabricaLogica.getIStock().obtenerAccionesTerapeuticas();
	}   
	@Override
	public void iniciarSesion(String nombreUsuario, String contrasenia) throws Excepciones{
		this.user = FabricaPersistencia.getInstanciaUsuaruiPersistencia().getUsuario(nombreUsuario, contrasenia);
		if (user.getNombre() == null)
			throw(new Excepciones(Excepciones.USUARIO_INVALIDO, Excepciones.ERROR_DATOS)); 
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
		// NO ESTA EN EL ACTA 
		return FabricaLogica.getInstanciaProveedores().obtenerMarcas();
	}

	@Override
	public boolean existeCodigoParaProveedor(long idProveedor,
			long codigoIdentificador) throws Excepciones {
		return FabricaLogica.getInstanciaProveedores().existeCodigoParaProveedor(idProveedor,codigoIdentificador);
	}

	@Override
	public long registrarNuevaVenta(Venta v) throws Excepciones {
		//if (user.tienePermiso(casoDeUso.buscarArticulo))
		return FabricaLogica.getIFacturacion().registrarNuevaVenta(v);

		//else
		//	throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));

	}

	@Override
	public List<TipoIva> obtenerTiposIva() throws Excepciones {
		//actualizarStock();
		return FabricaLogica.getIStock().obtenerTiposIva();

	}

	@Override
	public Usuario obtenerUsuarioLogueado() {
		// TODO Auto-generated method stub
		return user;

	}
	
	@Override
	public void modificarStock(long idArticulo, long nuevoValor, long registroCantidad, char tipoMovimiento, String motivo) throws Excepciones {
	
		if (user.tienePermiso(casoDeUso.modificarStock)) {
			
			FabricaLogica.getIStock().modificarStock(idArticulo, nuevoValor);
			
			try {
				FabricaPersistencia.getStockPersistencia().movimientoStock(user.getNombre(), idArticulo, registroCantidad, tipoMovimiento, motivo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA);
			}
		} else {
			
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		}
	}

	@Override
	public void modificarArticulo(DTModificacionArticulo articulo) throws Excepciones {
		FabricaLogica.getIStock().modificarArticulo(articulo);
		
	}

	@Override
	public void ingresarFacturaCompra(Orden orden) throws Excepciones {
		// TODO Auto-generated method stub
		FabricaLogica.getInstanciaCompras().ingresarFacturaCompra(orden);
	}

	@Override
	public Map<Integer, DTTiposDGI> obtenerTiposDGI() throws Excepciones {
		return FabricaLogica.getInstanciaCompras().obtenerTiposDGI();
	}

	@Override
	public Map<Long, DTComprobanteFactura> obtenerFacturasDUSA() throws Excepciones {
		return FabricaLogica.getInstanciaCompras().obtenerFacturasDUSA();
	}

	@Override
	public Articulo obtenerArticulo(int idArticulo) throws Excepciones {
		return FabricaLogica.getIStock().obtenerArticulo(idArticulo);
	}
	
	@Override
	public List<Venta> listarVentasPendientes() throws Excepciones {
		
		List<Venta> vPendientes = null;
		if (user.tienePermiso(casoDeUso.listarVentasPendientes)) {
			


			try {
				vPendientes = FabricaLogica.getIFacturacion().listarVentasPendientes();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		} else {
			
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		}
		
		return vPendientes;
	}
	
	@Override
	public boolean facturarVentaPendiente(long idVenta) throws Excepciones {
		if (user.tienePermiso(casoDeUso.facturarVentaPendiente)) {
			
			boolean ret = FabricaLogica.getIFacturacion().facturarVenta(idVenta);
			
			Operacion operacion = user.getOperacion(casoDeUso.facturarVentaPendiente);
			Actividad act = new Actividad(operacion, user.getNombre());
			
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
			return ret;
		} else {
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		}
	}
	
	@Override
	public void cancelarVentaPendiente(long idVenta) throws Excepciones {
	
		if (user.tienePermiso(casoDeUso.cancelarVentaPendiente)) {
			
			FabricaLogica.getIFacturacion().facturarVenta(idVenta);
		} else {
			
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		}
		
	}

	@Override
	public List<Articulo> obtenerArticulosDelProveedor(long idProveedor)
			throws Excepciones {
	 return FabricaLogica.getIStock().obtenerArticulosDelProveedor(idProveedor);
	}

}
