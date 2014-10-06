package interfaces;

import controladores.Excepciones;
import model.Proveedor;

public interface IProveedores {

	/**
	 * Da de alta un proveedor en el sistema
	 * 
	 * @param proveedor
	 *            - Proveedor
	 * @throws Excepciones
	 *             RUT_EXISTENTE (si ya existe el rut en el sistema)
	 *             PROVEEDOR_NOMBRE_EXISTENTE (si ya existe un proveedor con ese nombre comercial)
	 *             ERROR_SISTEMA (en caso de error a la hora de persistir en la base de datos)
	 * @author Victoria Díaz
	 *      
	 */
	public void altaProveedor(Proveedor proveedor) throws Excepciones;

}
