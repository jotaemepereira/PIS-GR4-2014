package interfaces;

import controladores.Excepciones;
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
}
