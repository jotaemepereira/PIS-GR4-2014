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

	/**
	 * Retorna todos los proveedores activos existentes en el sistema marcados como marca o laboratorio.
	 * 
	 * @return Devuelve un List de DTProveedor con todos los proveedores del sistema marcados como marca o laboratorio.
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author José Aguerre
	 */
	public List<DTProveedor> obtenerMarcas() throws Excepciones;

	/**
	 * Chequea la existencia del codigoIdentificador para el proveedor.
	 * 
	 * @return True si existe un articulo con ese codigo para el proveedor.
	 * 
	 * @throws Excepciones
	 *             - ERROR_SISTEMA (en caso que suceda algún error a la hora de
	 *             conectarse o comunicarse con la base)
	 * @author José Aguerre
	 * @param idProveedor
	 * @param codigoIdentificador
	 */
	public boolean existeCodigoParaProveedor(long idProveedor,
			long codigoIdentificador) throws Excepciones;
}
