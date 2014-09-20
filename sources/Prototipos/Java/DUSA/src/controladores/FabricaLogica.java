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
	
	public static ICompras getInstanciaCompras(){
		return new MCompras();
	}
	
	public static IProveedores getInstanciaProveedores(){
		return new MProveedores();
	}
}
