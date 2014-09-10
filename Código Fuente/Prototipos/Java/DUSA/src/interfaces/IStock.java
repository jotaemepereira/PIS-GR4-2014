package interfaces;

import java.util.List;

import modelo.Product;

public interface IStock {

	public List<Product> getProduct(String description);
	
}
