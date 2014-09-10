package interfaces;

import java.util.List;

import modelo.Product;

public interface IPersistence {
	
	public List<Product> getProduct(String description);
	
}
