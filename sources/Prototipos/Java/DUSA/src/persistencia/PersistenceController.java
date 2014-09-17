package persistencia;

import java.util.ArrayList;
import java.util.List;

import model.Articulo;
import model.Venta;
import interfaces.IPersistence;

public class PersistenceController implements IPersistence {

	@Override
	public List<Articulo> getArticulos(String description) {
		List<Articulo> ret = new ArrayList<Articulo>();
		for (Articulo p : Database.getInstance().getProducts()) {
			if (p.getDescripcion().toLowerCase()
					.contains(description.toLowerCase())) {
				ret.add(p);
			}
		}
		return ret;
	}
	
	@Override
	public Articulo getArticulo(String barcode){
		for (Articulo p : Database.getInstance().getProducts()) {
			if (p.getCodigoBarras().toLowerCase()
					.equals(barcode.toLowerCase())) {
				return p;
			}
		}
		return null;
	}

	@Override
	public void insertSale(Venta sale) {
		Database.getInstance().getSales().add(sale);
	}

}
