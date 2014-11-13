package interfaces;

import java.util.List;

import controladores.Excepciones;
import model.Venta;

public interface IFacturacion {
	

	public List<Venta> listarVentasPendientes() throws Excepciones;


	public void articulosConPocasVentasEnLosUltimosMeses(int mesesAtras) throws Exception;
	
	
	public boolean facturarVenta(long venta) throws Excepciones;
	
	public void cancelarVenta(long venta) throws Excepciones;
	
	public long registrarNuevaVenta(Venta v)
			throws Excepciones;
	/**iajo
	 * Registra en el sistema la venta v
	 * @param v 
	 * 			- lista de DTVenta que componen la venta a registrar
	 * 
	 * @throws Excepciones
	 * @author Ignacio Rodriguez
	 */

	void facturarVenta(Venta v) throws Excepciones;
	/**
	 * factura en el sistema la venta v
	 * @param v 
	 * 			- venta a registrar
	 * 
	 * @throws Excepciones
	 * @author Ignacio Rodriguez
	 */
}
