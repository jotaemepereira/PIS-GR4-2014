package controladores;

import interfaces.IFacturacion;
import interfaces.IFacturacionPersistencia;

import java.io.File;
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

			// Obtengo venta pendiente y la marco como en proceso de facturación
			Venta venta = ifp.facturarVenta(ventaId);

			// Creo factura
			File f = XMLUtil.jaxbObjectToXML(venta);
		} catch (Exception e) {
			throw e;
		}
	}
}
