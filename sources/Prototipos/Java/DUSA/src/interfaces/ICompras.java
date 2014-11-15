package interfaces;

import java.util.Map;

import model.Orden;
import controladores.Excepciones;
import datatypes.DTComprobanteFactura;
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
	public Map<Integer, DTTiposDGI> obtenerTiposDGI() throws Excepciones;
	
	/**
	 * trae las facturas por el servicio de DUSA
	 * 
	 * @return lista de facturas de DUSA automaticas
	 * @throws Excepciones
	 * @author Victoria Díaz
	 */
	public Map<Long, DTComprobanteFactura> obtenerFacturasDUSA(String usuario) throws Excepciones;
}
