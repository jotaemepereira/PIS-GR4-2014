package interfaces;

import java.util.Date;
import java.util.Map;

import model.Orden;
import model.OrdenDetalle;
import controladores.Excepciones;
import datatypes.DTComprobanteFactura;
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
	 * Obtiene las facturas de DUSA pendientes a ser ingresadas
	 * 
	 * @return los comprobantes pendientes de procesar
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public Map<Long, DTComprobanteFactura> obtenerFacturasPendientes() throws Excepciones;
	
	/**
	 * Es la función encargada de pasar una orden de DUSA a procesada
	 * 
	 * @param orden
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public void actualizarFacturaCompraDUSA(Orden orden) throws Excepciones;
	
	/**
	 * Trae la fecha de la última factura de DUSA en el sistema
	 * 
	 * @return Date - fecha ultima factura
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public Date getFechaUltimaFacturaDUSA() throws Excepciones;
	
}
