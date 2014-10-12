package controladores;

import interfaces.ISistema;

public class FabricaSistema {
	
	public static ISistema getISistema(){
		return new SistemaControlador();
	}
}
