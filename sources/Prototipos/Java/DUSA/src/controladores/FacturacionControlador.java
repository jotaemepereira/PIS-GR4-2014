package controladores;

import interfaces.IFacturacion;
import interfaces.IFacturacionPersistencia;

import java.util.List;

import model.Venta;
import Util.XMLUtil;

public class FacturacionControlador implements IFacturacion {

	@Override
	public List<Venta> listarVentasPendientes() throws Exception {
		try {
			 IFacturacionPersistencia ifp = FabricaPersistencia.getInstanciaFacturacionPersistencia();
			 return ifp.listarVentasPendientes();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void facturarVenta(long ventaId) throws Exception {
		try {
			IFacturacionPersistencia ifp = FabricaPersistencia
					.getInstanciaFacturacionPersistencia();

			// Obtengo venta pendiente y la marco como facturada
			Venta venta = ifp.facturarVenta(ventaId);
			
			// Creo factura
			XMLUtil.jaxbObjectToXML(venta);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void cancelarVenta(long ventaId) throws Exception{
		try {
			IFacturacionPersistencia ifp = FabricaPersistencia
					.getInstanciaFacturacionPersistencia();

			ifp.cancelarVenta(ventaId);
		} catch (Exception e) {
			throw e;
		}
	}
}
