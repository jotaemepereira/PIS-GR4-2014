package controladores;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import datatypes.DTSaleDetail;
import model.Sale;
import model.SaleDetail;
import interfaces.IFacturacion;
import interfaces.IPersistence;

public class MFacturacionControlador implements IFacturacion {

	@Override
	public void newSale(int clientId, int userId, List<DTSaleDetail> dtLines,
			char saleType) throws Exception {

		try {
			
			List<SaleDetail> lines = new ArrayList<SaleDetail>();
			for (DTSaleDetail dt : dtLines){
				SaleDetail sd = new SaleDetail();
				sd.setDiscount(dt.getDiscount());
				sd.setProductId(dt.getProductId());
				sd.setQuantity(dt.getQuantity());
				sd.setSalePrice(dt.getSalePrice());
				lines.add(sd);
			}

			Sale s = new Sale();
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
