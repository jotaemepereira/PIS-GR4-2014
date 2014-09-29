package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import datatypes.DTVenta;

@ManagedBean
@ViewScoped
public class VentaBean implements Serializable {
	     
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String descripcion = "probandooooooooooo";
private String presentacion = "presentacionprueba";
private String principioActivo = "pinc. Act prueba";
private String laboratorio = "Laboratorio prueba";
private BigDecimal precioVenta = new BigDecimal(0);

private DTVenta venta = new DTVenta();
private List<DTVenta> lineasVenta = new ArrayList<DTVenta>();
private List<DTVenta> lineasVenta2 = new ArrayList<DTVenta>();
private List<DTVenta> ventasSeleccionadas = new ArrayList<DTVenta>();


public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

public void agregarLineasVenta() {
	//aca en realidad hay q buscar las ventas con el buscarArticulo y agregar todos los que coinciden con la descripcion buscados
	for (int i=0; i<5; i++) {
		DTVenta dt = new DTVenta();
		dt.setCantidad(i);
		dt.setDescripcion(descripcion);
		dt.setLaboratorio(laboratorio);
		dt.setPrecioVenta(precioVenta);
		dt.setPresentacion(presentacion);
		dt.setPrincipioActivo(principioActivo);
		lineasVenta.add(dt);
	}
}

public void agregarVenta(List<DTVenta> v){
	Iterator<DTVenta> it = v.iterator();
	while (it.hasNext()){
		DTVenta dt = new DTVenta();
		dt = it.next();
		lineasVenta2.add(dt);
	}
}

public String strTotal(){
	BigDecimal total = new BigDecimal(0);
	Iterator<DTVenta> it = lineasVenta2.iterator();
	while (it.hasNext()){
		total = total.add(it.next().getPrecioVenta());
	}
	return total.toString();
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

public DTVenta getVenta() {
	return venta;
}

public void setVenta(DTVenta venta) {
	this.venta = venta;
}

public List<DTVenta> getVentasSeleccionadas() {
	return ventasSeleccionadas;
}

public void setVentasSeleccionadas(List<DTVenta> ventasSeleccionadas) {
	this.ventasSeleccionadas = ventasSeleccionadas;
}


}
