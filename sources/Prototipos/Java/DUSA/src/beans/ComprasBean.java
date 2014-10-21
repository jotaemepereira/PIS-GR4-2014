/**
 * Clase que implementa el control de la presentación del módulo compras
 * 
 */
package beans;

import java.io.Serializable;
import java.util.List;

import datatypes.DTBusquedaArticulo;
import datatypes.DTProveedor;

public class ComprasBean implements Serializable {
	
	private List<DTProveedor> busquedaProveedores;
	private List<DTBusquedaArticulo> busquedaArticulos;

}
