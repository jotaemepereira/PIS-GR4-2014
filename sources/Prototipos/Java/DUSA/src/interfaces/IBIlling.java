package interfaces;

import java.util.List;

import datatypes.DTSaleDetail;

public interface IBIlling {
	
	public void newSale(int clientId, int userId, List<DTSaleDetail> lines, char saleType) throws Exception;
	
}
