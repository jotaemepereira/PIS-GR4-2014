package interfaces;

import java.util.List;

import model.Articulo;
import datatypes.DTProduct;

public interface IStock {

	public List<DTProduct> getProducts(String description);
	
	public DTProduct getProduct(String barcode);
	
	public void altaArticulo(Articulo articulo);
}
