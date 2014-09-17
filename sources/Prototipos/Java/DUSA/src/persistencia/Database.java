package persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class Database {

	// Estructura Singleton
	private static Database instance = new Database();
	
	private Database() {
		PopulateDatabase();
	}
	 
    private void PopulateDatabase() {
    	int i = 0;
    	while (i < 100){
    		Articulo p = new Articulo();
    		p.setCodigoBarras("" + i);
    		p.setDescripcion("artículo " + i);
    		p.setPrecioVenta(new BigDecimal(100+i));
    		products.add(p);		
    		i++;
    	}
		
	}

	public static Database getInstance() {
        return instance;
    }
    //
    
    // Tabla de Ventas
	private List<Venta> sales = new ArrayList<Venta>();
    
    // Tabla de artículos
    private List<Articulo> products = new ArrayList<Articulo>();

	public List<Venta> getSales() {
		return sales;
	}

	public void setSales(List<Venta> sales) {
		this.sales = sales;
	}

	public List<Articulo> getProducts() {
		return products;
	}

	public void setProducts(List<Articulo> products) {
		this.products = products;
	}
    
}
