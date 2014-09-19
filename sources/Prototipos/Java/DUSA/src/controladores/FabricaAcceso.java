package controladores;

import interfaces.IComprasPersistencia;

public class FabricaAcceso {
	public static IComprasPersistencia getInstanciaStockPersistencia(){
		return new PStock();
	}
}
