/**
 * Clase que implementa el control de la presentación del módulo compras
 * 
 */
package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import model.Orden;
import model.OrdenDetalle;
import controladores.Excepciones;
import datatypes.DTBusquedaArticulo;
import datatypes.DTComprobanteFactura;
import datatypes.DTLineaFacturaCompra;
import datatypes.DTProveedor;
import datatypes.DTTiposDGI;

@ManagedBean
@SessionScoped
public class ComprasBean implements Serializable {

	private ISistema instanciaSistema;

	private static final long serialVersionUID = 1L;

	private Boolean disableBotones = false;
	private Boolean facturaAutomatica = false;
	private Boolean serieFactura = false;
	private String hideTable = "hidden";
	private String selectFacturaDUSA = "hidden";
	private String selectProveedores = "hidden";

	private List<DTProveedor> proveedores;
	private int proveedorSeleccionado;

	private List<DTBusquedaArticulo> busquedaArticulos;
	private String busqueda;

	private Map<Long, DTComprobanteFactura> mapFacturasDUSA = new HashMap<Long, DTComprobanteFactura>();
	private List<DTComprobanteFactura> facturasDUSA = new ArrayList<DTComprobanteFactura>();
	private long ordenDeCompraDUSA;
	private DTComprobanteFactura factura = new DTComprobanteFactura();

	private Map<Integer, DTTiposDGI> mapTiposDGI = new HashMap<Integer, DTTiposDGI>();
	private List<DTTiposDGI> tiposDGI = new ArrayList<DTTiposDGI>();

	List<DTLineaFacturaCompra> alertasPrecios = new ArrayList<DTLineaFacturaCompra>();
	
	private Date hoy = new Date();

	// getters y setters
	
	public Date getHoy(){
		return hoy;
	}
	
	public Boolean getDisableBotones() {
		return disableBotones;
	}

	public void setDisableBotones(Boolean disableBotones) {
		this.disableBotones = disableBotones;
	}

	public String getHideTable() {
		return hideTable;
	}

	public void setHideTable(String hideTable) {
		this.hideTable = hideTable;
	}

