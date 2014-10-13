package interfaces;

import java.util.List;
import java.util.Map;

import controladores.Excepciones;
import model.Proveedor;
import datatypes.DTProveedor;

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
	
	/**
	 * 
	 * @return Devuelve un Map de DTProveedor con todos los proveedores del sistema
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author José Aguerre
	 */
	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones;

}
