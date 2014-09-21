package interfaces;

import java.util.List;

import model.Venta;
import datatypes.DTSaleDetail;

public interface IFacturacion {
	
	public List<Venta> listarVentasPendientes() throws Exception;
	
	public void facturarVenta(long ventaId) throws Exception;
	
}
