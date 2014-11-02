package interfaces;

import java.util.List;
import java.util.Map;

import model.Orden;
import controladores.Excepciones;
import datatypes.DTFormasVenta;
import datatypes.DTLineaFacturaCompra;
import datatypes.DTTiposDGI;

public interface IComprasPersistencia {
	/**
	 * Ingresa una factura de compra al sistema
	 * 
	 * @param orden
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public void ingresarFacturaCompra(Orden orden) throws Excepciones;
	
	/**
	 * Obtiene los tipos de las facturas de la base de datos
	 * 
	 * @return lista con las distintas formas de venta
	 * @author Victoria Díaz
	 * @throws Excepciones 
	 */
	public Map<Integer, DTTiposDGI> obtenerTiposDGI() throws Excepciones;

	/**
	 * Completa los datos de un articulo de una factura para el caso cuando se traen
	 * las facturas de DUSA
	 * 
	 * @param dtLineaFacturaCompra
	 * @author Victoria Díaz
	 * @throws Excepciones 
	 */
	public void getDatosArticuloLinea(DTLineaFacturaCompra dtLineaFacturaCompra) throws Excepciones;
	
}
