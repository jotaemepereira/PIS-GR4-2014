package interfaces;

import java.util.List;

import model.Product;

public interface IStock {

	public List<Product> getProduct(String description);
	
}
