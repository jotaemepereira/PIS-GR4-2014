package interfaces;

import java.util.List;

import datatypes.DTProduct;
import model.Product;

public interface IStock {

	public List<DTProduct> getProducts(String description);
	
	public DTProduct getProduct(String barcode);
	
}
