package controladores;

import interfaces.IFacturacionPersistencia;
import interfaces.IProveedoresPersistencia;
import interfaces.IStockPersistencia;
import persistencia.PFacturacionControlador;
import persistencia.PProveedoresControlador;
import persistencia.PStockControlador;

public class FabricaPersistencia {
	
	public static IStockPersistencia getIStockPersistencia(){
		return new PStockControlador();
	}
	
	public static IProveedoresPersistencia getInstanciaProveedoresPersistencia(){
		return new PProveedoresControlador();
	}

	public static IFacturacionPersistencia getInstanciaFacturacionPersistencia(){
		return new PFacturacionControlador();
	}
	
}
