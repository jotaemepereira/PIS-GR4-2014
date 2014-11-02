package controladores;

import datatypes.DTLineaFacturaCompra;
import interfaces.IComprasPersistencia;
import interfaces.IFacturacionPersistencia;
import interfaces.IProveedoresPersistencia;
import interfaces.IStockPersistencia;
import interfaces.IUsuarioPersistencia;
import persistencia.PComprasControlador;
import persistencia.PFacturacionControlador;
import persistencia.PProveedoresControlador;
import persistencia.PStockControlador;
import persistencia.PUsuarioControlador;

public class FabricaPersistencia {
	
	public static IStockPersistencia getStockPersistencia(){
		return new PStockControlador();
	}
	
	public static IProveedoresPersistencia getInstanciaProveedoresPersistencia(){
		return new PProveedoresControlador();
	}
	

	public static IFacturacionPersistencia getInstanciaFacturacionPersistencia(){
		return new PFacturacionControlador();
	}
	
	public static IUsuarioPersistencia getInstanciaUsuaruiPersistencia(){
		return new PUsuarioControlador();
	}
	
	public static IComprasPersistencia getInstanciaComprasPersistencia(){
		return new PComprasControlador();
	}
}
