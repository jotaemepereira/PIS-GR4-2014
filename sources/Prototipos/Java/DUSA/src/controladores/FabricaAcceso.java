package controladores;

import persistencia.PProveedores;
import interfaces.IProveedoresPersistencia;

public class FabricaAcceso {
	public static IProveedoresPersistencia getInstanciaProveedoresPersistencia(){
		return new PProveedores();
	}
}
