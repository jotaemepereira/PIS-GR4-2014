package interfaces;

import java.sql.Date;
import java.util.List;

import controladores.Excepciones;
import model.Venta;

public interface IFacturacionPersistencia {
	
	public List<Venta> listarVentasPendientes() throws Exception;

	public void facturarVenta(long ventaId) throws Exception;

	public Venta obtenerVentaParaFacturar(long ventaId) throws Exception;
	
	public void marcarVentaFacturada(long ventaId) throws Exception;
	
	/**
	 * @author Guille
	 * @param desde
	 * @param hasta
	 * @return Retorna una lista de id de artículos, de D.U.S.A. no borrados, que se vendieron entre las fechas "desde" hasta la fecha "hasta", ambas inclusive. 
	 * @throws Exception
	 */
	public List<Long> getIdArticulosEnPeriodo(Date desde, Date hasta) throws Excepciones;
	
	/**
	 * @author Guille
	 * @param idArticulo
	 * @param desde
	 * @param hasta
	 * @return Retorna la cantidad vendida del artículo con id "idArticulo" entre las fechas "desde" hasta la fecha "hasta", ambas inclusive.
	 * @throws Excepciones
	 */
	public int cantidadVendidaEnPeriodo(Long idArticulo, Date desde, Date hasta) throws Excepciones;
}
