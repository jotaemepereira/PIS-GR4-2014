package persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import datatypes.DTVenta;
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
    		
    		DTVenta dt = new DTVenta();
			dt.setCantidad(i);
			dt.setDescripcion("descripcion" + i);
			dt.setLaboratorio("laboratorio" + i);
			dt.setPrecioVenta(new BigDecimal(100+i));
			dt.setPrincipioActivo("principioActivo" + i);
    		dt.setCodigoBarras("" + i);
			dt.setPresentacion("presentacion" + i);
    		ventas.add(dt);		
    		i++;
    	}
		
	}

	public static Database getInstance() {
        return instance;
    }
    
    //Tabla de articulos buscados para la Venta
    private List<DTVenta> ventas = new ArrayList<DTVenta>();
    

	public List<DTVenta> getVentas() {
		return ventas;
	}

	public void setVentas(List<DTVenta> ventas) {
		this.ventas = ventas;
	}
    
}
