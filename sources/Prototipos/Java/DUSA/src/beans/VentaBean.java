package beans;

import interfaces.IFacturacion;
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
import controladores.FabricaLogica;
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
	private DTVenta articuloSeleccionado = new DTVenta();
	private String strDescuento = "";
	private boolean descuentoReceta1 = false;
	private boolean descuentoReceta2 = false;

	private boolean ventaFacturacion = false;

	public VentaBean() {

		try {
			// me fijo que tenga el permiso para facturar y ademas que este en
			// modo 1 o en modo 2 para habilitar el modo ventaFacturacion y que
			// pueda facturar directo desde la venta
			ventaFacturacion = ( (this.instanciaSistema.obtenerUsuarioLogueado()
					.tienePermiso(Enumerados.casoDeUso.facturarVentaPendiente)) && ( (Integer
					.parseInt(FacesContext.getCurrentInstance()
							.getExternalContext()
							.getInitParameter("MODO_FACTURACION")) == Enumerados.modoFacturacion.basica) || (Integer
					.parseInt(FacesContext.getCurrentInstance()
							.getExternalContext()
							.getInitParameter("MODO_FACTURACION")) == Enumerados.modoFacturacion.controlada) ) );

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Excepciones.MENSAJE_ERROR_SISTEMA, ""));
		}
	}

	public void buscarArticulos(ActionEvent event) {

		// Busqueda con solr
		lineasVenta = new ArrayList<DTVenta>();
		try {
			lineasVenta = FabricaSistema.getISistema().buscarArticulosVenta(
					descripcionBusqueda);

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
			BigDecimal x = new BigDecimal(0);
			
			if (  (v.getDescuento().compareTo(new BigDecimal(101)) == -1) && (v.getDescuento().compareTo(new BigDecimal(-1)) == 1)   ){
				
				x = (v.getPrecioVenta().multiply(v.getDescuento()))
						.divide(new BigDecimal(100));
			}else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"el descuento ingresado debe ser un numero entre 0 y 100",
										""));
			}

			v.setDescuentoPrecio("$"
					+ v.getPrecioVenta().subtract(x).toString() + "(" + "%"
					+ v.getDescuento().toString() + ")");

		}

	}

	
	//calculo el total del precio de un articulo
	public void strDescuentoPrecio2() {

		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();
			
			BigDecimal n = new BigDecimal(0);
			BigDecimal x = new BigDecimal(0);
			
			if (  (v.getDescuento().compareTo(new BigDecimal(101)) == -1) && (v.getDescuento().compareTo(new BigDecimal(-1)) == 1)   ){
				
				x = (v.getArticulo().getPrecioVenta().multiply(v
						.getDescuento())).divide(new BigDecimal(100));
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
			}else {

				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"el descuento ingresado debe ser un numero entre 0 y 100",
										""));
			}

			v.setTotalPrecioLinea("$"
					+ (v.getArticulo().getPrecioVenta().subtract(x))
							.subtract(n)
							.multiply(new BigDecimal(v.getCantidad()))
							.toString());

		}

	}
	
	public void eliminarLineaVenta(LineaVenta lv){
			lineasVenta2.remove(lv);			
	}

	public void agregarVentaPerdida() {

		if (!lineasVenta2.isEmpty()) {

			try {

				venta.setLineas(lineasVenta2);
				venta.setTotalIvaBasico(new BigDecimal(0));
				venta.setTotalIvaMinimo(new BigDecimal(0));
				// Agarrar el usuario logueado
				Usuario usr = new Usuario();
				// TODO
				usr.setNombre("Admin");
				// usr = this.instanciaSistema.obtenerUsuarioLogueado();
				venta.setUsuario(usr);

				// TODO ver como se elige la forma de pago.
				venta.setFormaDePago(Enumerados.TipoFormaDePago.CONTADO
						.toString());
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

				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e
								.getMessage(), ""));
			}

			lineasVenta2 = new ArrayList<LineaVenta>();
			lineasVenta = new ArrayList<DTVenta>();

		} else {

			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Debe ingresar al menos un articulo para registrar la venta perdida",
									""));
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

		if (!lineasVenta2.isEmpty()) {

			try {

				venta.setLineas(lineasVenta2);
				venta.setTotalIvaBasico(new BigDecimal(0));
				venta.setTotalIvaMinimo(new BigDecimal(0));
				// Agarrar el usuario logueado
				Usuario usr = new Usuario();
				// usr = this.instanciaSistema.obtenerUsuarioLogueado();
				// TODO
				usr.setNombre("Admin");
				venta.setUsuario(usr);
				// TODO ver como se elige la forma de pago.
				venta.setFormaDePago(Enumerados.TipoFormaDePago.CONTADO
						.toString());
				venta.setCantidadLineas(lineasVenta2.size());

				venta.setEstadoVenta(String
						.valueOf(Enumerados.EstadoVenta.PENDIENTE)); // estado p
																		// seria
																		// la
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

		} else {

			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Debe ingresar al menos un articulo para enviar a facturar",
									""));
		}

	}

	public void facturarVentaDirecto() {

		if (!lineasVenta2.isEmpty()) {

			try {
				venta.setLineas(lineasVenta2);
				venta.setTotalIvaBasico(new BigDecimal(0));
				venta.setTotalIvaMinimo(new BigDecimal(0));
				// Agarrar el usuario logueado
				// TODO
				Usuario usr = new Usuario();
				// usr = this.instanciaSistema.obtenerUsuarioLogueado();
				usr.setNombre("Admin");
				venta.setUsuario(usr);
				// TODO ver como se elige la forma de pago.
				venta.setFormaDePago(Enumerados.TipoFormaDePago.CONTADO
						.toString());
				venta.setCantidadLineas(lineasVenta2.size());

				venta.setEstadoVenta(String
						.valueOf(Enumerados.EstadoVenta.PENDIENTE)); // estado p
																		// seria
																		// la
																		// venta
																		// pendiente
				FabricaSistema.getISistema().registrarNuevaVenta(venta);

				// la venta ya esta guardada en el sistema y ahora se factura:

				IFacturacion ifact = FabricaLogica.getIFacturacion();
				// de todas las ventas pendientes obtengo la de mayor Id que
				// fue la ultima ingresada en el sistema.
				// TODO revisar esto si esta bien asi o si hay alguna otra forma
				// de obtener el id.
				List<Venta> ventas = ifact.listarVentasPendientes();
				Iterator<Venta> it = ventas.iterator();
				long maxId = 0;
				while (it.hasNext()) {

					long vId = it.next().getVentaId();
					if (vId > maxId) {
						maxId = vId;
					}
				}

				ifact.facturarVenta(maxId);

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Venta facturada con éxito", ""));

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Excepciones.MENSAJE_ERROR_SISTEMA, ""));
			}

			lineasVenta2 = new ArrayList<LineaVenta>();
			lineasVenta = new ArrayList<DTVenta>();

		} else {

			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Debe ingresar al menos un articulo para facturar la venta",
									""));
		}

	}

	public void agregarLineaVenta(DTVenta v) {

		// pasar del DTVenta a una LineaVenta los datos.
		if (v.getCantidad()==0){
			v.setCantidad(1);
		}
		v.setDescuentoReceta("");
		LineaVenta e = new LineaVenta();
		e.setTotalPrecioLinea("$"
				+ (v.getPrecioVenta().subtract((v.getPrecioVenta().multiply(v
						.getDescuento())).divide(new BigDecimal(100))))
						.toString());
		e.setLinea(lineasVenta2.size() + 1);
		e.setDescuentoReceta(v.getDescuentoReceta());
		e.setPrecio(v.getPrecioVenta());
		e.setCantidad(v.getCantidad());
		e.setDescuento(v.getDescuento());
		e.setRecetaBlanca(v.isRecetaBlanca());
		e.setRecetaNaranja(v.isRecetaNaranja());
		e.setRecetaVerde(v.isRecetaVerde());
		e.setProductoId(v.getProductId());
		//TODO ver el tema de descripcion oferta:
		e.setDescripcionOferta("Falta ver este tema");
		e.setDescuentoPrecio(v.getDescuentoPrecio());
		e.setDescuentoPrecio(articuloSeleccionado.getDescuentoPrecio());
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
		
		//controlo que el articulo no este ingresado ya en la venta:
		if (!lineasVenta2.isEmpty()){
			Iterator<LineaVenta> it = lineasVenta2.iterator();
			boolean salir = true;
			boolean encontre = false;
			while (it.hasNext() && (salir)) {
				LineaVenta lv = it.next();
				
				if (lv.getProductoId() == e.getProductoId()) {
					salir = false;
					encontre = true;
					lv.setCantidad(lv.getCantidad() + 1);
				}
			}
			if (!encontre) {
				lineasVenta2.add(e);
			}
			
		}else{
			lineasVenta2.add(e);
		}
		
		lineasVenta.remove(v);
	}

	public String strIva() {

		BigDecimal totIva = new BigDecimal(0);
		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();
			// calculo lo que tengo que restarle al precio segun el valor del
			// IVA
			BigDecimal iva = (v.getArticulo().getPrecioVenta().multiply(v
					.getIva())).divide(new BigDecimal(100));

			// calculo para IVA del 10%
			if (v.getIva().equals(new BigDecimal(22))) {

				venta.setMontoNetoGravadoIvaBasico(v.getPrecio().subtract(iva));
				venta.setMontoTributoIvaBasico(iva);
				venta.setTotalIvaBasico(venta.getTotalIvaBasico().add(iva));
			}
			// calculo para IVA del 22%
			if (v.getIva().equals(new BigDecimal(10))) {

				venta.setMontoNetoGravadoIvaMinimo(v.getPrecio().subtract(iva));
				venta.setMontoTributoIvaMinimo(iva);
				venta.setTotalIvaMinimo(venta.getTotalIvaMinimo().add(iva));
			}

			// sumo los totales restandole los IVA correspondientes a
			// cada uno y los multiplico por las cantidades
			totIva = totIva.add(iva).multiply(new BigDecimal(v.getCantidad()));
		}
		return totIva.toString();
	}

	public String strTotal() {
		BigDecimal total = new BigDecimal(0);
		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();
			
			BigDecimal n = new BigDecimal(0);
			BigDecimal x = new BigDecimal(0);
			
//			if(!(v.getDescuentoPrecio().isEmpty())){
//				if(v.getDescuentoPrecio().equals("5") ){
//					v.setDescuento(new BigDecimal(5));
//				}else if(v.getDescuentoPrecio().equals("10") ){
//					v.setDescuento(new BigDecimal(10));
//				}else if(v.getDescuentoPrecio().equals("15") ){
//					v.setDescuento(new BigDecimal(15));
//				}
//			}
			
			
			if (  (v.getDescuento().compareTo(new BigDecimal(101)) == -1) && (v.getDescuento().compareTo(new BigDecimal(-1)) == 1)   ){
				
				// calculo lo que tengo que restarle al precio segun el descuento
				// seleccionado:
				x = (v.getArticulo().getPrecioVenta().multiply(v
						.getDescuento())).divide(new BigDecimal(100));

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
				}else {

					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"el descuento ingresado debe ser un numero entre 0 y 100",
											""));
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

			total = total
					.add(((v.getArticulo()).getPrecioVenta().subtract(iva))
							.multiply(new BigDecimal(v.getCantidad())));

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

	public boolean isVentaFacturacion() {
		return ventaFacturacion;
	}

	public void setVentaFacturacion(boolean ventaFacturacion) {
		this.ventaFacturacion = ventaFacturacion;
	}

	public DTVenta getArticuloSeleccionado() {
		return articuloSeleccionado;
	}

	public void setArticuloSeleccionado(DTVenta articuloSeleccionado) {
		this.articuloSeleccionado = articuloSeleccionado;
	}

}
