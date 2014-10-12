package controladores;

import interfaces.*;

public class FabricaLogica {

	public static IFacturacion getIFacturacion() {
		return new FacturacionControlador();
	}
	
	public static IStock getIStock(){
		return new StockControlador();
	}
	
	public static ICompras getInstanciaCompras(){
		return new ComprasControlador();
	}
	
	public static IProveedores getInstanciaProveedores(){
		return new ProveedoresControlador();
	}
}
