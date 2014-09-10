package controladores;

import persistencia.PersistenceController;
import interfaces.*;

public class Fabric {

	public IBIlling getIBilling() {
		return new BillingController();
	}
	
	public IPersistence getIPersistence(){
		return new PersistenceController();
	}

	public IStock getIStock(){
		return new StockController();
	}
}
