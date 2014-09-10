package controladores;

import persistencia.PersistenceController;
import interfaces.*;

public class Fabric {

	public static IBIlling getIBilling() {
		return new BillingController();
	}
	
	public static IPersistence getIPersistence(){
		return new PersistenceController();
	}

	public static IStock getIStock(){
		return new StockController();
	}
}
