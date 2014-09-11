package interfaces;

import java.util.List;

import model.Product;
import model.Sale;

public interface IPersistence {
	
	public List<Product> getProducts(String description);
	public Product getProduct(String barcode);
	
	public void insertSale(Sale sale);
}
