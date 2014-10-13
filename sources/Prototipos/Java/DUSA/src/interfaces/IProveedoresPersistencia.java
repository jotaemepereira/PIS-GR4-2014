package interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controladores.Excepciones;
import datatypes.DTProveedor;
import model.Proveedor;

public interface IProveedoresPersistencia {

	/**
	 * Persiste el proveedor en la base de datos
	 * 
	 * @param proveedor
	 *            - Proveedor
	 * @throws Excepciones
	 *             ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             persistir o de conectarse a la base)
	 * @author Victoria Díaz
	 */
	public void persistirProveedor(Proveedor proveedor) throws Excepciones;

	/**
	 * 
	 * @param nombreComercial
	 *            - String
	 * @return true en caso que ya exista un proveedor con el mismo nombre
	 *         comercial y false en caso contrario
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author Victoria Díaz
	 */
	public boolean existeProveedorNombreComercial(String nombreComercial)
			throws Excepciones;

	/**
	 * 
	 * @param RUT
	 *            - String
	 * @return true en caso que ya exista un proveedor con el mismo RUT
	 *         y false en caso contrario
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda alg�n error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author Victoria Díaz
	 */
	public boolean existeProveedorRUT(String RUT) throws Excepciones;
	
	/**
	 * 
	 * @return Devuelve una lista de DTProveedor con todos los proveedores del sistema
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author José Aguerre
	 */
	public Map<Integer, DTProveedor> obtenerProveedores() throws Excepciones;
}
