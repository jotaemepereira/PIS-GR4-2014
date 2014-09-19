package controladores;

import model.Proveedor;
import interfaces.ICompras;
import interfaces.IComprasPersistencia;

public class MCompras implements ICompras {
	
	@Override
	public void altaProveedor(Proveedor proveedor) {
		if(proveedor.getRUT() == 0){
			//TODO: error y salir
		}
		if(proveedor.getNombreComercial() == ""){
			//TODO: error y salir
		}
		
		// si los datos eran correctos, continuo a persistirlos
		IComprasPersistencia persistencia = FabricaAcceso.getInstanciaStockPersistencia();
		persistencia.persistirProveedor(proveedor);
	}
}
