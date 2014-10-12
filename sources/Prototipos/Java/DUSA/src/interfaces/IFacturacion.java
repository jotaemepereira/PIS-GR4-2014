package interfaces;

import java.util.List;

import model.Venta;
import datatypes.DTVenta;

public interface IFacturacion {
	
	public List<Venta> listarVentasPendientes() throws Exception;
	
	public void facturarVenta(Venta venta) throws Exception;
	
}
