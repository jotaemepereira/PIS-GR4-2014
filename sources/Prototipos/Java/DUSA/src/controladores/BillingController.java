package controladores;

import java.util.Date;
import java.util.List;

import model.SaleDetail;
import model.Sale;
import interfaces.IBIlling;
import interfaces.IPersistence;

public class BillingController implements IBIlling {

	@Override
	public void newSale(int clientId, int userId, List<SaleDetail> lines,
			char saleType) throws Exception {

		try {

			Sale s = new Sale();
			s.setClientId(clientId);
			s.setDate(new Date());
			s.setLines(lines);
			s.setSaleType(saleType);
			s.setUserId(userId);
			
			IPersistence ip = Fabric.getIPersistence();
			ip.insertSale(s);
			
		} catch (Exception e) {
			// falta logger
			throw e;
		}

	}

}
