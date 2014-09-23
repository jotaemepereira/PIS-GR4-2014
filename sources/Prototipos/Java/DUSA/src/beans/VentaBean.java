package beans;
import java.util.ArrayList;
import java.util.List;

import model.Articulo;
import datatypes.DTVenta;

public class VentaBean {
	     
private String descripcion;
private DTVenta Venta = new DTVenta();
private List<DTVenta> Ventas = new ArrayList<DTVenta>();



public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

//para cargar unos datos para probar es este ini
public void init(Integer size) {
	List<DTVenta> list = new ArrayList<DTVenta>();
    for(int i = 0 ; i < size ; i++) {
        list.add(null);
    }
    setVentas(list);
}

public List<DTVenta> getVentas() {
	return Ventas;
}

public void setVentas(List<DTVenta> ventas) {
	Ventas = ventas;
}

public DTVenta getVenta() {
	return Venta;
}

public void setVenta(DTVenta venta) {
	Venta = venta;
}



}