	public List<DTProveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<DTProveedor> proveedores) {
		this.proveedores = proveedores;
	}

	public List<DTBusquedaArticulo> getBusquedaArticulos() {
		return busquedaArticulos;
	}

	public void setBusquedaArticulos(List<DTBusquedaArticulo> busquedaArticulos) {
		this.busquedaArticulos = busquedaArticulos;
	}

	public List<DTComprobanteFactura> getFacturasDUSA() {
		return facturasDUSA;
	}

	public void setFacturasDUSA(List<DTComprobanteFactura> facturasDUSA) {
		this.facturasDUSA = facturasDUSA;
	}

	public DTComprobanteFactura getFactura() {
		return factura;
	}

	public void setFactura(DTComprobanteFactura factura) {
		this.factura = factura;
	}

	public long getOrdenDeCompraDUSA() {
		return ordenDeCompraDUSA;
	}

	public void setOrdenDeCompraDUSA(long ordenDeCompraDUSA) {
		this.ordenDeCompraDUSA = ordenDeCompraDUSA;
	}

	public String getSelectFacturaDUSA() {
		return selectFacturaDUSA;
	}

	public void setSelectFacturaDUSA(String selectFacturaDUSA) {
		this.selectFacturaDUSA = selectFacturaDUSA;
	}

	public String getSelectProveedores() {
		return selectProveedores;
	}

	public void setSelectProveedores(String selectProveedores) {
		this.selectProveedores = selectProveedores;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public void setISistema(ISistema s) {
		this.instanciaSistema = s;

		if (this.instanciaSistema != null) {
			obtenerTiposDGI();
		}
	}

	public List<DTTiposDGI> getTiposDGI() {
		return tiposDGI;
	}

	public void setTiposDGI(List<DTTiposDGI> tiposDGI) {
		this.tiposDGI = tiposDGI;
	}

	public int getProveedorSeleccionado() {
		return proveedorSeleccionado;
	}

	public void setProveedorSeleccionado(int proveedorSeleccionado) {
		this.proveedorSeleccionado = proveedorSeleccionado;
	}

	public Boolean getFacturaAutomatica() {
		return facturaAutomatica;
	}

	public void setFacturaAutomatica(Boolean facturaAutomatica) {
		this.facturaAutomatica = facturaAutomatica;
	}

	public Boolean getSerieFactura() {
		return serieFactura;
	}

	public void setSerieFactura(Boolean serieFactura) {
		this.serieFactura = serieFactura;
	}

	public List<DTLineaFacturaCompra> getAlertasPrecios() {
		return alertasPrecios;
	}

	public void setSerieFactura(List<DTLineaFacturaCompra> alertasPrecios) {
		this.alertasPrecios = alertasPrecios;
	}

	// funciones ingresar compra
	public void ingresoManual() {
		actualizarProveedores();

		facturaAutomatica = false;
		disableBotones = true;
		hideTable = "visible";
		selectFacturaDUSA = "hidden";
		selectProveedores = "visible";
		serieFactura = false;
	}

	public void facturaAutomaticaDUSA() {
		try {
			proveedorSeleccionado = 1;
			mapFacturasDUSA = this.instanciaSistema.obtenerFacturasDUSA();
			this.facturasDUSA = new ArrayList<DTComprobanteFactura>(
					mapFacturasDUSA.values());
		} catch (Excepciones e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			return;
		}

		facturaAutomatica = true;
		disableBotones = true;
		hideTable = "visible";
		selectFacturaDUSA = "visible";
		selectProveedores = "hidden";
		serieFactura = true;
	}

	public void seleccionFacturaDUSA() {
		factura = mapFacturasDUSA.get(ordenDeCompraDUSA);
		proveedorSeleccionado = 1;
	}

	public void cancelarIngresarCompra() {
		disableBotones = false;
		hideTable = "hidden";
		selectFacturaDUSA = "hidden";
		selectProveedores = "hidden";
		factura = new DTComprobanteFactura();
		serieFactura = false;
	}

	public void ingresarCompra() {
		FacesContext context = FacesContext.getCurrentInstance();
		alertasPrecios.clear();
		
		if((serieFactura == false) && (factura.getSerieCFE().equals(""))){
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "En el caso de los comprobantes electrónicos es necesario ingresar la serie.", ""));
			return;
		}

		List<OrdenDetalle> detalles = new ArrayList<OrdenDetalle>();
		Orden orden = new Orden();

		// Genero todos los detalles de las órdenes
		Iterator<DTLineaFacturaCompra> it = factura.getDetalle().iterator();
		int numeroLinea = 0;

		while (it.hasNext()) {
			DTLineaFacturaCompra linea = (DTLineaFacturaCompra) it.next();
			OrdenDetalle detalle = new OrdenDetalle();
			numeroLinea++;

			System.out.println(linea.getCostoListaArticulo().compareTo(
					linea.getPrecioUnitario()));
			// Chequeo si el costo de lista es menor al costo comprado y si lo
			// es, lo agrego a la lista de alerta
			if (linea.getCostoListaArticulo().compareTo(
					linea.getPrecioUnitario()) < 0) {
				alertasPrecios.add(linea);
			}

			try {
				detalle.setCantidad(linea.getCantidad());
				detalle.setDescripcionOferta(linea.getDescripcionOferta());
				detalle.setDescuento(linea.getDescuento());
				detalle.setIndicadorDeFacturacion(linea
						.getIndicadorDeFacturacion());
				detalle.setNumeroArticulo(linea.getNumeroArticulo());
				if (factura.getOrdenDeCompra() != 0) { // en el caso de factura
														// de DUSA
					detalle.setNumeroLinea(linea.getNumeroLinea());
				} else { // caso de factura manual
					detalle.setNumeroLinea(numeroLinea);
				}
				detalle.setPrecioUnitario(linea.getPrecioUnitario());
				detalle.setProductId(linea.getProductId());

				detalles.add(detalle);
			} catch (Excepciones e) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
				return;
			}
		}

		// Guardo los datos de la factura
		try {
			orden.setCantidadLineas(numeroLinea);
			orden.setDetalle(detalles);
			orden.setFechaComprobante(factura.getFechaComprobante());
			orden.setFormaDePago(factura.getFormaDePago());
			if(factura.getOrdenDeCompra() == 0){
				orden.setIdProveedor(factura.getIdProveedor());
			}else{
				orden.setIdOrden(factura.getIdOrden());
				orden.setIdProveedor(1);
			}
			orden.setMontoNetoGravadoIvaBasico(factura
					.getMontoNetoGravadoIvaBasico());
			orden.setMontoNetoGravadoIvaMinimo(factura
					.getMontoNetoGravadoIvaMinimo());
			orden.setMontoNoFacturable(factura.getMontoNoFacturable());
			orden.setMontoNoGravado(factura.getMontoNoGravado());
			orden.setMontoRetenidoIRAE(factura.getMontoRetenidoIRAE());
			orden.setMontoRetenidoIVA(factura.getMontoRetenidoIVA());
			orden.setMontoTotalAPagar(factura.getMontoTotalAPagar());
			orden.setMontoTributoIvaBasico(factura.getMontoTributoIvaBasico());
			orden.setMontoTributoIvaMinimo(factura.getMontoTributoIvaMinimo());
			orden.setNumeroCFE(factura.getNumeroCFE());
			orden.setTipoCFE(factura.getTipoCFE());
			orden.setTotalIvaBasico(factura.getTotalIvaBasico());
			orden.setTotalIvaMinimo(factura.getTotalIvaMinimo());
			orden.setProcesada(true);
			orden.setOrdenDeCompra(factura.getOrdenDeCompra());
		} catch (Excepciones e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			return;
		}

		try {
			this.instanciaSistema.ingresarFacturaCompra(orden);
		} catch (Excepciones e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			return;
		}

		// Reseteo los valores por defecto
		disableBotones = false;
		hideTable = "hidden";
		selectFacturaDUSA = "hidden";
		selectProveedores = "hidden";
		factura = new DTComprobanteFactura();
		serieFactura = false;

		if (!alertasPrecios.isEmpty()) {
			System.out.println("alerta");
			RequestContext.getCurrentInstance().execute(
					"PF('dialogAlerta').show()");
		}
	}

	public void actualizarProveedores() {
		Map<Integer, DTProveedor> proveedoresLista;
		try {
			proveedoresLista = this.instanciaSistema.obtenerProveedores();
			this.proveedores = new ArrayList<DTProveedor>(
					proveedoresLista.values());
		} catch (Excepciones e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			e.printStackTrace();
		}
	}

	public void obtenerTiposDGI() {
		try {
			this.mapTiposDGI = this.instanciaSistema.obtenerTiposDGI();
			this.tiposDGI = new ArrayList<DTTiposDGI>(mapTiposDGI.values());
		} catch (Excepciones e) {
			e.printStackTrace();
		}
	}

	public void agregarArticulo(DTBusquedaArticulo articulo) {
		DTLineaFacturaCompra linea = new DTLineaFacturaCompra();

		linea.setDescripcion(articulo.getDescripcion());
		linea.setNumeroArticulo(articulo.getNumeroProducto_proveedor());
		linea.setProductId(articulo.getIdArticulo());
		linea.setCantidad(1);
		linea.setCostoListaArticulo(articulo.getCostoDeLista());
		linea.setDescripcionOferta("");
		linea.setDescuento(new BigDecimal(0));
		linea.setTotal(new BigDecimal(0));
		linea.setPrecioUnitario(new BigDecimal(0));

		List<DTLineaFacturaCompra> detalle = factura.getDetalle();
		detalle.add(linea);

	}

	public void buscarArticulos() {
		busquedaArticulos = new ArrayList<DTBusquedaArticulo>();

		if (busqueda.equals("")) {
			return;
		}

		try {
			System.out.println("PROVEEDOR: " + factura.getIdProveedor());
			if (factura.getIdProveedor() != 0) {
				busquedaArticulos = this.instanciaSistema.buscarArticulos(
						busqueda, this.proveedorSeleccionado);
			} else {
				busquedaArticulos = this.instanciaSistema
						.buscarArticulos(busqueda);
			}
			System.out.println("CANTIDAD ENCONTRADA: "
					+ busquedaArticulos.size());
		} catch (Excepciones e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			e.printStackTrace();
		}
	}

	/**
	 * frente a un cambio en el precio, cantidad o descuento, calcula el total
	 * de ese articulo
	 */
	public void calcularTotalArticulo(DTLineaFacturaCompra detalle) {
		// Saco el valor anterior de la suma del subtotal
		factura.setSubtotalProdctos(factura.getSubtotalProdctos().subtract(
				detalle.getTotal()));

		// Calculo el total del producto modificado
		BigDecimal precio = detalle.getPrecioUnitario();
		BigDecimal descuento = detalle.getDescuento()
				.subtract(new BigDecimal(100)).abs()
				.divide(new BigDecimal(100));
		BigDecimal cantidad = new BigDecimal(detalle.getCantidad());

		BigDecimal total = precio.multiply(cantidad).multiply(descuento);
		detalle.setTotal(total);

		// Agrego el precio calculado al total
		factura.setSubtotalProdctos(factura.getSubtotalProdctos().add(total));
	}

	/**
	 * frente a un cambio en los montos de iva o retenciones, recalcula el total
	 * de cada iva y el total
	 */
	public void actualizarDatos() {

		factura.setTotalIvaBasico(factura.getMontoNetoGravadoIvaBasico().add(
				factura.getMontoTributoIvaBasico()));
		factura.setTotalIvaMinimo(factura.getMontoNetoGravadoIvaMinimo().add(
				factura.getMontoTributoIvaMinimo()));

		factura.setMontoTotalAPagar(factura.getTotalIvaBasico()
				.add(factura.getTotalIvaMinimo())
				.add(factura.getMontoRetenidoIRAE())
				.add(factura.getMontoRetenidoIVA())
				.add(factura.getMontoNoGravado())
				.add(factura.getMontoNoFacturable()));

	}

	public void saveProveedor() {

		this.proveedorSeleccionado = factura.getIdProveedor();

	}

	public void cambioTipoCFE() {
		if ((factura.getTipoCFE() == 1) || (factura.getTipoCFE() == 2)) {
			serieFactura = true;
			factura.setSerieCFE("");
		} else {
			serieFactura = (false || facturaAutomatica);
		}
	}

	public String getNombreTipoFactura(int tipo) {
		return mapTiposDGI.get(tipo).getDescripcion();
	}
}
