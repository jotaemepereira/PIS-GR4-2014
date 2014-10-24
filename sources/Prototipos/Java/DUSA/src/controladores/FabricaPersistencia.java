package controladores;

import interfaces.IFacturacionPersistencia;
import interfaces.IProveedoresPersistencia;
import interfaces.IStockPersistencia;
import interfaces.IUsuarioPersistencia;
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
	
}
