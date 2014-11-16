package interfaces;

import java.util.List;

import controladores.Excepciones;
import model.Venta;

public interface IFacturacion {
	

	public List<Venta> listarVentasPendientes() throws Excepciones;

	
	/**
	 * @author santiago 
	 * @param mesesAtras corresponde a la cantidad de meses en los cuales se 
	 * conciderará la cantidad de ventas de cada artículos
	 * @throws Exception
	 * Sea la cantidad e ventas de un artículo en el período comprendido
	 * entre la fecha actual y meses atrás. Se concidera que un artículo 
	 * tiene pocas ventas si esta cantidad es menor  al valor mínimo stock 
	 * que se encuentra en la tabla de products. En este caso se crea un 
	 * objeto del tipo PocasVentas para enviar dicha información por mail
	 *  
	 */
	public void articulosConPocasVentasEnLosUltimosMeses(int mesesAtras) throws Exception;
	
	
	public boolean facturarVenta(long venta) throws Excepciones;
	
	public void cancelarVenta(long venta) throws Excepciones;
	
	/**iajo
	 * Registra en el sistema la venta v
	 * @param v 
	 * 			- lista de DTVenta que componen la venta a registrar
	 * 
	 * @throws Excepciones
	 * @author Ignacio Rodriguez
	 */
	public long registrarNuevaVenta(Venta v)
			throws Excepciones;
	
	/**
	 * factura en el sistema la venta v
	 * @param v 
	 * 			- venta a registrar
	 * 
	 * @throws Excepciones
	 * @author Ignacio Rodriguez
	 */
	void facturarVenta(Venta v) throws Excepciones;
	}
