package controladores;

import persistencia.PersistenceController;
import interfaces.*;

public class FabricaLogica {

	public static IFacturacion getIFacturacion() {
		return new MFacturacionControlador();
	}
	
	public static IPersistence getIPersistence(){
		return new PersistenceController();
	}

	public static IStock getIStock(){
		return new MStockControlador();
	}
}
