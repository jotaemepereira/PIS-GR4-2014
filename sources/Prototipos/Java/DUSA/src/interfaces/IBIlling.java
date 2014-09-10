package interfaces;

import java.util.List;

import model.SaleDetail;

public interface IBIlling {
	
	public void newSale(int clientId, int userId, List<SaleDetail> lines, char saleType) throws Exception;
	
}
