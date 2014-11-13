package controladores;

public class Excepciones extends Exception {
	
	private static final long serialVersionUID = 4664456874499611218L;
    
	private int errorCode = 0;
	
	public static int ERROR_DATOS					= 0;
	public static int ADVERTENCIA_DATOS				= 1;
	public static int ERROR_SISTEMA					= 2;
	public static int USUARIO_NO_TIENE_PERMISOS     = 3;
	public static int ERROR_SIN_CONEXION    		= 4;
	
	// Mensajes generales
	public static String MENSAJE_ERROR_SISTEMA					= "Ha ocurrido un error, por favor vuelva a intentarlo.";
	public static String MENSAJE_OK_ALTA						= "El alta ha sido efectuada con éxito.";
	public static String MENSAJE_OK_MODIFICACION				= "La modificación ha sido efectuada con éxito.";
	public static String MENSAJE_ERROR_DATOS					= "Para continuar complete todos los campos obligatorios.";
	
	// Mensajes para Proveedor
	public static String MENSAJE_RUT_DUPLICADO					= "Ya existe un Proveedor en el sistema con ese documento.";
	public static String MENSAJE_RUT_ERRONEO					= "El documento ingresado no es válido.";
	public static String MENSAJE_NOMBRE_COMERCIAL_DUPLICADO		= "Ya existe un Proveedor en el sistema con ese Nombre Comercial.";
     
	// Mensajes para pedido automático
	public static String MENSAJE_OK_PEDIDO						= "El pedido fue enviado con éxito.";
	public static String MENSAJE_PEDIDO_VACIO					= "Primero debes seleccionar un pedido.";
	
	// Mensajes para Artículo
	public static String MENSAJE_ART_DUPLICADO					= "Ya existe un artículo en el sistema con esa descripción.";
	
    // Mensajes para el Login
	public static String USUARIO_INVALIDO						= "El nombre de usuario y/o la contraseña no son válidos.";
	
	//Mensaje de no tiene permisos para realizar dicha operacion 
	public static String MENSAJE_USUARIO_NO_TIENE_PERMISOS 				= "El usuario logueado no tiene permisos para realizar la operacion.";
	
	//Mensajes de conexion con webServices
	public static String MENSAJE_ERROR_CONEXION_WS						= "No se ha establecido conexión. Por favor, intentelo más tarde o verifique su conexión a internet.";
	
	//Mensajes de facturacion
	public static String MENSAJE_FACTURADA_OK					= "Se facturó la venta correctamente.";
	public static String MENSAJE_CANCELADA_OK					= "Se venta ha sido cancelada correctamente.";
	public static String MENSAJE_NO_CORROBORADO_OK				= "Debe corroborar todos los items de la venta.";
	public static String MENSAJE_ERROR_IMPRESION_FACTURA		= "Ha ocurrido un error durante la impresión de la factura.";
	public static String MENSAJE_FACTURA_FACTURADA				= "La venta fue facturada con anterioridad.";
	
	//Mensaje de ingresar compra
	public static String MENSAJE_ERROR_TOTAL					= "El total de la factura no puede ser 0.";
	public static String MENSAJE_DETALLE_VACIO					= "Es necesario ingresar al menos un artículo.";
	public static String MENSAJE_FACTURA_DUPLICADA				= "Ya existe en el sistema una factura con el mismo tipo, serie y numero.";
	public static String MENSAJE_TIPO_REQUERIDO					= "Para continuar es necesario ingeresar el tipo de factura";
	public static String MENSAJE_SERIE_REQUERIDO				= "Para continuar es necesario ingeresar la serie de factura";
	public static String MENSAJE_NUMERO_REQUERIDO				= "Para continuar es necesario ingeresar el numero de factura";
	public static String MENSAJE_PROVEEDOR_VACIO				= "Para continuar es necesario seleccionar un proveedor";
	public static String MENSAJE_ERROR_FECHA					= "La fecha seleccionada no puede ser posterior a la fecha actual.";
	public static String MENSAJE_COMPRA_OK						= "La compra ha sido realizada con éxito.";
	
	public Excepciones(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;
    }
     
    public int getErrorCode(){
        return this.errorCode;
    }
}
