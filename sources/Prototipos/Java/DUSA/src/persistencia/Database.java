package persistencia;

import java.util.ArrayList;
import java.util.List;

import model.*;

public class Database {

	// Estructura Singleton
	private static Database instance = new Database();
	
	private Database() {}
	 
    public static Database getInstance() {
        return instance;
    }
    //
    
    // Tabla de Ventas
    public List<Sale> sales = new ArrayList<Sale>();
    
    // Tabla de artículos
    public List<Product> products = new ArrayList<Product>();
    
}
