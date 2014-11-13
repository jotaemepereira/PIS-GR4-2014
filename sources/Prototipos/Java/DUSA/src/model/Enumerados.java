package model;

public class Enumerados {

	public enum TipoFormaDePago {
		CREDITO, CONTADO
	}
	
	public enum casoDeUso{
	     
	    
	    registrarNuevaVenta, 
	    registrarVentaPerdida,
	    facturarVentaPendiente,
	    cancelarVentaPendiente,
	    listarVentasPendientes,
	    nuevaCompra,
	    listarComprasDusa,
	    altaArticulo,
	    bajaArticulo,
	    obtenerArticulo,
	    modificarArticulo,
	    modificarStock,
	    buscarArticulo,
	    generPeEnBaseAPedAnt,
	    genPedEnBaseAHist,
	    realizarPedido,
	    altaCliente,
	    modificarCliente,
	    buscarCliente,
	    altaProveedor,
	    modificarProveedor,
	    buscarProveedor,
	    generarEstadistica,
	    iniciarSesion,
	    cerrarSesion,
	}
	/**
	 * Informacion básica del proveedor DUSA para uso de distintos casos de uso. 
	 * @author Guille
	 */
	public final class infoDUSA {
		
		public static final int proveedorID = 1;//Supongo que será el primer proveedor a ingresar en el sistema final.
		public static final String nombreComercial = "DUSA";
	}
	
	public final class formasVenta {
	    public static final char ventaLibre = '1';
	    public static final char controlado = '2';
	    public static final char bajoReceta = '3';
	    public static final char controlMedico = '4';
	}
	
	public final class descuentosReceta {
		public static final short VEINTICINCO = 1;
		public static final short CUARENTA = 2;
	}
	
	public final class descuentosRecetaDesc {
		public static final String VEINTICINCO = "25%";
		public static final String CUARENTA = "40%";
	}
	
	public final class modoFacturacion{
	    public static final int basica = 1;
	    public static final int interrumpida = 2;
	    public static final int controlada = 3;
	}
	public final class indicadoresFacturacion{
	    public static final int EXENTO = 1;
	    public static final int MINIMO = 2;
	    public static final int BASICO = 3;
	}
	public class EstadoVenta {
		public static final char PENDIENTE = 'P';
		public static final char FACTURADA = 'F';
		public static final char CANCELADA = 'C';
		public static final char PERDIDA = 'X';
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
	
	public class tiposIVA {
		public static final char IVAEXENTO = '0';
		public static final char PSICOFARMACOS = '1';
		public static final char MEDICAMENTOS = '2';
		public static final char IVA10 = '3';
		public static final char IVA22 = '4';
		public static final char IVA22IMESI = '5';
		public static final char IVA22TRIBUTO = '6';
	}
	
	public class tiposIVAParaMostrar {
		public static final String IVAEXENTO = "I.V.A. Exento";
		public static final String IVA10 = "I.V.A. 10%";
		public static final String IVA22 = "I.V.A. 22%";
	}
	
	public class formaDePago {
		public static final String CONTADO = "CONTADO";
		public static final String CREDITO = "CREDITO";
	}
	
	public static String descripcionTipoArticulo(String tipo){
		switch (tipo.charAt(0)) {
			case tipoArticulo.MEDICAMENTO:
				return "Medicamento";
			case tipoArticulo.PERFUMERIA:
				return "Perfumería";
			case tipoArticulo.OTROS:
				return "Otros";
			default:
				return "";
		}
	}
	
	public static String descripcionTipoArticuloAbreviado(String tipo){
		if(tipo == null){
			return "";
		}
		switch (tipo.charAt(0)) {
			case tipoArticulo.MEDICAMENTO:
				return "Medic.";
			case tipoArticulo.PERFUMERIA:
				return "Perfum.";
			case tipoArticulo.OTROS:
				return "Otros";
			default:
				return "";
		}
	}
	
	public static String descripcionTipoVenta(String tipo){
		if(tipo == null){
			return "";
		}
		switch (tipo.charAt(0)) {
		case formasVenta.bajoReceta:
			return "Bajo receta";
		case formasVenta.controlado:
			return "Controlado";
		case formasVenta.controlMedico:
			return "Control médico";
		case formasVenta.ventaLibre:
			return "Venta libre";
		default:
			return "";
		}
	}
	/**
	 * 
	 * @author Guille
	 */
	public final class tipoMovimientoDeStock {
		
		public static final char aumentoStock 	= 'A';
		public static final char bajaStock 		= 'B';
		public static final char desarmeStock 	= 'D';
	}
}
