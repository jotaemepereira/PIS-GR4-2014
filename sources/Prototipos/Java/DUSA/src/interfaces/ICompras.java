package interfaces;

import java.util.List;

import model.Orden;
import controladores.Excepciones;
import datatypes.DTFormasVenta;
import datatypes.DTTiposDGI;

public interface ICompras {

	/**
	 * Ingresa una factura de compra al sistema
	 * 
	 * @param orden
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public void ingresarFacturaCompra(Orden orden) throws Excepciones;
	
	/**
	 * Obtiene las tipos de las facturas de la base de datos
	 * 
	 * @return lista con las distintas formas de venta
	 * @author Victoria Díaz
	 * @throws Excepciones 
	 */
	public List<DTTiposDGI> obtenerTiposDGI() throws Excepciones;
}
