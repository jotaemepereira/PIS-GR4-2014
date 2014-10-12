package controladores;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Util.XMLUtil;
import model.LineaVenta;
import model.Usuario;
import model.Venta;
import interfaces.IFacturacion;
import interfaces.IFacturacionPersistencia;

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
	public void facturarVenta(Venta venta) throws Exception {
		try {
			IFacturacionPersistencia ifp = FabricaPersistencia
					.getInstanciaFacturacionPersistencia();

			// Obtengo venta pendiente y la marco como en proceso de facturación
			ifp.facturarVenta(venta);

			// Creo factura
			File f = XMLUtil.jaxbObjectToXML(venta);
		} catch (Exception e) {
			throw e;
		}
	}
}
