package interfaces;

import java.util.List;
import java.util.Map;

import model.Orden;
import model.OrdenDetalle;
import controladores.Excepciones;
import datatypes.DTComprobanteFactura;
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
	 * Completa los datos de un artículo de una factura
	 * 
	 * @param detalle
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public void getDatosArticulo(OrdenDetalle detalle) throws Excepciones;
	
	/**
	 * 
	 * @return los comprobantes pendientes de procesar
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public Map<Long, DTComprobanteFactura> obtenerFacturasPendientes() throws Excepciones;
	
}
