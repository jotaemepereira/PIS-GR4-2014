/**
 * Controlador que implementa la capa de seguridad y registro de operaciones de usuario del sistema
 */
package controladores;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import datatypes.DTBusquedaArticulo;
import datatypes.DTComprobanteFactura;
import datatypes.DTLineaPedido;
import datatypes.DTModificacionArticulo;
import datatypes.DTProveedor;
import datatypes.DTTiposDGI;
import datatypes.DTProducto;
import datatypes.DTVencimiento;
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
			
			//Registro actividad del usuario
			Operacion operacion = user.getOperacion(casoDeUso.altaArticulo);
			Actividad act = new Actividad(operacion, user.getNombre());
			
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
		} else {
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		}

	}

	@Override
	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.obtenerProveedores))
			return FabricaLogica.getInstanciaProveedores().obtenerProveedores();
		else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
	}

	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda) throws Excepciones {

		if (user.tienePermiso(casoDeUso.buscarArticulo))
			return FabricaLogica.getIStock().buscarArticulos(busqueda);
		else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));

	}

	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda, long proveedor) throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.buscarArticulo))
			return FabricaLogica.getIStock().buscarArticulos(busqueda, proveedor);
		else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));

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
			
			//Registro actividad del usuario
			Operacion operacion = user.getOperacion(casoDeUso.realizarPedido);
			Actividad act = new Actividad(operacion, user.getNombre());
			
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
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
		
		if (user.tienePermiso(casoDeUso.obtenerDrogas)) 
			return FabricaLogica.getIStock().obtenerDrogas();
		 else 
			 throw new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS);
	}

	@Override
	public List<AccionTer> obtenerAccionesTerapeuticas() throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.obtenerDrogas)) 
			return FabricaLogica.getIStock().obtenerAccionesTerapeuticas();
		 else 
			 throw new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS);
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
	public List<DTProducto> buscarArticulosVenta(String busqueda) throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.buscarArticulo))
			return FabricaLogica.getIStock().buscarArticulosVenta(busqueda);
		else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));

	}
	
	@Override
	public DTProducto buscarArticulosPorCodigo(String codigo)throws Excepciones {
		if (user.tienePermiso(casoDeUso.buscarArticulo))
			return FabricaLogica.getIStock().buscarArticulosVentaPorCodigo(codigo);
		else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));

	}

	@Override
	public List<DTProveedor> obtenerMarcas() throws Excepciones {

		List<DTProveedor> marcas = null;
		if (user.tienePermiso(casoDeUso.obtenerMarcas)) {
			
			marcas = FabricaLogica.getInstanciaProveedores().obtenerMarcas();
		} else {
			
			throw new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS);
		}
		
		return marcas;
	}

	@Override
	public boolean existeCodigoParaProveedor(long idProveedor, long codigoIdentificador) throws Excepciones {
		
		boolean existe = false;
		if (user.tienePermiso(casoDeUso.existeCodigoProveedor)) {
			
			existe = FabricaLogica.getInstanciaProveedores().existeCodigoParaProveedor(idProveedor,codigoIdentificador);
		} else {
			
			throw new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS);
		}
		
		return existe;
	}

	@Override
	public long registrarNuevaVenta(Venta v) throws Excepciones {
		if (user.tienePermiso(casoDeUso.registrarNuevaVenta)) {
			
			long idVenta = FabricaLogica.getIFacturacion().registrarNuevaVenta(v);
			
			//Registro actividad del usuario
			Operacion operacion = user.getOperacion(casoDeUso.registrarNuevaVenta);
			Actividad act = new Actividad(operacion, user.getNombre());
			
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
			
			return idVenta;
		} else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));

	}
	
	@Override
	public long registrarVentaPerdida(Venta v) throws Excepciones {
		if (user.tienePermiso(casoDeUso.registrarVentaPerdida)) {
			
			long idVenta = FabricaLogica.getIFacturacion().registrarNuevaVenta(v);
			
			//Registro actividad del usuario
			Operacion operacion = user.getOperacion(casoDeUso.registrarVentaPerdida);
			Actividad act = new Actividad(operacion, user.getNombre());
			
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
			
			return idVenta;
		} else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));

	}

	@Override
	public List<TipoIva> obtenerTiposIva() throws Excepciones {
		//actualizarStock();
		if (user.tienePermiso(casoDeUso.obtenerTiposIva))
			return FabricaLogica.getIStock().obtenerTiposIva();
		else
			throw new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS);

	}

	@Override
	public Usuario obtenerUsuarioLogueado() {
		
		return user;
	}
	
	@Override
	public void modificarStock(long idArticulo, long nuevoValor, long registroCantidad, char tipoMovimiento, String motivo) throws Excepciones {
	
		if (user.tienePermiso(casoDeUso.modificarStock)) {
			
			FabricaLogica.getIStock().modificarStock(idArticulo, nuevoValor);
			
			FabricaPersistencia.getStockPersistencia().movimientoStock(user.getNombre(), idArticulo, registroCantidad, tipoMovimiento, motivo);
			
			//Registro actividad del usuario
			Operacion operacion = user.getOperacion(casoDeUso.modificarStock);
			Actividad act = new Actividad(operacion, user.getNombre());
			
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
		} else {
			
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		}
	}

	@Override
	public void modificarArticulo(DTModificacionArticulo articulo) throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.modificarArticulo)) {
			
			FabricaLogica.getIStock().modificarArticulo(articulo);
			
			//Registro actividad del usuario
			Operacion operacion = user.getOperacion(casoDeUso.modificarArticulo);
			Actividad act = new Actividad(operacion, user.getNombre());
			
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
		} else {
			
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		}
	}

	@Override
	public void ingresarFacturaCompra(Orden orden) throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.ingresarFacturaCompra)){
			
			orden.setNombreUsuario(user.getNombre());
			FabricaLogica.getInstanciaCompras().ingresarFacturaCompra(orden);
			
			//Registro actividad del usuario
			Operacion operacion = user.getOperacion(casoDeUso.ingresarFacturaCompra);
			Actividad act = new Actividad(operacion, user.getNombre());
			
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
		
		} else
			throw new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS);
	}

	@Override
	public Map<Integer, DTTiposDGI> obtenerTiposDGI() throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.obtenerTiposDGI))
			return FabricaLogica.getInstanciaCompras().obtenerTiposDGI();
		else
			throw new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS);
	}

	@Override
	public Map<Long, DTComprobanteFactura> obtenerFacturasDUSA() throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.obtenerFacturasDUSA))
			return FabricaLogica.getInstanciaCompras().obtenerFacturasDUSA(user.getNombre());
		else
			throw new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS);
	}

	@Override
	public Articulo obtenerArticulo(int idArticulo) throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.obtenerArticulo))
			return FabricaLogica.getIStock().obtenerArticulo(idArticulo);
		else
			throw new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS);
	}
	
	@Override
	public List<Venta> listarVentasPendientes() throws Excepciones {
		
		List<Venta> vPendientes = null;
		if (user.tienePermiso(casoDeUso.listarVentasPendientes)) {

			vPendientes = FabricaLogica.getIFacturacion().listarVentasPendientes();
		} else {
			
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		}
		
		return vPendientes;
	}
	
	@Override
	public boolean facturarVentaPendiente(long idVenta) throws Excepciones {
		if (user.tienePermiso(casoDeUso.facturarVentaPendiente)) {
			
			boolean ret = FabricaLogica.getIFacturacion().facturarVenta(idVenta);
			
			//Registro actividad del usuario
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
			
			FabricaLogica.getIFacturacion().cancelarVenta(idVenta);
			
			//Registro actividad del usuario
			Operacion operacion = user.getOperacion(casoDeUso.cancelarVentaPendiente);
			Actividad act = new Actividad(operacion, user.getNombre());
			
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
		} else {
			
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
		}		
	}

	@Override
	public List<Articulo> obtenerArticulosDelProveedor(long idProveedor)
			throws Excepciones {
	 return FabricaLogica.getIStock().obtenerArticulosDelProveedor(idProveedor);
	}
	
	
	@Override
	public void modificarPrecioArticulos(Map<Long, BigDecimal> articulosInfo)
			throws Excepciones {
		
		if (user.tienePermiso(casoDeUso.modificarPrecioArticulo)) {
			
			FabricaLogica.getIStock().modificarPreciodeArticulos(articulosInfo);
			
			//Registro actividad del usuario
			Operacion operacion = user.getOperacion(casoDeUso.modificarPrecioArticulo);
			Actividad act = new Actividad(operacion, user.getNombre());
			
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
		} else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
	}

	@Override
	public List<DTVencimiento> articulosQueSeVencenEnPeriodo(Date desde,
			Date hasta) throws Excepciones {
		if (user.tienePermiso(casoDeUso.alertaVencimiento)){
			return FabricaLogica.getIStock().articulosQueSeVencenEnPeriodo(desde, hasta);
		} else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
	}

	@Override
	public void modificarVencimientosDeArticulos(Map<Long, Date> cambios)
			throws Excepciones {
		if (user.tienePermiso(casoDeUso.alertaVencimiento)){
			
			FabricaLogica.getIStock().modificarVencimientosDeArticulos(cambios);
			
			//Registro actividad del usuario
			Operacion operacion = user.getOperacion(casoDeUso.alertaVencimiento);
			Actividad act = new Actividad(operacion, user.getNombre());			
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
		} else
			throw(new Excepciones(Excepciones.MENSAJE_USUARIO_NO_TIENE_PERMISOS, Excepciones.USUARIO_NO_TIENE_PERMISOS));
	}

}
