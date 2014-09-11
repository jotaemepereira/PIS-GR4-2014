package controladores;

import interfaces.IPersistence;
import interfaces.IStock;

import java.util.ArrayList;
import java.util.List;

import datatypes.DTProduct;
import model.Product;

public class StockController implements IStock {

	@Override
	public List<DTProduct> getProducts(String description) {
		IPersistence ip = Fabric.getIPersistence();

		List<Product> pList = ip.getProducts(description);
		List<DTProduct> retList = new ArrayList<DTProduct>();
		for (Product p : pList) {
			DTProduct dt = new DTProduct();
			dt.setBarcode(p.getBarcode());
			dt.setSalePrice(p.getSalePrice());
			retList.add(dt);
		}
		return retList;
	}

	@Override
	public DTProduct getProduct(String barcode) {
		IPersistence ip = Fabric.getIPersistence();
		Product p = ip.getProduct(barcode);
		DTProduct dt = new DTProduct();
		dt.setBarcode(p.getBarcode());
		dt.setSalePrice(p.getSalePrice());
		dt.setDescription(p.getDescription());
		return dt;
	}
}
