package controladores;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import datatypes.DTSaleDetail;
import model.LineaVenta;
import model.Venta;
import interfaces.IFacturacion;
import interfaces.IPersistence;

public class FacturacionControlador implements IFacturacion {

	@Override
	public void newSale(int clientId, int userId, List<DTSaleDetail> dtLines,
			char saleType) throws Exception {

		try {
			
			List<LineaVenta> lines = new ArrayList<LineaVenta>();
			for (DTSaleDetail dt : dtLines){
				LineaVenta sd = new LineaVenta();
				sd.setDiscount(dt.getDiscount());
				sd.setProductId(dt.getProductId());
				sd.setQuantity(dt.getQuantity());
				sd.setSalePrice(dt.getSalePrice());
				lines.add(sd);
			}

			Venta s = new Venta();
			s.setClientId(clientId);
			s.setDate(new Date());
			s.setLines(lines);
			s.setSaleType(saleType);
			s.setUserId(userId);
			
			IPersistence ip = FabricaLogica.getIPersistence();
			ip.insertSale(s);
			
		} catch (Exception e) {
			// falta logger
			throw e;
		}

	}

}
