package model;

public class Enumerados {

	public enum TipoFormaDePago {
		CREDITO, CONTADO
	}
	
	public final class formasVenta {
	    public static final char ventaLibre = '1';
	    public static final char controlado = '2';
	    public static final char bajoReceta = '3';
	    public static final char controlMedico = '4';
	}
	
	public class EstadoVenta {
		public static final char PENDIENTE = 'P';
		public static final char FACTURANDO = 'E';
		public static final char FACTURADA = 'F';
	}
	
	public final class habilitado {	    
	    public static final char C = 'C';
	    public static final char F = 'F';
	    public static final char K = 'K';
	    public static final char M = 'M';
	    public static final char O = 'O';
	    public static final char PEDIDO = 'P';
	    public static final char R = 'R';
	    public static final char HABILITADO = 'S';
	    public static final char V = 'V';
	    public static final char X = 'X';
	}
}
