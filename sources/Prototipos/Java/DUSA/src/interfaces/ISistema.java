package interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import controladores.Excepciones;
import datatypes.DTBusquedaArticulo;
import datatypes.DTComprobanteFactura;
import datatypes.DTLineaPedido;
import datatypes.DTModificacionArticulo;
import datatypes.DTProveedor;
import datatypes.DTTiposDGI;
import datatypes.DTProducto;
import datatypes.DTVencimiento;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Orden;
import model.Pedido;
import model.Proveedor;
import model.TipoIva;
import model.Usuario;
import model.Venta;

public interface ISistema {

	public void altaProveedor(Proveedor proveedor) throws Excepciones;

	/**
	 * Se encarga de dar de alta un nuevo artículo en el sistema.
	 * 
	 * @param articulo
	 *            Artículo a persistir.
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author José Aguerre
	 */
	public void altaArticulo(Articulo articulo) throws Excepciones;

	/**
	 * Modifica un articulo del sistema
	 * 
	 * @param DTModificacionArticulo
	 *            - Datatype que contiene el artículo y aparte listas de
	 *            proveedores, drogas y acciones terapeuticas con las
	 *            modificaciones necesarias.
	 * @throws Excepciones
	 *             ERROR_SISTEMA (en caso de error a la hora de persistir en la
	 *             base de datos)
	 * @author Jmaguerre
	 * 
	 */
	public void modificarArticulo(DTModificacionArticulo articulo)
			throws Excepciones;

	/**
	 * Retorna todos los proveedores activos existentes en el sistema.
	 * 
	 * @return Devuelve un Map de DTProveedor con todos los proveedores del
	 *         sistema
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author José Aguerre
	 */
	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones;

	/**
	 * @author Jmaguerre
	 * @return Devuelve una lista con todas las drogas del sistema.
	 */
	public List<Droga> obtenerDrogas() throws Excepciones;

	/**
	 * @author Jmaguerre
	 * @return Devuelve una lista con todas las acciones terapéuticas del
	 *         sistema.
	 */
	public List<AccionTer> obtenerAccionesTerapeuticas() throws Excepciones;

	/**
	 * realiza la indexación de todos los articulos
	 * 
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */

	/**
	 * Genera un pedido de artículos de D.U.S.A. La cantidad de cada artículo es
	 * igual a la cantidad vendida, del artículo, desde el pedido anterior.
	 * 
	 * @author Guille
	 * @return Lista de dataType LineaPedido con la informacion del articulo y
	 *         su cantidad
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> generarPedidoEnBaseAPedidoAnterior()
			throws Excepciones;

	/**
	 * Genera un pedido de artículos de D.U.S.A. Donde la cantidad de cada
	 * articulo se obtiene de forma predictiva tomando en cuenta mismo periodo
	 * de años anteriores y estimando los próximos días por medio de mínimos
	 * cuadrados.
	 * 
	 * @author Santiago
	 * @author Guille
	 * @param diasAPredecir
	 *            cantidad de días a predecir.
	 * @return Lista de dataType LineaPedido con la información del articulo y
	 *         su cantidad
	 * @throws Excepciones
	 */
	public List<DTLineaPedido> generarPedidoEnBaseAHistorico(int diasAPredecir)
			throws Excepciones;

	/**
	 * Se conecta con D.U.S.A. para realizar el pedido, se persiste el pedido si
	 * el envío es realizado con éxito.
	 * 
	 * @author Guille
	 * @param pedido
	 * @throws Excepciones
	 *             ERROR_SIN_CONEXION si hay error al realizar la conexión.
	 *             ERROR_SISTEMA si hay error con la base de datos.
	 */
	public void realizarPedido(Pedido pedido) throws Excepciones;

	/**
	 * @author santiago
	 * @throws Excepciones
	 */
	public void iniciarSesion(String nombreUsuario, String contrasenia)
			throws Excepciones;

	public void cerrarSesion(String nombreUsuario, String contrasenia);

	/**
	 * Busca los articulos que coinciden con el string buscar y devuelve los
	 * datos necesarios para la venta
	 * 
	 * @param busqueda
	 *            - string ingresado
	 * @return List<DTVenta> Lista de articulos encontrados
	 * @throws Excepciones
	 * @author Victoria Diaz
	 */
	public List<DTProducto> buscarArticulosVenta(String busqueda)
			throws Excepciones;
	
	/**
	 * Busca el articulo con el codigo ingresado y devuelve los
	 * datos necesarios para la venta
	 * 
	 * @param codigo
	 *            - string ingresado
	 * @return List<DTVenta> Lista de articulos encontrados
	 * @throws Excepciones
	 * @author Sebastian Caballero
	 */
	public DTProducto buscarArticulosPorCodigo(String codigo)
			throws Excepciones;

	/**
	 * Retorna todos los proveedores activos existentes en el sistema marcados
	 * como marca o laboratorio.
	 * 
	 * @return Devuelve un List de DTProveedor con todos los proveedores del
	 *         sistema marcados como marca o laboratorio.
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author José Aguerre
	 */
	public long registrarNuevaVenta(Venta v) throws Excepciones;
	
	public long registrarVentaPerdida(Venta v) throws Excepciones;

	/**
	 * Registra en el sistema la venta v
	 * 
	 * @param v
	 *            - lista de DTVenta que componen la venta a registrar
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author Ignacio Rodriguez
	 */
	public List<DTProveedor> obtenerMarcas() throws Excepciones;

