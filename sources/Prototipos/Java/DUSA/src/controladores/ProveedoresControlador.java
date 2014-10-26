package controladores;

import java.util.List;
import java.util.Map;

import datatypes.DTProveedor;
import model.Proveedor;
import interfaces.IProveedores;

public class ProveedoresControlador implements IProveedores {
	
	@Override
	public void altaProveedor(Proveedor proveedor) throws Excepciones {
		Boolean existsNombre = false;
		
		// Me fijo si ya existe otro proveedor con este RUT en caso que se haya ingresado algo en el campo
		if((proveedor.getRUT() != "") && (FabricaPersistencia.getInstanciaProveedoresPersistencia().existeProveedorRUT(proveedor.getRUT()))){
			throw(new Excepciones(Excepciones.MENSAJE_RUT_DUPLICADO, Excepciones.ERROR_DATOS));
		}

		// Me fijo si ya existe otro proveedor con este nombre para avisarle al usuario
		existsNombre = FabricaPersistencia.getInstanciaProveedoresPersistencia().existeProveedorNombreComercial(proveedor.getNombreComercial());
		
		// si los datos eran correctos, continuo a persistirlos
		FabricaPersistencia.getInstanciaProveedoresPersistencia().persistirProveedor(proveedor);
		
		if(existsNombre){
			throw(new Excepciones(Excepciones.MENSAJE_NOMBRE_COMERCIAL_DUPLICADO, Excepciones.ADVERTENCIA_DATOS));
		}
	}

	@Override
	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones {
		return FabricaPersistencia.getInstanciaProveedoresPersistencia().obtenerProveedores();
	}

	@Override
	public List<DTProveedor> obtenerMarcas() throws Excepciones {
		return FabricaPersistencia.getInstanciaProveedoresPersistencia().obtenerMarcas(); 
	}

	@Override
	public boolean existeCodigoParaProveedor(long idProveedor,
			long codigoIdentificador) throws Excepciones {
		return FabricaPersistencia.getInstanciaProveedoresPersistencia().existeCodigoParaProveedor(idProveedor,codigoIdentificador);
	}
	
}
