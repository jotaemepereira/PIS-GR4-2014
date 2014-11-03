package beans;

import interfaces.ISistema;

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
import model.Usuario;
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
	
	private ISistema instanciaSistema;
	
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
			lineasVenta = FabricaSistema.getISistema().buscarArticulosVenta(
					descripcionBusqueda);

			Iterator<DTVenta> it = lineasVenta.iterator();
			while (it.hasNext()) {
				DTVenta dtVenta = (DTVenta) it.next();
				System.out.println("precio " + dtVenta.getPrecioVenta());
				dtVenta.setDescuentoPrecio("$"
						+ dtVenta.getPrecioVenta().toString() + "(%0)");
			}
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buscarArticuloLector() {
		// busca articulo cpor el codigo ingresado por el lector de codigo de
		// barras y lo agrega a la venta.

		if (codigoBusqueda.equals("")) {
			return;
		}

		List<DTVenta> lv = new ArrayList<DTVenta>();
		try {
			lv = FabricaSistema.getISistema().buscarArticulosVenta(
					codigoBusqueda);

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
		 * // Probando con el Database.java para agregar a mano un codigo,
		 * simulando el lector de codigo de barras : Database DB =
		 * Database.getInstance(); List<DTVenta> list = DB.getVentas();
		 * Iterator<DTVenta> it = list.iterator(); while (it.hasNext()) {
		 * DTVenta v = it.next(); if (!(codigoBusqueda.isEmpty()) &&
		 * v.getCodigoBarras().contains(codigoBusqueda)) { agregarLineaVenta(v);
		 * } }
		 **/

	}

	// para calcular el precio con el descuento a poner cuando lista los
	// articulos en la busqueda:
	public void strDescuentoPrecio() {

		Iterator<DTVenta> it = lineasVenta.iterator();
		while (it.hasNext()) {
			DTVenta v = it.next();

			BigDecimal x = (v.getPrecioVenta().multiply(v.getDescuento()))
					.divide(new BigDecimal(100));

			v.setDescuentoPrecio("$"
					+ v.getPrecioVenta().subtract(x).toString() + "(" + "%"
					+ v.getDescuento().toString() + ")");

		}

	}
	
	public void strDescuentoPrecio2() {

		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();

			BigDecimal x = (v.getArticulo().getPrecioVenta().multiply(v.getDescuento()))
					.divide(new BigDecimal(100));
			
			BigDecimal n = new BigDecimal(0);
			// calculo descuento por receta blanca 1 del 25%
			if (v.getDescuentoReceta().equals("25")) {
				n = (v.getArticulo().getPrecioVenta().multiply(new BigDecimal(
						25))).divide(new BigDecimal(100));
			}
			// calculo descuento por receta blanca 2 del 40%
			if (v.getDescuentoReceta().equals("40")) {
				n = (v.getArticulo().getPrecioVenta().multiply(new BigDecimal(
						30))).divide(new BigDecimal(100));
			}

			v.setTotalPrecioLinea("$"
					+ (v.getArticulo().getPrecioVenta().subtract(x)).subtract(n).multiply(new BigDecimal(v.getCantidad())).toString() );

		}

	}

	public void agregarVentaPerdida() {
		
		if (!lineasVenta2.isEmpty()){
			
			try {

				venta.setLineas(lineasVenta2);
				venta.setTotalIvaBasico(new BigDecimal(0));
				venta.setTotalIvaMinimo(new BigDecimal(0));
				// Agarrar el usuario logueado
				Usuario usr = new Usuario();
				usr = instanciaSistema.obtenerUsuarioLogueado();
				venta.setUsuario(usr);
				
				// TODO ver como se elige la forma de pago.
				venta.setFormaDePago(Enumerados.TipoFormaDePago.CONTADO.toString());
				venta.setCantidadLineas(lineasVenta2.size());

				venta.setEstadoVenta(String
						.valueOf(Enumerados.EstadoVenta.PERDIDA)); // estado X
																		// seria la
																		// venta
																		// perdida

				FabricaSistema.getISistema().registrarNuevaVenta(venta);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Venta perdida ingresada con éxito", ""));
			} catch (Excepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e
								.getMessage(), ""));
			}

			lineasVenta2 = new ArrayList<LineaVenta>();
			lineasVenta = new ArrayList<DTVenta>();
			
		}else{
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Debe ingresar al menos un articulo para registrar la venta perdida", ""));	
		}

	}

	public void facturarVenta() {

		// esto no va es solo para cargar articulos para prueba //
		// try {
		// FabricaSistema.getISistema().actualizarStock();
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// ////////////////////////////////////////////////////////
		
		if (!lineasVenta2.isEmpty()){
			
			try {

				venta.setLineas(lineasVenta2);
				venta.setTotalIvaBasico(new BigDecimal(0));
				venta.setTotalIvaMinimo(new BigDecimal(0));
				// Agarrar el usuario logueado
				Usuario usr = new Usuario();
				usr = instanciaSistema.obtenerUsuarioLogueado();
				venta.setUsuario(usr);
				// TODO ver como se elige la forma de pago.
				venta.setFormaDePago(Enumerados.TipoFormaDePago.CONTADO.toString());
				venta.setCantidadLineas(lineasVenta2.size());

				venta.setEstadoVenta(String
						.valueOf(Enumerados.EstadoVenta.PENDIENTE)); // estado p
																		// seria la
																		// venta
																		// pendiente

				FabricaSistema.getISistema().registrarNuevaVenta(venta);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Factura ingresada con éxito", ""));
			} catch (Excepciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e
								.getMessage(), ""));
			}

			lineasVenta2 = new ArrayList<LineaVenta>();
			lineasVenta = new ArrayList<DTVenta>();
			
		}else{
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Debe ingresar al menos un articulo para enviar a facturar", ""));
		}
		
		
	}

	public void agregarLineaVenta(DTVenta v) {
		// pasa del DTVenta a una LineaVenta los datos.
		v.setCantidad(1);
		v.setDescuentoReceta("");
		LineaVenta e = new LineaVenta();
		e.setTotalPrecioLinea("$"+ (v.getPrecioVenta().subtract((v.getPrecioVenta().multiply(v.getDescuento())).divide(new BigDecimal(100)))).toString());
		e.setLinea(lineasVenta2.size() + 1);
		e.setDescuentoReceta(v.getDescuentoReceta());
		e.setPrecio(v.getPrecioVenta());
		e.setCantidad(v.getCantidad());
		e.setDescuento(v.getDescuento());
		e.setRecetaBlanca(v.isRecetaBlanca());
		e.setRecetaNaranja(v.isRecetaNaranja());
		e.setRecetaVerde(v.isRecetaVerde());
		e.setProductoId(v.getProductId());
		e.setDescripcionOferta("Falta ver este tema");
		e.setDescuentoPrecio(v.getDescuentoPrecio());
		e.setIva(v.getIva());
		e.setIndicadorFacturacion(v.getIndicadorFacturacion());
		

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
	
	public String strIva(){
		
		BigDecimal totIva = new BigDecimal(0);
		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();
			// calculo lo que tengo que restarle al precio segun el valor del IVA
			BigDecimal iva = (v.getArticulo().getPrecioVenta().multiply(v
					.getIva())).divide(new BigDecimal(100));

			// calculo para IVA del 10%
			if (v.getIva().equals(new BigDecimal(22)) ) {
				
				venta.setMontoNetoGravadoIvaBasico(v.getPrecio().subtract(iva));
				venta.setMontoTributoIvaBasico(iva);
				venta.setTotalIvaBasico(venta.getTotalIvaBasico().add(iva) );
			}
			// calculo para IVA del 22%
			if (v.getIva().equals(new BigDecimal(10)) ) {
				
				venta.setMontoNetoGravadoIvaMinimo(v.getPrecio().subtract(iva));
				venta.setMontoTributoIvaMinimo(iva);
				venta.setTotalIvaMinimo(venta.getTotalIvaMinimo().add(iva) );
			}

			// sumo los totales restandole los IVA correspondientes a
			// cada uno y los multiplico por las cantidades
			totIva = totIva.add(iva).multiply(new BigDecimal (v.getCantidad() ) );
		}
		return totIva.toString();
	}

	public String strTotal() {
		BigDecimal total = new BigDecimal(0);
		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();
			// calculo lo que tengo que restarle al precio segun el descuento
			// seleccionado:
			BigDecimal x = (v.getArticulo().getPrecioVenta().multiply(v
					.getDescuento())).divide(new BigDecimal(100));

			BigDecimal n = new BigDecimal(0);
			// calculo descuento por receta blanca 1 del 25%
			if (v.getDescuentoReceta().equals("25")) {
				n = (v.getArticulo().getPrecioVenta().multiply(new BigDecimal(
						25))).divide(new BigDecimal(100));
			}
			// calculo descuento por receta blanca 2 del 40%
			if (v.getDescuentoReceta().equals("40")) {
				n = (v.getArticulo().getPrecioVenta().multiply(new BigDecimal(
						30))).divide(new BigDecimal(100));
			}

			// sumo los totales restandole los descuentos correspondientes a
			// cada uno y los multiplico por las cantidades
			total = total.add(((v.getArticulo().getPrecioVenta().subtract(x))
					.subtract(n)).multiply(new BigDecimal(v.getCantidad())));

		}
		venta.setMontoTotalAPagar(total);
		return total.toString();
	}

	public String strSubTotal() {
		BigDecimal total = new BigDecimal(0);
		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();
			
			// calculo lo que tengo que restarle al precio segun el valor del
			// IVA
			BigDecimal iva = (v.getArticulo().getPrecioVenta().multiply(v
					.getIva())).divide(new BigDecimal(100));
						
			total = total.add( ( (v.getArticulo()).getPrecioVenta().subtract(iva) ).multiply( new BigDecimal( v.getCantidad() ) ) );
			
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
