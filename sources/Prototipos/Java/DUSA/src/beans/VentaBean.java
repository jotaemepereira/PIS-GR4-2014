package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import controladores.Excepciones;
import controladores.FabricaSistema;
import persistencia.Database;
import datatypes.DTBusquedaArticulo;
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
	private String descripcionBusqueda;
	private String nombre = "nombre";
	
	private DTVenta venta = new DTVenta();
	private List<DTVenta> lineasVenta = new ArrayList<DTVenta>();
	private List<DTVenta> lineasVenta2 = new ArrayList<DTVenta>();
	private List<DTVenta> lineasVentaPerdidas = new ArrayList<DTVenta>();
	private List<DTVenta> ventasSeleccionadas = new ArrayList<DTVenta>();
	private String strDescuento;

	public VentaBean() {
		// agregarLineasVenta();
	}

	public void buscarArticulos(ActionEvent event) {
		System.out.println("buscar articulos");
		// aca en realidad hay q buscar las ventas con el buscarArticulo y
		// agregar todos los que coinciden con la descripcion buscados

		// Probando con el Database.java para buscar simulando la busqueda :
		Database DB = Database.getInstance();
		List<DTVenta> list = DB.getVentas();
		Iterator<DTVenta> it = list.iterator();
		lineasVenta = new ArrayList<DTVenta>();
		while (it.hasNext()) {
			DTVenta v = it.next();
			if (!(descripcionBusqueda.isEmpty())
					&& v.getDescripcion().contains(descripcionBusqueda)) {
				v.setDescuentoPrecio("$"+v.getPrecioVenta().toString()+"(%0)");
				lineasVenta.add(v);
			}
		}
		
		/**
		
		// Busqueda con solr
		lineasVenta = new ArrayList<DTVenta>();
		try {
			lineasVenta = FabricaSistema.getISistema().buscarArticulosVenta(descripcionBusqueda);
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		**/
		

	}
	
	/**public void iniDescuentos(){
		// inicializo los descuentos ya fijos q puede seleccionar el vendedor:
		descuentos = new ArrayList<Integer>();
		int x = 5;
		while (x <= 15) {
			descuentos.add(x);
			x = x + 5;
		}
	}**/
	
	//para calcular el precio con el descuento a poner cuando lista los articulos en la busqueda, falta terminar
	public void strDescuentoPrecio(){
		
		Iterator<DTVenta> it = lineasVenta.iterator();
		while (it.hasNext()) {
			DTVenta v = it.next();
		
		BigDecimal x = (v.getPrecioVenta().multiply(v.getDescuento())).divide(new BigDecimal(100));
		
		  v.setDescuentoPrecio("$"+v.getPrecioVenta().subtract(x).toString()+"("+"%"+v.getDescuento().toString()+")");
		
		}
		
	}
	
	public void agregarLineaVentaPerdida(DTVenta vp){
		vp.setCantidad(1);
		lineasVentaPerdidas.add(vp);
	}
	


	public void agregarLineaVenta(DTVenta v){

		v.setCantidad(1);
		lineasVenta2.add(v);
		lineasVenta.remove(v);
	}
	
	// este agregar es para agregar los productos buscados a la venta
	/**public void agregarLinea(ActionEvent actionEvent) {

		Iterator<DTVenta> it = ventasSeleccionadas.iterator();
		while (it.hasNext()) {
			DTVenta d = it.next();
			// Aca se hace el descuento correspondiente:
			BigDecimal x = (d.getPrecioVenta().multiply(d.getDescuento()));
			BigDecimal a = new BigDecimal(100);
			d.setPrecioVenta(d.getPrecioVenta().subtract(x.divide(a)));
			d.setCantidad(1);
			lineasVenta2.add(d);

		}

	}**/

	public String strTotal() {
		BigDecimal total = new BigDecimal(0);
		Iterator<DTVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			DTVenta v = it.next();
			//calculo lo que tengo que restarle al precio segun el descuento seleccionado:
			BigDecimal x = (v.getPrecioVenta().multiply(v.getDescuento())).divide(new BigDecimal(100));
			
			//sumo los totales restandole los descuentos correspondientes a cada uno y los multiplico por las cantidades
			total = total.add((v.getPrecioVenta().subtract(x)).multiply(
					new BigDecimal(v.getCantidad())));
		}
		return total.toString();
	}
	
	public String strSubTotal() {
		BigDecimal total = new BigDecimal(0);
		Iterator<DTVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			DTVenta v = it.next();
			total = total.add(v.getPrecioVenta().multiply(
					new BigDecimal(v.getCantidad())));
		}
		return total.toString();
	}

	public void facturarVenta() {
		ventasSeleccionadas = new ArrayList<DTVenta>();
		lineasVenta2 = new ArrayList<DTVenta>();
		lineasVenta = new ArrayList<DTVenta>();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Factura ingresada con éxito", ""));
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
	

	public String getDescripcionBusqueda() {
		return descripcionBusqueda;
	}

	public void setDescripcionBusqueda(String descripcionBusqueda) {
		this.descripcionBusqueda = descripcionBusqueda;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public String getPrincipioActivo() {
		return principioActivo;
	}

	public void setPrincipioActivo(String principioActivo) {
		this.principioActivo = principioActivo;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<DTVenta> getLineasVentaPerdidas() {
		return lineasVentaPerdidas;
	}

	public void setLineasVentaPerdidas(List<DTVenta> lineasVentaPerdidas) {
		this.lineasVentaPerdidas = lineasVentaPerdidas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getStrDescuento() {
		return strDescuento;
	}

	public void setStrDescuento(String strDescuento) {
		this.strDescuento = strDescuento;
	}


}
