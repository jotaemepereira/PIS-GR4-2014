package interfaces;

import java.util.List;

import model.Venta;

public interface IFacturacionPersistencia {
	
	public List<Venta> listarVentasPendientes() throws Exception;

	public void facturarVenta(long ventaId) throws Exception;

}
