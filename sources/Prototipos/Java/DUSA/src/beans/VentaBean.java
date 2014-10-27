package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import model.Articulo;
import model.Enumerados;
import model.LineaVenta;
import model.Venta;
import controladores.Excepciones;
import controladores.FabricaSistema;
import datatypes.DTBusquedaArticuloSolr;
import datatypes.DTProveedor;
import datatypes.DTVenta;

@ManagedBean
@ViewScoped
public class VentaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal precioVenta = new BigDecimal(0);
	private String descripcionBusqueda;
	private String codigoBusqueda;
	
	private Venta venta = new Venta();
	private List<DTVenta> lineasVenta = new ArrayList<DTVenta>();
	private List<LineaVenta> lineasVenta2 = new ArrayList<LineaVenta>();
	private List<LineaVenta> lineasVentaPerdidas = new ArrayList<LineaVenta>();
	private String strDescuento = "";
	private boolean descuentoReceta1 = false;
	private boolean descuentoReceta2 = false;

	public VentaBean() {
		// agregarLineasVenta();
	}

	public void buscarArticulos(ActionEvent event) {
		
		// Busqueda con solr
		lineasVenta = new ArrayList<DTVenta>();
		try {
			lineasVenta = FabricaSistema.getISistema().buscarArticulosVenta(descripcionBusqueda);
			
			Iterator<DTVenta> it = lineasVenta.iterator();
			while (it.hasNext()) {
				DTVenta dtVenta = (DTVenta) it.next();
				System.out.println("precio " + dtVenta.getPrecioVenta());
				dtVenta.setDescuentoPrecio("$"+dtVenta.getPrecioVenta().toString()+"(%0)");	
			}
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	
	public void buscarArticuloLector(){
		//busca articulo cpor el codigo ingresado por el lector de codigo de barras y lo agrega a la venta.
		
				if(codigoBusqueda.equals("")){
					return;
				}
		
				List<DTVenta> lv = new ArrayList<DTVenta>();
				try {
					lv = FabricaSistema.getISistema().buscarArticulosVenta(codigoBusqueda);
					
					Iterator<DTVenta> it = lv.iterator();
					while (it.hasNext()) {
						DTVenta dtVenta = (DTVenta) it.next();
						agregarLineaVenta(dtVenta);
					}

				} catch (Excepciones e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/**
				
				// Probando con el Database.java para agregar a mano un codigo, simulando el lector de codigo de barras :
				Database DB = Database.getInstance();
				List<DTVenta> list = DB.getVentas();
				Iterator<DTVenta> it = list.iterator();
				while (it.hasNext()) {
					DTVenta v = it.next();
					if (!(codigoBusqueda.isEmpty())
							&& v.getCodigoBarras().contains(codigoBusqueda)) {
						agregarLineaVenta(v);
					}
				}
					
				**/
				
	}
	
	//para calcular el precio con el descuento a poner cuando lista los articulos en la busqueda:
	public void strDescuentoPrecio(){
		
		Iterator<DTVenta> it = lineasVenta.iterator();
		while (it.hasNext()) {
			DTVenta v = it.next();
		
		BigDecimal x = (v.getPrecioVenta().multiply(v.getDescuento())).divide(new BigDecimal(100));
		
		  v.setDescuentoPrecio("$"+v.getPrecioVenta().subtract(x).toString()+"("+"%"+v.getDescuento().toString()+")");
		
		}
		
	}
	
	public void agregarLineaVentaPerdida(){
		
		try {
			
			Venta v = new Venta();
			v.setLineas(lineasVenta2);
			v.setEstadoVenta("f"); // estado f seria la venta perdida
			FabricaSistema.getISistema().registrarNuevaVenta(v);

		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lineasVenta2 = new ArrayList<LineaVenta>();
		lineasVenta = new ArrayList<DTVenta>();
		
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Venta perdida ingresada con éxito", ""));
	}
	
	public void facturarVenta() {
		
		//esto no va es solo para cargar articulos para prueba //
		try {
			FabricaSistema.getISistema().actualizarStock();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//////////////////////////////////////////////////////////
		
		try {
			
			Venta v = new Venta();
			v.setLineas(lineasVenta2);
			v.setTotalIvaBasico(new BigDecimal(0));
			v.setTotalIvaMinimo(new BigDecimal(0));
			
			v.setCantidadLineas(lineasVenta2.size());
			
			
			Iterator<LineaVenta> it = lineasVenta2.iterator();
			while(it.hasNext()){
				LineaVenta lv = it.next();
				if (lv.getIndicadorFacturacion() == Enumerados.indicadoresFacturacion.BASICO){
					v.setTotalIvaBasico(v.getTotalIvaBasico().add(lv.getIva()));					
				} else if(lv.getIndicadorFacturacion() == Enumerados.indicadoresFacturacion.MINIMO) {
					v.setTotalIvaMinimo(v.getTotalIvaMinimo().add(lv.getIva()));
				}
				
			}
			
			v.setEstadoVenta("p"); // estado p seria la venta pendiente
			
			FabricaSistema.getISistema().registrarNuevaVenta(v);

		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lineasVenta2 = new ArrayList<LineaVenta>();
		lineasVenta = new ArrayList<DTVenta>();
		
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Factura ingresada con éxito", ""));
	}

	public void agregarLineaVenta(DTVenta v){
		// pasa del DTVenta a una LineaVenta los datos.
		v.setCantidad(1);
		LineaVenta e = new LineaVenta();
		
		e.setDescuentoReceta(v.getDescuentoReceta());
		e.setPrecio(v.getPrecioVenta());
		e.setCantidad(v.getCantidad());
		e.setDescuento(v.getDescuento());
		e.setRecetaBlanca(v.isRecetaBlanca());
		e.setRecetaNaranja(v.isRecetaNaranja());
		e.setRecetaVerde(v.isRecetaVerde());
		e.setProductoId(v.getProductId());

		Articulo a = new Articulo();
		a.setPrecioVenta(v.getPrecioVenta());
		a.setDescripcion(v.getDescripcion());
		a.setCodigoBarras(v.getCodigoBarras());
		a.setStock(v.getStock());
		a.setPresentacion(v.getPresentacion());
		a.setIdArticulo(v.getProductId());
		e.setArticulo(a);
		
		lineasVenta2.add(e);
		lineasVenta.remove(v);
	}

	public String strTotal() {
		BigDecimal total = new BigDecimal(0);
		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();
			//calculo lo que tengo que restarle al precio segun el descuento seleccionado:
			BigDecimal x = (v.getArticulo().getPrecioVenta().multiply(v.getDescuento())).divide(new BigDecimal(100));
			
			BigDecimal n = new BigDecimal(0);
			//calculo descuento por receta blanca 1 del 25%
			if (v.getDescuentoReceta().equals("25")){
				n = (v.getArticulo().getPrecioVenta().multiply(new BigDecimal(25))).divide(new BigDecimal(100));
			}
			//calculo descuento por receta blanca 2 del 40%
			if (v.getDescuentoReceta().equals("40")){
				n = (v.getArticulo().getPrecioVenta().multiply(new BigDecimal(30))).divide(new BigDecimal(100));
			}
			
			//sumo los totales restandole los descuentos correspondientes a cada uno y los multiplico por las cantidades
			total = total.add(((v.getArticulo().getPrecioVenta().subtract(x)).subtract(n)).multiply(
					new BigDecimal(v.getCantidad())));
			
		}
		venta.setMontoTotalAPagar(total);
		return total.toString();
	}
	
	public String strSubTotal() {
		BigDecimal total = new BigDecimal(0);
		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();
			total = total.add((v.getArticulo()).getPrecioVenta().multiply(
					new BigDecimal(v.getCantidad())));
		}
		venta.setMontoTotal(total);
		return total.toString();
	}

	public List<DTVenta> getLineasVenta() {
		return lineasVenta;
	}

	public void setLineasVenta(List<DTVenta> lineasVenta) {
		this.lineasVenta = lineasVenta;
	}

	public List<LineaVenta> getLineasVenta2() {
		return lineasVenta2;
	}

	public void setLineasVenta2(List<LineaVenta> lineasVenta2) {
		this.lineasVenta2 = lineasVenta2;
	}

	public String getDescripcionBusqueda() {
		return descripcionBusqueda;
	}

	public void setDescripcionBusqueda(String descripcionBusqueda) {
		this.descripcionBusqueda = descripcionBusqueda;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public List<LineaVenta> getLineasVentaPerdidas() {
		return lineasVentaPerdidas;
	}

	public void setLineasVentaPerdidas(List<LineaVenta> lineasVentaPerdidas) {
		this.lineasVentaPerdidas = lineasVentaPerdidas;
	}

	public String getStrDescuento() {
		return strDescuento;
	}

	public void setStrDescuento(String strDescuento) {
		this.strDescuento = strDescuento;
	}

	public boolean isDescuentoReceta1() {
		return descuentoReceta1;
	}

	public void setDescuentoReceta1(boolean descuentoReceta1) {
		this.descuentoReceta1 = descuentoReceta1;
	}

	public boolean isDescuentoReceta2() {
		return descuentoReceta2;
	}

	public void setDescuentoReceta2(boolean descuentoReceta2) {
		this.descuentoReceta2 = descuentoReceta2;
	}

	public String getCodigoBusqueda() {
		return codigoBusqueda;
	}

	public void setCodigoBusqueda(String codigoBusqueda) {
		this.codigoBusqueda = codigoBusqueda;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}


}
