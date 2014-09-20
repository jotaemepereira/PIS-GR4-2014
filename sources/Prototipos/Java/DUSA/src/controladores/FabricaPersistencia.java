package controladores;

import interfaces.IProveedoresPersistencia;
import interfaces.IStockPersistencia;
import persistencia.PProveedoresControlador;
import persistencia.PStockControlador;

public class FabricaPersistencia {
	
	public static IStockPersistencia getIStockPersistencia(){
		return new PStockControlador();
	}
	
	public static IProveedoresPersistencia getInstanciaProveedoresPersistencia(){
		return new PProveedoresControlador();
	}

}
