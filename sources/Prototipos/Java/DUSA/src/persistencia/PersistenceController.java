package persistencia;

import java.util.ArrayList;
import java.util.List;

import model.Product;
import model.Sale;
import interfaces.IPersistence;

public class PersistenceController implements IPersistence {

	@Override
	public List<Product> getProducts(String description) {
		List<Product> ret = new ArrayList<Product>();
		for (Product p : Database.getInstance().getProducts()) {
			if (p.getDescription().toLowerCase()
					.contains(description.toLowerCase())) {
				ret.add(p);
			}
		}
		return ret;
	}
	
	@Override
	public Product getProduct(String barcode){
		for (Product p : Database.getInstance().getProducts()) {
			if (p.getBarcode().toLowerCase()
					.equals(barcode.toLowerCase())) {
				return p;
			}
		}
		return null;
	}

	@Override
	public void insertSale(Sale sale) {
		Database.getInstance().getSales().add(sale);
	}

}
