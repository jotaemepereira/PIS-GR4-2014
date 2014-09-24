package controladores;

public class Excepciones extends Exception {
	private static final long serialVersionUID = 4664456874499611218L;
    
	public static int ERROR_DATOS					= 0;
	public static int PROVEEDOR_NOMBRE_EXISTENTE	= 1;
	
    private int errorCode = 0;
     
    public Excepciones(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;
    }
     
    public int getErrorCode(){
        return this.errorCode;
    }
}
