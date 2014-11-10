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
	private boolean descuentoReceta1 = false;
	private boolean descuentoReceta2 = false;

	private boolean ventaFacturacion = false;

	private BigDecimal subtotal = new BigDecimal(0);
	private BigDecimal iva10 = new BigDecimal(0);
	private BigDecimal iva22 = new BigDecimal(0);
	private BigDecimal total = new BigDecimal(0);
	private BigDecimal descuento = new BigDecimal(0);
	private BigDecimal montoNetoGravadoIvaMinimo = new BigDecimal(0);
	private BigDecimal totalIvaMinimo = new BigDecimal(0);
	private BigDecimal montoNetoGravadoIvaBasico = new BigDecimal(0);
	private BigDecimal montoTributoIvaBasico = new BigDecimal(0);
	private BigDecimal totalIvaBasico = new BigDecimal(0);

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
			lineasVenta = this.instanciaSistema.buscarArticulosVenta(
					descripcionBusqueda);

		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							e.getMessage(), ""));
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
			lv = this.instanciaSistema.buscarArticulosVenta(
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
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							e.getMessage(), ""));
		}
		codigoBusqueda = "";
	}

	// para calcular el precio con el descuento a poner cuando lista los
	// articulos en la busqueda:
	public void strDescuentoPrecio() {

		Iterator<DTVenta> it = lineasVenta.iterator();
		while (it.hasNext()) {
			DTVenta v = it.next();
			BigDecimal x = new BigDecimal(0);

			if ((v.getDescuento().compareTo(new BigDecimal(101)) == -1)
					&& (v.getDescuento().compareTo(new BigDecimal(-1)) == 1)) {

				x = (v.getPrecioVenta().multiply(v.getDescuento()))
						.divide(new BigDecimal(100));
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

			v.setDescuentoPrecio("$"
					+ v.getPrecioVenta().subtract(x).toString() + "(" + "%"
					+ v.getDescuento().toString() + ")");

		}

	}

	// calculo el total del precio de un articulo
	public void strDescuentoPrecio2() {

		Iterator<LineaVenta> it = lineasVenta2.iterator();
		while (it.hasNext()) {
			LineaVenta v = it.next();

			BigDecimal n = new BigDecimal(0);
			BigDecimal x = new BigDecimal(0);

			if ((v.getDescuento().compareTo(new BigDecimal(101)) == -1)
					&& (v.getDescuento().compareTo(new BigDecimal(-1)) == 1)) {

				x = (v.getArticulo().getPrecioVenta()
						.multiply(v.getDescuento()))
						.divide(new BigDecimal(100));
				// calculo descuento por receta blanca 1 del 25%
				if (v.getDescuentoReceta().equals("25")) {
					n = (v.getArticulo().getPrecioVenta()
							.multiply(new BigDecimal(25)))
							.divide(new BigDecimal(100));
				}
				// calculo descuento por receta blanca 2 del 40%
				if (v.getDescuentoReceta().equals("40")) {
					n = (v.getArticulo().getPrecioVenta()
							.multiply(new BigDecimal(30)))
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

			v.setTotalPrecioLinea("$"
					+ (v.getArticulo().getPrecioVenta().subtract(x))
							.subtract(n)
							.multiply(new BigDecimal(v.getCantidad()))
							.toString());

		}

	}

	public void eliminarLineaVenta(LineaVenta lv) {
		lineasVenta2.remove(lv);
		calcularTotales();
	}

	public void agregarVentaPerdida() {

		if (!lineasVenta2.isEmpty()) {

			try {

				venta.setLineas(lineasVenta2);
				venta.setUsuario(this.instanciaSistema.obtenerUsuarioLogueado());
				// TODO ver como se elige la forma de pago.
				venta.setFormaDePago(Enumerados.TipoFormaDePago.CONTADO
						.toString());
				venta.setCantidadLineas(lineasVenta2.size());

				venta.setEstadoVenta(String
						.valueOf(Enumerados.EstadoVenta.PERDIDA)); // estado X
																	// seria la
																	// venta
																	// perdida

				this.instanciaSistema.registrarNuevaVenta(venta);
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
									"Debe ingresar al menos un artículo para registrar la venta perdida",
									""));
		}

	}

	public void facturarVenta() {

		if (!lineasVenta2.isEmpty()) {

			try {

				venta.setLineas(lineasVenta2);
				Usuario usr = new Usuario();
				usr = this.instanciaSistema.obtenerUsuarioLogueado();
				// usr.setNombre("Admin");
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

				this.instanciaSistema.registrarNuevaVenta(venta);
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
									"Debe ingresar al menos un artículo para enviar a facturar",
									""));
		}

	}

	public void facturarVentaDirecto() {

		if (!lineasVenta2.isEmpty()) {

			try {
				venta.setLineas(lineasVenta2);

				// Agarrar el usuario logueado
				Usuario usr = this.instanciaSistema.obtenerUsuarioLogueado();
				
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
				long ventaId = this.instanciaSistema
						.registrarNuevaVenta(venta);

				// la venta ya esta guardada en el sistema y ahora se factura:
				this.instanciaSistema.facturarVentaPendiente(ventaId);

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Venta facturada con éxito", ""));

			} catch (Excepciones e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								e.getMessage(), ""));
			}catch (Exception ex) {
				ex.printStackTrace();
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
									"Debe ingresar al menos un artículo para facturar la venta",
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
				+ (articuloSeleccionado.getPrecioVenta()
						.subtract((articuloSeleccionado.getPrecioVenta()
								.multiply(articuloSeleccionado.getDescuento()))
								.divide(new BigDecimal(100)))).toString());
		e.setLinea(lineasVenta2.size() + 1);
		e.setDescuentoReceta(articuloSeleccionado.getDescuentoReceta());
		e.setPrecio(articuloSeleccionado.getPrecioVenta());
		e.setCantidad(articuloSeleccionado.getCantidad());
		e.setDescuento(articuloSeleccionado.getDescuento());
		e.setRecetaBlanca(articuloSeleccionado.isRecetaBlanca());
		e.setRecetaNaranja(articuloSeleccionado.isRecetaNaranja());
		e.setRecetaVerde(articuloSeleccionado.isRecetaVerde());
		e.setProductoId(articuloSeleccionado.getProductId());
		// TODO ver el tema de descripcion oferta:
		// e.setDescripcionOferta("Falta ver esto");
		e.setDescuentoPrecio(articuloSeleccionado.getDescuentoPrecio());
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

				if (lv.getProductoId() == e.getProductoId() // mismo articulo
						&& lv.getDescuento().compareTo(
								articuloSeleccionado.getDescuento()) == 0 // mismo
																			// descuento
						&& lv.isRecetaBlanca() == e.isRecetaBlanca()) {
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

		lineasVenta.remove(articuloSeleccionado);

		calcularTotales();
	}

	public void calcularTotales() {
		total = new BigDecimal(0);
		subtotal = new BigDecimal(0);
		iva10 = new BigDecimal(0);
		iva22 = new BigDecimal(0);
		descuento = new BigDecimal(0);
		montoNetoGravadoIvaMinimo = new BigDecimal(0);
		totalIvaMinimo = new BigDecimal(0);
		montoNetoGravadoIvaBasico = new BigDecimal(0);
		montoTributoIvaBasico = new BigDecimal(0);
		totalIvaBasico = new BigDecimal(0);

		for (LineaVenta v : lineasVenta2) {

			if ((v.getDescuento().compareTo(new BigDecimal(101)) == -1)
					&& (v.getDescuento().compareTo(new BigDecimal(-1)) == 1)) {

				BigDecimal subLinea = new BigDecimal(0);

				subLinea = v.getPrecio().multiply(
						new BigDecimal(v.getCantidad()));

				BigDecimal descReceta = new BigDecimal(0);
				if (v.isRecetaBlanca() && !v.getDescuentoReceta().isEmpty()) {
					descReceta = new BigDecimal(v.getDescuentoReceta());
				}

				if (descReceta.compareTo(v.getDescuento()) >= 0) {
					subLinea = subLinea.multiply((new BigDecimal(100)
							.subtract(descReceta)).divide(new BigDecimal(100)));
				} else {
					subLinea = subLinea.multiply((new BigDecimal(100)
							.subtract(v.getDescuento())).divide(new BigDecimal(
							100)));
				}

				subtotal = subtotal.add(subLinea);

				if (v.getIva().equals(new BigDecimal(22))) {
					iva22 = iva22.add(subLinea.multiply(new BigDecimal(1.22)));
					montoNetoGravadoIvaBasico = montoNetoGravadoIvaBasico
							.add(subLinea);
					// montoTributoIvaBasico = montoTributoIvaBasico.add(iva);
					totalIvaBasico = totalIvaBasico.add(subLinea
							.multiply(new BigDecimal(1.22)));
				} else if (v.getIva().equals(new BigDecimal(10))) {
					iva10 = iva10.add(subLinea.multiply(new BigDecimal(1.1)));
					montoNetoGravadoIvaMinimo = montoNetoGravadoIvaMinimo
							.add(subLinea);
					totalIvaMinimo = totalIvaMinimo.add(subLinea
							.multiply(new BigDecimal(1.1)));
				}

				descuento = descuento.add(v.getPrecio().multiply(
						new BigDecimal(v.getCantidad())).subtract(subLinea));

			} else {

				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"El descuento ingresado debe ser un numero entre 0 y 100",
										""));
			}
		}
		total = subtotal.add(iva10).add(iva22);
	}

	public void cancelarVenta() {

		lineasVenta2 = new ArrayList<LineaVenta>();
		lineasVenta = new ArrayList<DTVenta>();
		venta = new Venta();
		total = new BigDecimal(0);
		subtotal = new BigDecimal(0);
		iva10 = new BigDecimal(0);
		iva22 = new BigDecimal(0);
		descuento = new BigDecimal(0);
		montoNetoGravadoIvaMinimo = new BigDecimal(0);
		totalIvaMinimo = new BigDecimal(0);
		montoNetoGravadoIvaBasico = new BigDecimal(0);
		montoTributoIvaBasico = new BigDecimal(0);
		totalIvaBasico = new BigDecimal(0);
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

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getIva10() {
		return iva10;
	}

	public void setIva10(BigDecimal iva) {
		this.iva10 = iva;
	}

	public BigDecimal getIva22() {
		return iva22;
	}

	public void setIva22(BigDecimal iva) {
		this.iva22 = iva;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

}
