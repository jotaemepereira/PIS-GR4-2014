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
    		Product p = new Product();
    		p.setBarcode("" + i);
    		p.setDescription("artículo " + i);
    		p.setSalePrice(new BigDecimal(100+i));
    		products.add(p);		
    		i++;
    	}
		
	}

	public static Database getInstance() {
        return instance;
    }
    //
    
    // Tabla de Ventas
	private List<Sale> sales = new ArrayList<Sale>();
    
    // Tabla de artículos
    private List<Product> products = new ArrayList<Product>();

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
    
}
