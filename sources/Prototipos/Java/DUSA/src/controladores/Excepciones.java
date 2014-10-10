package controladores;

public class Excepciones extends Exception {
	private static final long serialVersionUID = 4664456874499611218L;
    
	private int errorCode = 0;
	
	public static int ERROR_DATOS					= 0;
	public static int ADVERTENCIA_DATOS				= 1;
	public static int ERROR_SISTEMA					= 2;
	
	// Mensajes generales
	public static String MENSAJE_ERROR_SISTEMA					= "Ha ocurrido un error, por favor vuelva a intentarlo";
	public static String MENSAJE_OK_ALTA						= "El alta ha sido efectuada con éxito";
	public static String MENSAJE_ERROR_DATOS					= "Para continuar complete todos los campos obligatorios";
	
	// Mensajes para Proveedor
	public static String MENSAJE_RUT_DUPLICADO					= "Ya existe un Proveedor en el sistema con ese RUT";
	public static String MENSAJE_NOMBRE_COMERCIAL_DUPLICADO		= "Ya existe un Proveedor en el sistema con ese Nombre Comercial";
     
	// Mensajes para pedido automático
	public static String MENSAJE_OK_PEDIDO						= "El pedido fue enviado con éxito.";
	public static String MENSAJE_PEDIDO_VACIO					= "Primero debes seleccionar un pedido.";
	
	// Mensajes para Artículo
	public static String MENSAJE_ART_DUPLICADO					= "Ya existe un artículo en el sistema con esa descripción";
	
    public Excepciones(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;
    }
     
    public int getErrorCode(){
        return this.errorCode;
    }
}
