package controladores;

import interfaces.ISistema;

public class FabricaSistema {
	
	public static SistemaControlador getMSistema() {
		return new SistemaControlador();
	}

	public static ISistema getISistema(){
		return new MSistema();
	}
}
