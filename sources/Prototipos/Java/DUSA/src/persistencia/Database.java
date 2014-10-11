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
    	BigDecimal desc1 = new BigDecimal(10);
    	BigDecimal desc2 = new BigDecimal(30);
    	BigDecimal desc3 = new BigDecimal(50);
    	
    	while (i < 100){
    		DTVenta dt = new DTVenta();
			dt.setCantidad(i);
			dt.setDescripcion("descripcion" + i);
			dt.setLaboratorio("laboratorio" + i);
			dt.setPrecioVenta(new BigDecimal(100+i));
			dt.setPrincipioActivo("principioActivo" + i);
    		dt.setCodigoBarras("" + i);
			dt.setPresentacion("presentacion" + i);
			dt.setNombre("nombre" + 1);
			dt.setConcentracion("concentracion" + i);
			dt.setDescuento1(desc1);
			dt.setDescuento2(desc2);
			dt.setDescuento3(desc3);
			if (i%2==0){
				dt.setRecetaBlanca(true);
				dt.setRecetaNaranja(true);
				dt.setRecetaVerde(true);
			}else{
				dt.setRecetaBlanca(false);
				dt.setRecetaNaranja(false);
				dt.setRecetaVerde(false);
			}
    		ventas.add(dt);		
    		i++;
    	}
		
	}

	public static Database getInstance() {
        return instance;
    }
    
	private DTVenta venta = new DTVenta();
	
    //Tabla de articulos buscados para la Venta
    private List<DTVenta> ventas = new ArrayList<DTVenta>();
    

	public List<DTVenta> getVentas() {
		return ventas;
	}

	public void setVentas(List<DTVenta> ventas) {
		this.ventas = ventas;
	}

	public DTVenta getVenta() {
		return venta;
	}

	public void setVenta(DTVenta venta) {
		this.venta = venta;
	}
    
}
