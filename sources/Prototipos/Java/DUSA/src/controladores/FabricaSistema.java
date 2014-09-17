package controladores;

import interfaces.IFacturacion;

public class FabricaSistema {
	
	public static SistemaControlador getMSistema() {
		return new SistemaControlador();
	}

}
