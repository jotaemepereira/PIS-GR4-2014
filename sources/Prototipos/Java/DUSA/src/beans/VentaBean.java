package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import model.Enumerados;
import model.LineaVenta;
import model.Usuario;
import model.Venta;
import controladores.Excepciones;
import datatypes.DTProducto;

@ManagedBean
@ViewScoped
public class VentaBean implements Serializable {

	private ISistema instanciaSistema;

	private static final long serialVersionUID = 1L;
	private String descripcionBusqueda;
	private String codigoBusqueda;

	private Venta venta = new Venta();
	private List<DTProducto> resultadoBusqueda = new ArrayList<DTProducto>();
	private List<DTProducto> lineasVenta = new ArrayList<DTProducto>();
	private DTProducto articuloSeleccionado = new DTProducto();

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
	private BigDecimal montoNoGravado = new BigDecimal(0);

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
								.getInitParameter("MODO_FACTURACION")) == Enumerados.modoFacturacion.basica)));

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Excepciones.MENSAJE_ERROR_SISTEMA, ""));
			}
		}
	}

	public void buscarArticulos() {

		// Busqueda con solr
		resultadoBusqueda = new ArrayList<DTProducto>();
		try {
			resultadoBusqueda = this.instanciaSistema
					.buscarArticulosVenta(descripcionBusqueda);

			for (DTProducto dtVenta : resultadoBusqueda) {
				if (dtVenta.getPrecioReceta() == null
						|| dtVenta.getPrecioReceta().equals(new BigDecimal(0))) {
					dtVenta.setRecetaBlanca(false);
				} else {
					dtVenta.setRecetaBlanca(true);
				}
			}

		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
		descripcionBusqueda = "";	
		codigoBusqueda = "";
	}

	public void buscarArticuloLector() {
		// busca articulo cpor el codigo ingresado por el lector de codigo de
		// barras y lo agrega a la venta.

		if (codigoBusqueda.equals("")) {
			return;
		}

		List<DTProducto> lv = new ArrayList<DTProducto>();
		try {
			lv = this.instanciaSistema.buscarArticulosVenta(codigoBusqueda);

			if (lv.size() > 0) {
				DTProducto dtVenta = (DTProducto) lv.get(0);
				articuloSeleccionado = dtVenta;
				RequestContext.getCurrentInstance().execute("PF('ventaDialog').show();");
			}
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		} catch(Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
		
		codigoBusqueda = "";
		descripcionBusqueda = "";
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

		for (DTProducto v : lineasVenta) {

			if ((v.getDescuento().compareTo(new BigDecimal(101)) == -1)
					&& (v.getDescuento().compareTo(new BigDecimal(-1)) == 1)) {

				BigDecimal subLinea = new BigDecimal(0);

				subLinea = v.getPrecioVenta().multiply(
						new BigDecimal(v.getCantidad()));

				if (v.isRecetaBlanca()) {
					subLinea = subLinea.multiply(
							(new BigDecimal(100)).subtract(v
									.getDescuentoReceta())).divide(
							new BigDecimal(100));
				}

				subLinea = subLinea.multiply((new BigDecimal(100)).subtract(
						v.getDescuento()).divide(new BigDecimal(100)));

				subtotal = subtotal.add(subLinea);

				if (v.getIva().compareTo(new BigDecimal(22)) == 0) {
					iva22 = iva22.add(subLinea.multiply(new BigDecimal(0.22)));
					montoNetoGravadoIvaBasico = montoNetoGravadoIvaBasico
							.add(subLinea);
					totalIvaBasico = totalIvaBasico.add(subLinea
							.multiply(new BigDecimal(1.22)));
				} else if (v.getIva().compareTo(new BigDecimal(10)) == 0) {
					iva10 = iva10.add(subLinea.multiply(new BigDecimal(0.1)));
					montoNetoGravadoIvaMinimo = montoNetoGravadoIvaMinimo
							.add(subLinea);
					totalIvaMinimo = totalIvaMinimo.add(subLinea
							.multiply(new BigDecimal(1.1)));
				} else {
					montoNoGravado = montoNoGravado.add(subLinea);
				}

				descuento = descuento.add(v.getPrecioVenta()
						.multiply(new BigDecimal(v.getCantidad()))
						.subtract(subLinea));

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
		}
		total = subtotal.add(iva10).add(iva22);
	}

	public void agregarLineaVenta() {

		if (articuloSeleccionado.getCantidad() == 0) {
			articuloSeleccionado.setCantidad(1);
		}

		if (lineasVenta == null) {
			lineasVenta = new ArrayList<DTProducto>();
		}

		boolean encontre = false;
		for (DTProducto lv : lineasVenta) {
			if ((lv.getProductId() == articuloSeleccionado.getProductId())
					&& (lv.getDescuento().compareTo(
							articuloSeleccionado.getDescuento()) == 0)
					&& (lv.isRecetaBlanca() == articuloSeleccionado
							.isRecetaBlanca())) {
				lv.setCantidad(lv.getCantidad()
						+ articuloSeleccionado.getCantidad());
				encontre = true;
			}
		}

		if (!encontre) {
			lineasVenta.add(articuloSeleccionado);
		}

		resultadoBusqueda = new ArrayList<DTProducto>();
		calcularTotales();
	}

	public void eliminarLineaVenta(DTProducto lv) {
		lineasVenta.remove(lv);
		calcularTotales();
	}

	public void cancelarVenta() {
		resultadoBusqueda = new ArrayList<DTProducto>();
		lineasVenta = new ArrayList<DTProducto>();
		venta = new Venta();

	}

	public void facturarVenta() {
		if (lineasVenta.isEmpty()) {

			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Debe ingresar al menos un artículo para enviar a facturar",
									""));
		} else {

			try {

				prepararVenta();

				venta.setEstadoVenta(String
						.valueOf(Enumerados.EstadoVenta.PENDIENTE));

				
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
			nuevaVenta();

		}
	}
	
	private void prepararVenta(){
		List<LineaVenta> lineas = convertirProductoALinea(lineasVenta);
		venta.setLineas(lineas);

		// Agarrar el usuario logueado
		Usuario usr = new Usuario();
		usr = this.instanciaSistema.obtenerUsuarioLogueado();
		venta.setUsuario(usr);
		// TODO ver como se elige la forma de pago.
		venta.setFormaDePago(Enumerados.TipoFormaDePago.CONTADO
				.toString());
		venta.setCantidadLineas(lineas.size());

		venta.setMontoNetoGravadoIvaMinimo(montoNetoGravadoIvaMinimo);
		venta.setTotalIvaMinimo(totalIvaMinimo);
		venta.setMontoNetoGravadoIvaBasico(montoNetoGravadoIvaBasico);
		venta.setMontoTributoIvaBasico(montoTributoIvaBasico);
		venta.setTotalIvaBasico(totalIvaBasico);
		venta.setMontoNoGravado(montoNoGravado);

		venta.setMontoTotal(total);
		venta.setMontoTotalAPagar(total.setScale(0,
				BigDecimal.ROUND_HALF_UP));
	}

	private List<LineaVenta> convertirProductoALinea(List<DTProducto> param) {
		List<LineaVenta> lineas = new ArrayList<LineaVenta>();
		LineaVenta lv = null;
		int i = 1;
		for (DTProducto p : param) {
			lv = new LineaVenta();

			lv.setCantidad(p.getCantidad());

			lv.setDescuento(new BigDecimal(0));
			if (p.isRecetaBlanca()) {
				lv.setDescuento(p.getPrecioVenta().multiply(p.getDescuentoReceta()).divide(new BigDecimal(100)));
			}
			lv.setDescuento(lv.getDescuento().add(p.getPrecioVenta()
					.multiply(p.getDescuento())
					.divide(new BigDecimal(100))));

			lv.setLinea(i);
			lv.setPrecio(p.getPrecioVenta().subtract(lv.getDescuento()));
			lv.setProductoId(p.getProductId());
			lv.setRecetaBlanca(p.isRecetaBlanca());
			lv.setRecetaVerde(p.isRecetaVerde());
			lv.setRecetaNaranja(p.isRecetaNaranja());
			lv.setDescripcionOferta("");
			lineas.add(lv);
			i++;
		}

		return lineas;
	}

	public void facturarVentaDirecto() {

		if (lineasVenta.isEmpty()) {

			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Debe ingresar al menos un artículo para facturar la venta.",
									""));
		} else {

			try {


				prepararVenta();
				venta.setEstadoVenta(String
						.valueOf(Enumerados.EstadoVenta.PENDIENTE));

				long ventaId = this.instanciaSistema.registrarNuevaVenta(venta);

				// la venta ya esta guardada en el sistema y ahora se factura:
				this.instanciaSistema.facturarVentaPendiente(ventaId);

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Venta facturada con éxito", ""));

			} catch (Excepciones e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e
								.getMessage(), ""));
			} catch (Exception ex) {
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Excepciones.MENSAJE_ERROR_SISTEMA, ""));
			}

			nuevaVenta();

		}
	}

	public void agregarVentaPerdida() {

		if (lineasVenta.isEmpty()) {

			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Debe ingresar al menos un artículo para ingresar la venta perdida.",
									""));
		} else {

			try {

				prepararVenta();
				venta.setEstadoVenta(String
						.valueOf(Enumerados.EstadoVenta.PERDIDA));

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

			nuevaVenta();
		}
	}

	private void nuevaVenta() {
		resultadoBusqueda = new ArrayList<DTProducto>();
		lineasVenta = new ArrayList<DTProducto>();

		subtotal = new BigDecimal(0);
		iva10 = new BigDecimal(0);
		iva22 = new BigDecimal(0);
		total = new BigDecimal(0);
		descuento = new BigDecimal(0);
		montoNetoGravadoIvaMinimo = new BigDecimal(0);
		totalIvaMinimo = new BigDecimal(0);
		montoNetoGravadoIvaBasico = new BigDecimal(0);
		montoTributoIvaBasico = new BigDecimal(0);
		totalIvaBasico = new BigDecimal(0);
		montoNoGravado = new BigDecimal(0);
	}

	public String descuentoVenta(DTProducto v) {
		String descuento = "";

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(0);

		if (v.isRecetaBlanca()) {
			descuento = df.format(v.getDescuentoReceta()) + "%";
		}

		if (v.getDescuento().compareTo(new BigDecimal(0)) > 0) {
			descuento = descuento + " " + df.format(v.getDescuento()) + "%";
		}

		if (descuento.isEmpty()) {
			descuento = "0";
		}

		return descuento;
	}

	public List<DTProducto> getLineasVenta() {
		return lineasVenta;
	}

	public void setLineasVenta(List<DTProducto> lineasVenta) {
		this.lineasVenta = lineasVenta;
	}

	public String getDescripcionBusqueda() {
		return descripcionBusqueda;
	}

	public void setDescripcionBusqueda(String descripcionBusqueda) {
		this.descripcionBusqueda = descripcionBusqueda;
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

	public DTProducto getArticuloSeleccionado() {
		return articuloSeleccionado;
	}

	public void setArticuloSeleccionado(DTProducto articuloSeleccionado) {
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

	public void setIva10(BigDecimal iva10) {
		this.iva10 = iva10;
	}

	public BigDecimal getIva22() {
		return iva22;
	}

	public void setIva22(BigDecimal iva22) {
		this.iva22 = iva22;
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

	public BigDecimal getMontoNetoGravadoIvaMinimo() {
		return montoNetoGravadoIvaMinimo;
	}

	public void setMontoNetoGravadoIvaMinimo(
			BigDecimal montoNetoGravadoIvaMinimo) {
		this.montoNetoGravadoIvaMinimo = montoNetoGravadoIvaMinimo;
	}

	public BigDecimal getTotalIvaMinimo() {
		return totalIvaMinimo;
	}

	public void setTotalIvaMinimo(BigDecimal totalIvaMinimo) {
		this.totalIvaMinimo = totalIvaMinimo;
	}

	public BigDecimal getMontoNetoGravadoIvaBasico() {
		return montoNetoGravadoIvaBasico;
	}

	public void setMontoNetoGravadoIvaBasico(
			BigDecimal montoNetoGravadoIvaBasico) {
		this.montoNetoGravadoIvaBasico = montoNetoGravadoIvaBasico;
	}

	public BigDecimal getMontoTributoIvaBasico() {
		return montoTributoIvaBasico;
	}

	public void setMontoTributoIvaBasico(BigDecimal montoTributoIvaBasico) {
		this.montoTributoIvaBasico = montoTributoIvaBasico;
	}

	public BigDecimal getTotalIvaBasico() {
		return totalIvaBasico;
	}

	public void setTotalIvaBasico(BigDecimal totalIvaBasico) {
		this.totalIvaBasico = totalIvaBasico;
	}

	public List<DTProducto> getResultadoBusqueda() {
		return resultadoBusqueda;
	}

	public void setResultadoBusqueda(List<DTProducto> resultadoBusqueda) {
		this.resultadoBusqueda = resultadoBusqueda;
	}

	public String getStrSubtotal() {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);

		return df.format(subtotal);
	}

	public String getStrIva10() {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);

		return df.format(iva10);
	}

	public String getStrIva22() {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);

		return df.format(iva22);
	}

	public String getStrDescuento() {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);

		return df.format(descuento);
	}

	public String getStrTotal() {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);

		return df.format(total);
	}
}
