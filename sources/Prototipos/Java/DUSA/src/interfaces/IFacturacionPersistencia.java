package interfaces;

import java.sql.Date;
import java.util.List;

import model.Venta;

public interface IFacturacionPersistencia {
	
	public List<Venta> listarVentasPendientes() throws Exception;

	public void facturarVenta(long ventaId) throws Exception;

	public void facturarVenta(Venta venta) throws Exception;
	
	public void marcarVentaFacturada(long ventaId) throws Exception;

	public List<Long> getIdArticulosEnPeriodo(Date desde, Date hasta) throws Exception;
	
	public int cantidadVendidaEnPeriodo(Long idArticulo, Date desde, Date hasta) throws Exception;
}
