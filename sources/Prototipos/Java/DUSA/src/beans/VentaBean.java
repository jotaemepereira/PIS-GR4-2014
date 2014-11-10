package beans;

import interfaces.IFacturacion;
import interfaces.ISistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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

	private boolean ventaFacturacion = false;

	/**
	 * Utilizado en el xhtml por el loginBean
	 * 
	 * @param s
	 */
	public void setISistema(ISistema s) {

		this.instanciaSistema = s;
		if (this.instanciaSistema != null) {

			try {
				// me fijo que tenga el permiso para facturar y ademas que este
				// en
				// modo 1 o en modo 2 para habilitar el modo ventaFacturacion y
				// que
				// pueda facturar directo desde la venta
				ventaFacturacion = ((this.instanciaSistema
						.obtenerUsuarioLogueado()
						.tienePermiso(Enumerados.casoDeUso.facturarVentaPendiente)) && ((Integer
						.parseInt(FacesContext.getCurrentInstance()
								.getExternalContext()
								.getInitParameter("MODO_FACTURACION")) == Enumerados.modoFacturacion.basica) || (Integer
						.parseInt(FacesContext.getCurrentInstance()
								.getExternalContext()
								.getInitParameter("MODO_FACTURACION")) == Enumerados.modoFacturacion.controlada)));

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Excepciones.MENSAJE_ERROR_SISTEMA, ""));
			}
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
				articuloSeleccionado = dtVenta;
				agregarLineaVenta();
			}

		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		codigoBusqueda = "";
	}

	
	//calculo el total del precio de un articulo
	public void strDescuentoPrecio2() {

		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();
			BigDecimal x = new BigDecimal(0);
			BigDecimal n = new BigDecimal(0);

			if ((v.getDescuento().compareTo(new BigDecimal(101)) == -1)
					&& (v.getDescuento().compareTo(new BigDecimal(-1)) == 1)) {

				x = (v.getArticulo().getPrecioVenta()
						.multiply(v.getDescuento()))
						.divide(new BigDecimal(100));
				// calculo descuento del 5%
				if (v.getDescuentoPrecio().equals("5")) {
					n = (v.getArticulo().getPrecioVenta()
							.multiply(new BigDecimal(5)))
							.divide(new BigDecimal(100));
				}
				// calculo descuento del 10%
				if (v.getDescuentoPrecio().equals("10")) {
					n = (v.getArticulo().getPrecioVenta()
							.multiply(new BigDecimal(10)))
							.divide(new BigDecimal(100));
				}
				// calculo descuento del 15%
				if (v.getDescuentoPrecio().equals("15")) {
					n = (v.getArticulo().getPrecioVenta()
							.multiply(new BigDecimal(10)))
							.divide(new BigDecimal(100));
				}
				
			} else {

				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"el descuento ingresado debe ser un numero entre 0 y 100",
										""));
			}
			
			if ( (v.getDescuento().compareTo(new BigDecimal(0)) == 0 ) && (!v.getDescuentoPrecio().equals("0") ) ) {
				
				v.setTotalPrecioLinea("$"
						+ ((v.getArticulo().getPrecioVenta().subtract(x)).subtract(n)).toString() + "(" + "%"
						+ v.getDescuentoPrecio() + ")" );
			}
			
			if ( (v.getDescuento().compareTo(new BigDecimal(0)) != 0 ) && (!v.getDescuentoPrecio().equals("0") ) ) {
				
				v.setTotalPrecioLinea("$"
						+ ((v.getArticulo().getPrecioVenta().subtract(x)).subtract(n)).toString() + "(" + "%"
						+ v.getDescuentoPrecio() + " + %" + v.getDescuento().toString() + ")" );
			}
			
			if ( (v.getDescuento().compareTo(new BigDecimal(0)) != 0 ) && (v.getDescuentoPrecio().equals("0") ) ) {
				
				v.setTotalPrecioLinea("$"
						+ ((v.getArticulo().getPrecioVenta().subtract(x)).subtract(n)).toString() + "(" + "%"
						+ v.getDescuento().toString() + ")" );
			}
			
			if ( (v.getDescuento().compareTo(new BigDecimal(0)) == 0 ) && (v.getDescuentoPrecio().equals("0") ) ) {
				
				v.setTotalPrecioLinea("$"
						+ ((v.getArticulo().getPrecioVenta().subtract(x)).subtract(n)).toString() + "(%0)" );
			}

		}

	}

	public void eliminarLineaVenta(LineaVenta lv) {
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
				usr = this.instanciaSistema.obtenerUsuarioLogueado();
				venta.setUsuario(usr);

				venta.setUsuario(this.instanciaSistema
						.obtenerUsuarioLogueado());
				// TODO ver como se elige la forma de pago.
				venta.setFormaDePago(Enumerados.TipoFormaDePago.CONTADO
						.toString());
				venta.setCantidadLineas(lineasVenta2.size());

				venta.setEstadoVenta(String
						.valueOf(Enumerados.EstadoVenta.PERDIDA)); // estado X seria la venta perdida

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

		if (!lineasVenta2.isEmpty()) {

			try {

				venta.setLineas(lineasVenta2);
				venta.setTotalIvaBasico(new BigDecimal(0));
				venta.setTotalIvaMinimo(new BigDecimal(0));
				// Agarrar el usuario logueado
				Usuario usr = new Usuario();
				usr = this.instanciaSistema.obtenerUsuarioLogueado();
				venta.setUsuario(usr);
				// TODO ver como se elige la forma de pago.
				venta.setFormaDePago(Enumerados.TipoFormaDePago.CONTADO
						.toString());
				venta.setCantidadLineas(lineasVenta2.size());

				venta.setEstadoVenta(String
						.valueOf(Enumerados.EstadoVenta.PENDIENTE)); // estado p seria la venta pendiente

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
				Usuario usr = new Usuario();
				usr = this.instanciaSistema.obtenerUsuarioLogueado();
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

				ifact.facturarVenta(venta);

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

	public void agregarLineaVenta() {

		// pasar del DTVenta a una LineaVenta los datos.
		if (articuloSeleccionado.getCantidad() == 0) {
			articuloSeleccionado.setCantidad(1);
		}
		articuloSeleccionado.setDescuentoReceta("");
		LineaVenta e = new LineaVenta();
		e.setTotalPrecioLinea("$"
				+ (articuloSeleccionado.getPrecioVenta().subtract((articuloSeleccionado.getPrecioVenta().multiply(articuloSeleccionado
						.getDescuento())).divide(new BigDecimal(100))))
						.toString() + "(%" + articuloSeleccionado.getDescuento() + ")" ) ;
		e.setLinea(lineasVenta2.size() + 1);
		e.setDescuentoReceta(articuloSeleccionado.getDescuentoReceta());
		e.setPrecio(articuloSeleccionado.getPrecioVenta());
		e.setCantidad(articuloSeleccionado.getCantidad());
		e.setDescuento(articuloSeleccionado.getDescuento());
		e.setRecetaBlanca(articuloSeleccionado.isRecetaBlanca());
		e.setRecetaNaranja(articuloSeleccionado.isRecetaNaranja());
		e.setRecetaVerde(articuloSeleccionado.isRecetaVerde());
		e.setProductoId(articuloSeleccionado.getProductId());
		//TODO ver el tema de descripcion oferta:
		e.setDescripcionOferta("Falta ver esto");
		e.setDescuentoPrecio(articuloSeleccionado.getDescuentoPrecio());
		e.setIva(articuloSeleccionado.getIva());
		e.setIndicadorFacturacion(articuloSeleccionado
				.getIndicadorFacturacion());

		Articulo a = new Articulo();
		a.setPrecioVenta(articuloSeleccionado.getPrecioVenta());
		a.setDescripcion(articuloSeleccionado.getDescripcion());
		a.setCodigoBarras(articuloSeleccionado.getCodigoBarras());
		a.setStock(articuloSeleccionado.getStock());
		a.setPresentacion(articuloSeleccionado.getPresentacion());
		a.setIdArticulo(articuloSeleccionado.getProductId());
		e.setArticulo(a);

		// controlo que el articulo no este ingresado ya en la venta:
		if (!lineasVenta2.isEmpty()) {
			Iterator<LineaVenta> it = lineasVenta2.iterator();
			boolean salir = true;
			boolean encontre = false;
			while (it.hasNext() && (salir)) {
				LineaVenta lv = it.next();

				if ((lv.getProductoId() == e.getProductoId())  // mismo articulo
					&& (lv.getDescuento().compareTo(e.getDescuento()) == 0) // mismo descuento
					&& (lv.getDescuentoPrecio().equals(e.getDescuentoPrecio())) // mismo descuento de los predefinidos
					&& (lv.isRecetaBlanca() == e.isRecetaBlanca()) ) {
					salir = false;
					encontre = true;
					lv.setCantidad(lv.getCantidad() + 1);
				}
			}
			if (!encontre) {
				lineasVenta2.add(e);
			}

		} else {
			lineasVenta2.add(e);
		}
		strDescuentoPrecio2();
		lineasVenta.remove(articuloSeleccionado);
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

	public void cancelarVenta() {

		lineasVenta2 = new ArrayList<LineaVenta>();
		lineasVenta = new ArrayList<DTVenta>();
		venta = new Venta();

	}

	public String strDescuentos() {

		BigDecimal total = new BigDecimal(0);
		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();
			BigDecimal x = new BigDecimal(0);
			BigDecimal n = new BigDecimal(0);
			
			if ((v.getDescuento().compareTo(new BigDecimal(101)) == -1)
					&& (v.getDescuento().compareTo(new BigDecimal(-1)) == 1)) {

				// calculo lo que tendría que restarle al precio segun el
				// descuento
				// seleccionado:
				x = (v.getArticulo().getPrecioVenta()
						.multiply(v.getDescuento()))
						.divide(new BigDecimal(100));
				// calculo descuento del 5%
				if (v.getDescuentoPrecio().equals("5")) {
					n = (v.getArticulo().getPrecioVenta()
							.multiply(new BigDecimal(5)))
							.divide(new BigDecimal(100));
				}
				// calculo descuento del 10%
				if (v.getDescuentoPrecio().equals("10")) {
					n = (v.getArticulo().getPrecioVenta()
							.multiply(new BigDecimal(10)))
							.divide(new BigDecimal(100));
				}
				// calculo descuento del 15%
				if (v.getDescuentoPrecio().equals("15")) {
					n = (v.getArticulo().getPrecioVenta()
							.multiply(new BigDecimal(10)))
							.divide(new BigDecimal(100));
				}
			} else {

				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"el descuento ingresado debe ser un numero entre 0 y 100",
										""));
			}
			total = total.add(x.multiply(new BigDecimal(v.getCantidad()))).add(n.multiply(new BigDecimal(v.getCantidad())));
		}
		return total.toString();
	}

	public String strTotal() {
		BigDecimal total = new BigDecimal(0);
		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();

			BigDecimal n = new BigDecimal(0);
			BigDecimal x = new BigDecimal(0);

			if ((v.getDescuento().compareTo(new BigDecimal(101)) == -1)
					&& (v.getDescuento().compareTo(new BigDecimal(-1)) == 1)) {

				// calculo lo que tengo que restarle al precio segun el
				// descuento
				// seleccionado:
				x = (v.getArticulo().getPrecioVenta()
						.multiply(v.getDescuento()))
						.divide(new BigDecimal(100));

				// calculo descuento del 5%
				if (v.getDescuentoPrecio().equals("5")) {
					n = (v.getArticulo().getPrecioVenta()
							.multiply(new BigDecimal(5)))
							.divide(new BigDecimal(100));
				}
				// calculo descuento del 10%
				if (v.getDescuentoPrecio().equals("10")) {
					n = (v.getArticulo().getPrecioVenta()
							.multiply(new BigDecimal(10)))
							.divide(new BigDecimal(100));
				}
				// calculo descuento del 15%
				if (v.getDescuentoPrecio().equals("15")) {
					n = (v.getArticulo().getPrecioVenta()
							.multiply(new BigDecimal(10)))
							.divide(new BigDecimal(100));
				}
				
			} else {

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
