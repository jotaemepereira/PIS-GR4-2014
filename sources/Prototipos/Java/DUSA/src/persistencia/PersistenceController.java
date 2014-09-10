package persistencia;

import java.util.ArrayList;
import java.util.List;

import model.Product;
import model.Sale;
import interfaces.IPersistence;

public class PersistenceController implements IPersistence {

	@Override
	public List<Product> getProduct(String description) {
		List<Product> ret = new ArrayList<Product>();
		for (Product p : Database.getInstance().products) {
			if (p.getDescription().toLowerCase()
					.contains(description.toLowerCase())) {
				ret.add(p);
			}
		}
		return ret;
	}

	@Override
	public void insertSale(Sale sale) {
		Database.getInstance().sales.add(sale);
	}

}
