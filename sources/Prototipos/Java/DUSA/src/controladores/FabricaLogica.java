package controladores;

import persistencia.PersistenceController;
import interfaces.*;

public class FabricaLogica {

	public static IFacturacion getIFacturacion() {
		return new FacturacionControlador();
	}
	
	public static IPersistence getIPersistence(){
		return new PersistenceController();
	}

	public static IStock getIStock(){
		return new StockControlador();
	}
}
