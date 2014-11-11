package controladores;

import interfaces.IFacturacion;
import interfaces.IFacturacionPersistencia;
import interfaces.ISistema;

import java.util.List;

import datatypes.DTVenta;
import model.Venta;
import Util.XMLUtil;

public class FacturacionControlador implements IFacturacion {

	@Override
	public List<Venta> listarVentasPendientes() throws Excepciones {

		IFacturacionPersistencia ifp = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();
		return ifp.listarVentasPendientes();
	}

	@Override
	public boolean facturarVenta(long ventaId) throws Excepciones {
		IFacturacionPersistencia ifp = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();

		// Obtengo venta pendiente y la marco como facturada
		Venta venta = ifp.facturarVenta(ventaId);

		if (venta == null) {
			return false;
		}
		// Creo factura
		try {
			XMLUtil.jaxbObjectToXML(venta);
		} catch (Exception e) {

			e.printStackTrace();
			throw new Excepciones(Excepciones.MENSAJE_ERROR_IMPRESION_FACTURA,
					Excepciones.ERROR_SISTEMA);
		}
		return true;
	}
	
	@Override
	public void facturarVenta(Venta v) throws Excepciones {
		IFacturacionPersistencia ifp = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();

		// Obtengo venta pendiente y la marco como facturada
		Venta venta = ifp.facturarVenta(v.getVentaId());

		if (venta != null) {
			// Creo factura
			try {
				
				XMLUtil.jaxbObjectToXML(venta);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new Excepciones(Excepciones.MENSAJE_ERROR_IMPRESION_FACTURA, Excepciones.ERROR_SISTEMA);
			}
		}
	}

	@Override
	public void cancelarVenta(long ventaId) throws Excepciones {

		IFacturacionPersistencia ifp = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();

		ifp.cancelarVenta(ventaId);
	}

	@Override
	public long registrarNuevaVenta(Venta v) throws Excepciones {
		// TODO Auto-generated method stub
		IFacturacionPersistencia pf = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();
		return pf.persistirVenta(v);

	}
}
