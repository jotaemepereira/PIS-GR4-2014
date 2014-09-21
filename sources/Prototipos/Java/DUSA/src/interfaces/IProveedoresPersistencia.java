package interfaces;

import model.Proveedor;

public interface IProveedoresPersistencia {
	
	public void persistirProveedor(Proveedor proveedor);
	public boolean existeProveedor(int RUT);
}
