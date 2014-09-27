package controladores;

import java.util.List;

import model.Venta;
import interfaces.IFacturacion;
import interfaces.IFacturacionPersistencia;

public class FacturacionControlador implements IFacturacion {

	@Override
	public List<Venta> listarVentasPendientes() throws Exception {
		try {
			IFacturacionPersistencia ifp = FabricaPersistencia
					.getInstanciaFacturacionPersistencia();
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
			
			//Obtengo venta pendiente y la marco como en proceso de facturación
			Venta v = ifp.obtenerVentaParaFacturar(ventaId);
			
			//Creo factura
			//TODO: Falta esta parte
			
			//Marco venta como facturada
			ifp.marcarVentaFacturada(ventaId);
		} catch (Exception e) {
			throw e;
		}
	}


}
