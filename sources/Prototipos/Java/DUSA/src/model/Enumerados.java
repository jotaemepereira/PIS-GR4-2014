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
		public static final char FACTURADA = 'F';
	}
	
	public final class habilitado {	    
	    public static final char HABILITADO = 'S';
	    public static final char INHABILITADO = 'N'; //(significa que no vende DUSA, pero no implica que no lo venda otro)
	    public static final char DISCONTINUADO = 'D'; //(el laboratorio discontinuó el producto)
	}
	
	public class tipoArticulo {
		public static final char MEDICAMENTO = 'M';
		public static final char PERFUMERIA = 'P';
		public static final char OTROS = 'O';
	}
}
