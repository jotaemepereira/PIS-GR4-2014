package controladores;

import interfaces.IStockPersistencia;
import persistencia.PStockControlador;

public class FabricaPersistencia {
	
	public static IStockPersistencia getIStockPersistencia(){
		return new PStockControlador();
	}

}
