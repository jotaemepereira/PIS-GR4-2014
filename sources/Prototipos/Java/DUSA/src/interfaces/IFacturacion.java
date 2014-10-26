package interfaces;

import java.util.List;

import controladores.Excepciones;
import model.Venta;
import datatypes.DTVenta;

public interface IFacturacion {
	
	public List<Venta> listarVentasPendientes() throws Exception;
	
	public void facturarVenta(long venta) throws Exception;
	
	public void cancelarVenta(long venta) throws Exception;
	
	public void registrarNuevaVenta(Venta v)
			throws Excepciones;
	/**
	 * Registra en el sistema la venta v
	 * @param v 
	 * 			- lista de DTVenta que componen la venta a registrar
	 * 
	 * @throws Excepciones
	 * @author Ignacio Rodriguez
	 */
	
}