	/**
	 * retorna los articulos que coincidan con el string ingresado
	 * 
	 * @param busqueda
	 * @return List<DTBusquedaArticulo> lista de los articulos encontrados segun
	 *         el texto ingresado con todos los campos necesarios para el caso
	 *         de uso busqueda articulos
	 * @throws Excepciones
	 * @author Victoria Diaz
	 */
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda)
			throws Excepciones;

	/**
	 * retorna los articulos que coincidan con el string ingresado
	 * 
	 * @param busqueda
	 *            - string a buscar
	 * @param proveedor
	 *            - buscar articulos de determinado proveedor
	 * @return List<DTBusquedaArticulo> lista de los articulos encontrados segun
	 *         el texto ingresado con todos los campos necesarios para el caso
	 *         de uso busqueda articulos
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda,
			long proveedor) throws Excepciones;

	/**
	 * Chequea la existencia del codigoIdentificador para el proveedor.
	 * 
	 * @return True si existe un articulo con ese codigo para el proveedor.
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author José Aguerre
	 * @param idProveedor
	 * @param codigoIdentificador
	 */
	public boolean existeCodigoParaProveedor(long idProveedor,
			long codigoIdentificador) throws Excepciones;

	/**
	 * Retorna los distintos tipos de iva existentes en el sistema.
	 * 
	 * @return List<TipoIva> lista de los distintos tipos de iva existentes en
	 *         el sistema.
	 * @throws Excepciones
	 * @author José Aguerre
	 */
	public List<TipoIva> obtenerTiposIva() throws Excepciones;

	public Usuario obtenerUsuarioLogueado();

	/**
	 * Modifica el stock del articulo con id idArticulo al valor nuevoValor
	 * 
	 * @param idArticulo
	 * @param nuevoValor
	 * @param registroCantidad
	 *            cantidad asociada al tipo de movimiento
	 * @param char tipoMovimientoDeStock que indica aumento, baja o desarme de
	 *        stock
	 * @param motivo
	 *            Motivo por el cual se efectúa la modificación.
	 * @see model.Enumerados.tipoMovimientoDeStock
	 * @throws Excepciones
	 */
	public void modificarStock(long idArticulo, long nuevoValor,
			long registroCantidad, char tipoMovimiento, String motivo)
			throws Excepciones;

	/**
	 * Ingresa una factura de compra al sistema
	 * 
	 * @param orden
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public void ingresarFacturaCompra(Orden orden) throws Excepciones;

	/**
	 * Obtiene los tipos de las facturas de la base de datos
	 * 
	 * @return lista con las distintas formas de venta
	 * @author Victoria Díaz
	 * @throws Excepciones
	 */
	public Map<Integer, DTTiposDGI> obtenerTiposDGI() throws Excepciones;

	/**
	 * trae las facturas por el servicio de DUSA
	 * 
	 * @return lista de facturas de DUSA automaticas
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public Map<Long, DTComprobanteFactura> obtenerFacturasDUSA()
			throws Excepciones;

	/**
	 * Devuelve un artículo con todos sus datos, sus proveedores, drogas y
	 * acciones terapéuticas.
	 * 
	 * @param idArticulo
	 *            - Identificador del artículo.
	 * @return Articulo - Entidad de modelo con los datos del artículo de id
	 *         idArticulo.
	 * @throws Excepciones
	 * @author José Aguerre
	 */
	public Articulo obtenerArticulo(int idArticulo) throws Excepciones;

	// public void actualizarStock(Date fecha) throws Exception;
	/**
	 * Se obtiene una lista de ventas pendientes de facturacion
	 * 
	 * @author Seba
	 * @return
	 * @throws Excepciones
	 */
	public List<Venta> listarVentasPendientes() throws Excepciones;

	/**
	 * Se realiza la facturacion, con la baja de stock correspondiente, de la
	 * venta con id "idVenta"
	 * 
	 * @author Seba
	 * @param idVenta
	 * @throws Excepciones
	 */
	public boolean facturarVentaPendiente(long idVenta) throws Excepciones;

	/**
	 * Se cancela la venta pendiente con id "idVenta"
	 * 
	 * @author Seba
	 * @param venta
	 * @throws Excepciones
	 */
	public void cancelarVentaPendiente(long idVenta) throws Excepciones;

	/**
	 * Obtener los articulos del proveedor
	 * 
	 * @author Juanma
	 * @param id
	 *            del proveedor
	 * @throws Excepciones
	 * */
	public List<Articulo> obtenerArticulosDelProveedor(long idProveedor)
			throws Excepciones;

	/**
	 * Para cada 'idArticulo' se le modifica el precio unitario al valor
	 * 'nuevoPrecio'
	 * 
	 * @param articulosInfo
	 *            Diccionario (idArticulo, nuevoPrecio)
	 * @throws Excepciones
	 */
	public void modificarPrecioArticulos(Map<Long, BigDecimal> articulosInfo)
			throws Excepciones;

	/**
	 * Devuelve una lista con información de artículos los que su fecha de
	 * vencimiento más cercano está entre desde y hasta
	 * 
	 * @param desde
	 * @param hasta
	 * @return
	 * @throws Excepciones
	 */
	public List<DTVencimiento> articulosQueSeVencenEnPeriodo(Date desde,
			Date hasta) throws Excepciones;

	/**
	 * Recibe un map con los cambios de vencimientos a realizar y los periste.
	 * @param cambios
	 * @throws Excepciones
	 */
	public void modificarVencimientosDeArticulos(Map<Long, Date> cambios) throws Excepciones;
}
