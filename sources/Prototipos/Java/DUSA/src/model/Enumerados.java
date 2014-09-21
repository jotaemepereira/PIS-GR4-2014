package model;

public class Enumerados {

	public enum paymentType {
		CREDITO, CONTADO
	}
	
	public final class formasVenta {
	    public static final char ventaLibre = '1';
	    public static final char controlado = '2';
	    public static final char bajoReceta = '3';
	    public static final char controlMedico = '4';
	    
	    private formasVenta(){
	    }
	}
	
	public class EstadoVenta {
		public static final char PENDIENTE = 'P';
	}
	
}
