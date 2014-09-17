package controladores;

import interfaces.IPersistence;
import interfaces.IStock;

import java.util.ArrayList;
import java.util.List;

import datatypes.DTProduct;
import model.Articulo;

public class StockControlador implements IStock {

	@Override
	public List<DTProduct> getProducts(String description) {
		IPersistence ip = FabricaLogica.getIPersistence();

		List<Articulo> pList = ip.getArticulos(description);
		List<DTProduct> retList = new ArrayList<DTProduct>();
		for (Articulo p : pList) {
			DTProduct dt = new DTProduct();
			dt.setBarcode(p.getCodigoBarras());
			dt.setSalePrice(p.getPrecioVenta());
			dt.setProductId(p.getIdArticulo());
			retList.add(dt);
		}
		return retList;
	}

	@Override
	public DTProduct getProduct(String barcode) {
		IPersistence ip = FabricaLogica.getIPersistence();
		Articulo p = ip.getArticulo(barcode);
		if (p != null) {
			DTProduct dt = new DTProduct();
			dt.setBarcode(p.getCodigoBarras());
			dt.setSalePrice(p.getPrecioVenta());
			dt.setDescription(p.getDescripcion());
			dt.setProductId(p.getIdArticulo());
			return dt;
		}
		return null;
	}

	@Override
	public void altaArticulo(Articulo articulo) {
		// TODO Auto-generated method stub
		
	}
}
