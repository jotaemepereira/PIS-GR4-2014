package beans;
import java.util.ArrayList;
import java.util.List;

import datatypes.DTVenta;

public class VentaBean {
	     
private String descripcion = "probandooooooooooo";
private DTVenta Venta = new DTVenta();
private List<DTVenta> lineasVenta = new ArrayList<DTVenta>();
private List<DTVenta> lineasVenta2 = new ArrayList<DTVenta>();

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

public void agregarItem(DTVenta item) {
	lineasVenta.remove(item);
	lineasVenta2.add(item);
}

//para cargar unos datos para probar es este ini
public void init(Integer size) {
	List<DTVenta> list = new ArrayList<DTVenta>();
    for(int i = 0 ; i < size ; i++) {
        list.add(null);
    }
    setLineasVenta(list);
}

public DTVenta getVenta() {
	return Venta;
}

public void setVenta(DTVenta venta) {
	Venta = venta;
}

public List<DTVenta> getLineasVenta() {
	return lineasVenta;
}

public void setLineasVenta(List<DTVenta> lineasVenta) {
	this.lineasVenta = lineasVenta;
}

public List<DTVenta> getLineasVenta2() {
	return lineasVenta2;
}

public void setLineasVenta2(List<DTVenta> lineasVenta2) {
	this.lineasVenta2 = lineasVenta2;
}



}
