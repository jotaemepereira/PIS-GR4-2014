package interfaces;

import java.util.List;

import model.Product;
import model.Sale;

public interface IPersistence {
	
	public List<Product> getProduct(String description);
	
	public void insertSale(Sale sale);
}
