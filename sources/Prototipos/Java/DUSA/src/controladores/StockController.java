package controladores;

import interfaces.IPersistence;
import interfaces.IStock;

import java.util.List;

import model.Product;

public class StockController implements IStock {
	
	@Override
	public List<Product> getProduct(String description) {
		IPersistence ip = Fabric.getIPersistence();
		
		return ip.getProduct(description);
	}
	
}
