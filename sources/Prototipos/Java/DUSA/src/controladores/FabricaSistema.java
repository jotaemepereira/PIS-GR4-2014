package controladores;

import interfaces.IFacturacion;

public class FabricaSistema {
	
	public static MSistemaControlador getMSistema() {
		return new MSistemaControlador();
	}

}
