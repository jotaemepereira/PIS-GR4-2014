package model;

import datatypes.DTFormasVenta;

public class Enumerados {

	public enum TipoFormaDePago {
		CREDITO, CONTADO
	}
	
	public enum casoDeUso{
	    ventaLibre, 
	    registrarVentaPerdida,
	    registrarNuevaVenta, 
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
	public final class formasVenta {
	    public static final char ventaLibre = '1';
	    public static final char controlado = '2';
	    public static final char bajoReceta = '3';
	    public static final char controlMedico = '4';
	}
	
	public class EstadoVenta {
		public static final char PENDIENTE = 'P';
		public static final char FACTURADA = 'F';
		public static final char CANCELADA = 'C';
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
}
