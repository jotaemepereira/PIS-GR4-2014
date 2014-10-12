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
	public void facturarVenta(long ventaId) throws Exception {
		try {
			IFacturacionPersistencia ifp = FabricaPersistencia
					.getInstanciaFacturacionPersistencia();

			// Obtengo venta pendiente y la marco como en proceso de facturación
			Venta v = ifp.obtenerVentaParaFacturar(ventaId);

			// Creo factura
			File f = XMLUtil.jaxbObjectToXML(v);

			// Marco venta como facturada
			ifp.marcarVentaFacturada(ventaId);
		} catch (Exception e) {
			throw e;
		}
	}

	private List<Venta> listarVentasPendientesMock() {
		try {
			List<Venta> ventas = new ArrayList<Venta>();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String[] dates = { "10-05-2014", "09-05-2014", "09-05-2014",
					"08-05-2014", "08-05-2014", "08-05-2014", "08-05-2014",
					"07-05-2014", "07-05-2014", "07-05-2014" };

			Usuario user1 = new Usuario();
			user1.setNombre("Juan");
			Usuario user2 = new Usuario();
			user2.setNombre("Maria");
			Usuario user3 = new Usuario();
			user3.setNombre("Daniel");

			Usuario[] users = { user1, user2, user3, user3, user2, user2,
					user1, user1, user2, user2 };

			String[] montos = { "25", "25", "40", "95", "40", "25",
					"25", "25", "25", "40" };

			long[] ids = { 2, 4, 11, 23, 24, 25, 36, 37, 38, 40 };
			int[] lineas = { 1, 1, 2, 3, 2, 1, 2, 1, 1, 2, 3 };
			for (int i = 1; i < 10; i++) {
				Venta v = new Venta();
				v.setLineas(new ArrayList<LineaVenta>());
				v.setVentaId(ids[i]);
				v.setFechaVenta(formatter.parse(dates[i]));
				v.setUsuario(users[i]);
				v.setMontoTotalAPagar(new BigDecimal(montos[i]));
				for (int j = 0; j < lineas[i]; j++) {
					LineaVenta lv = new LineaVenta();
					lv.setCantidad(1);
					lv.setDescuento(new BigDecimal(0));
					if (j == 0) {
						lv.setPrecio(new BigDecimal(25));
					} else if (j== 1){
						lv.setPrecio(new BigDecimal(15));
					} else {
						lv.setPrecio(new BigDecimal(45));
					}
					v.getLineas().add(lv);
				}
				v.setCantidadLineas(lineas[i]);
				ventas.add(v);
			}

			return ventas;
		} catch (Exception e) {
			return null;
		}
	}

}
