package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.math.*;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.primefaces.event.FlowEvent;

import controladores.Excepciones;
import controladores.FabricaLogica;
import controladores.FabricaSistema;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Enumerados.tipoMovimientoDeStock;
import model.TipoIva;
import model.Usuario;
import model.Enumerados.TipoFormaDePago;
import model.LineaPedido;
import model.Pedido;
import model.Presentacion;
import datatypes.DTBusquedaArticuloSolr;
import datatypes.DTBusquedaArticulo;
import datatypes.DTFormasVenta;
import datatypes.DTLineaPedido;
import datatypes.DTProveedor;
import datatypes.DTTipoArticulo;

@ManagedBean
@ViewScoped
public class StockBean implements Serializable {

	private ISistema instanciaSistema;

	private static final long serialVersionUID = 1L;
	private Articulo articulo = new Articulo();
	private boolean noEsMedicamento;
	private int tipoIvaSeleccionado;

	// Proveedores
	private int proveedor;
	private long codigoIdentificador;
	private Map<Integer, DTProveedor> proveedores;
	private List<DTProveedor> listaProveedores;
	private List<DTProveedor> listaMarcas;
	private List<DTProveedor> proveedoresSeleccionados = new ArrayList<DTProveedor>();

	// Presentaciones
	private Presentacion presentacion = new Presentacion();
	private List<Presentacion> presentaciones = new ArrayList<Presentacion>();

	// Drogas
	private long[] drogasSeleccionadas;
	private List<Droga> listaDrogas;

	// Acciones Terapeuticas
	private long[] accionesTerSeleccionadas;
	private List<AccionTer> listaAccionesTer;

	private List<DTFormasVenta> formasVenta = new ArrayList<DTFormasVenta>();
	private List<DTTipoArticulo> tiposArticulo = new ArrayList<DTTipoArticulo>();
	private List<TipoIva> tiposIVA;
	private List<DTLineaPedido> pedidos = new ArrayList<DTLineaPedido>();
	private String message;
	private String messageClass;
	private Boolean disableDesdeUltimoPedido = false;
	private Boolean disablePrediccionDePedido = false;
	private String hideElement = "hidden";
	private String formaDePago = "contado";

	// Busqueda de artículos
	private String busqueda = "";
	private List<DTBusquedaArticulo> resBusqueda = new ArrayList<DTBusquedaArticulo>();

	//
	private DTBusquedaArticulo articuloSeleccionado;
	private int tipoMotivo;
	private String motivo;
	private String busquedaDesarme = "";
	private List<DTBusquedaArticulo> resBusquedaDesarme = new ArrayList<DTBusquedaArticulo>();
	private DTBusquedaArticulo articuloParaDesarme;
	private int nuevoStockSeleccionado;
	private int nuevoStockDesarme;
	private long[] nuevoStock;

	public long[] getNuevoStock() {
		return nuevoStock;
	}

	public void setNuevoStock(long[] nuevoStock) {
		this.nuevoStock = nuevoStock;
	}

	public DTBusquedaArticulo getArticuloSeleccionado() {
		return articuloSeleccionado;
	}

	public void setArticuloSeleccionado(DTBusquedaArticulo articuloSeleccionado) {
		this.articuloSeleccionado = articuloSeleccionado;
	}

	public int getTipoMotivo() {
		return tipoMotivo;
	}

	public void setTipoMotivo(int tipoMotivo) {
		this.tipoMotivo = tipoMotivo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getBusquedaDesarme() {
		return busquedaDesarme;
	}

	public void setBusquedaDesarme(String busquedaDesarme) {
		this.busquedaDesarme = busquedaDesarme;
	}

	public List<DTBusquedaArticulo> getResBusquedaDesarme() {
		return resBusquedaDesarme;
	}

	public void setResBusquedaDesarme(
			List<DTBusquedaArticulo> resBusquedaDesarme) {
		this.resBusquedaDesarme = resBusquedaDesarme;
	}

	public DTBusquedaArticulo getArticuloParaDesarme() {
		return articuloParaDesarme;
	}

	public void setArticuloParaDesarme(DTBusquedaArticulo articuloParaDesarme) {
		this.articuloParaDesarme = articuloParaDesarme;
	}

	public int getNuevoStockSeleccionado() {
		return nuevoStockSeleccionado;
	}

	public void setNuevoStockSeleccionado(int nuevoStockSeleccionado) {
		this.nuevoStockSeleccionado = nuevoStockSeleccionado;
	}

	public int getNuevoStockDesarme() {
		return nuevoStockDesarme;
	}

	public void setNuevoStockDesarme(int nuevoStockDesarme) {
		this.nuevoStockDesarme = nuevoStockDesarme;
	}

	public List<DTLineaPedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<DTLineaPedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public boolean isNoEsMedicamento() {
		return noEsMedicamento;
	}

	public void setNoEsMedicamento(boolean noEsMedicamento) {
		this.noEsMedicamento = noEsMedicamento;
	}

	public int getTipoIvaSeleccionado() {
		return tipoIvaSeleccionado;
	}

	public void setTipoIvaSeleccionado(int tipoIvaSeleccionado) {
		this.tipoIvaSeleccionado = tipoIvaSeleccionado;
	}

	public int getProveedor() {
		return proveedor;
	}

	public void setProveedor(int proveedor) {
		this.proveedor = proveedor;
	}

	public Map<Integer, DTProveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(Map<Integer, DTProveedor> proveedores) {
		this.proveedores = proveedores;
	}

	public Presentacion getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(Presentacion presentacion) {
		this.presentacion = presentacion;
	}

	public List<Presentacion> getPresentaciones() {
		return presentaciones;
	}

	public void setPresentaciones(List<Presentacion> presentaciones) {
		this.presentaciones = presentaciones;
	}

	public long[] getDrogasSeleccionadas() {
		return drogasSeleccionadas;
	}

	public void setDrogasSeleccionadas(long[] drogasSeleccionadas) {
		this.drogasSeleccionadas = drogasSeleccionadas;
	}

	public List<Droga> getListaDrogas() {
		return listaDrogas;
	}

	public void setListaDrogas(List<Droga> listaDrogas) {
		this.listaDrogas = listaDrogas;
	}

	public long[] getAccionesTerSeleccionadas() {
		return accionesTerSeleccionadas;
	}

	public void setAccionesTerSeleccionadas(long[] accionesTerSeleccionadas) {
		this.accionesTerSeleccionadas = accionesTerSeleccionadas;
	}

	public List<AccionTer> getListaAccionesTer() {
		return listaAccionesTer;
	}

	public void setListaAccionesTer(List<AccionTer> listaAccionesTer) {
		this.listaAccionesTer = listaAccionesTer;
	}

	public List<DTFormasVenta> getFormasVenta() {
		return formasVenta;
	}

	public void setFormasVenta(List<DTFormasVenta> formasVenta) {
		this.formasVenta = formasVenta;
	}

	public List<DTTipoArticulo> getTiposArticulo() {
		return tiposArticulo;
	}

	public void setTiposArticulo(List<DTTipoArticulo> tiposArticulo) {
		this.tiposArticulo = tiposArticulo;
	}

	public List<DTProveedor> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(List<DTProveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}

	public List<DTProveedor> getListaMarcas() {
		return listaMarcas;
	}

	public void setListaMarcas(List<DTProveedor> listaMarcas) {
		this.listaMarcas = listaMarcas;
	}

	public List<DTProveedor> getProveedoresSeleccionados() {
		return proveedoresSeleccionados;
	}

	public void setProveedoresSeleccionados(
			List<DTProveedor> proveedoresSeleccionados) {
		this.proveedoresSeleccionados = proveedoresSeleccionados;
	}

	public List<TipoIva> getTiposIVA() {
		return tiposIVA;
	}

	public void setTiposIVA(List<TipoIva> tiposIVA) {
		this.tiposIVA = tiposIVA;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getCodigoIdentificador() {
		return codigoIdentificador;
	}

	public void setCodigoIdentificador(long codigoIdentificador) {
		this.codigoIdentificador = codigoIdentificador;
	}

	public String getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}

	public Boolean getDisableDesdeUltimoPedido() {
		return disableDesdeUltimoPedido;
	}

	public void setDisableDesdeUltimoPedido(Boolean disableDesdeUltimoPedido) {
		this.disableDesdeUltimoPedido = disableDesdeUltimoPedido;
	}

	public Boolean getDisablePrediccionDePedido() {
		return disablePrediccionDePedido;
	}

	public void setDisablePrediccionDePedido(Boolean disablePrediccionDePedido) {
		this.disablePrediccionDePedido = disablePrediccionDePedido;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageClass() {
		return messageClass;
	}

	public void setMessageClass(String messageClass) {
		this.messageClass = messageClass;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	/**
	 * @return the resBusqueda
	 */
	public List<DTBusquedaArticulo> getResBusqueda() {
		return resBusqueda;
	}

	/**
	 * @param resBusqueda
	 *            the resBusqueda to set
	 */
	public void setResBusqueda(List<DTBusquedaArticulo> resBusqueda) {
		this.resBusqueda = resBusqueda;
	}

	/**
	 * genera el pedido desde el ultimo pedido en el sistema
	 */
	public void desdeUltimoPedido() {
		disablePrediccionDePedido = true;
		disableDesdeUltimoPedido = true;
		hideElement = "visible";
		pedidos.clear();

		try {
//			this.instanciaSistema.actualizarStock();
			pedidos = this.instanciaSistema
					.generarPedidoEnBaseAPedidoAnterior();

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		/*
		 * DTLineaPedido dt = new DTLineaPedido(); dt.setCantidad(5);
		 * dt.setIdArticulo(5); dt.setNumeroArticulo(5); //
		 * dt.setNombreArticulo("Ernex"); dt.setDescripcionArticulo("Ernex");
		 * dt.setPrecioPonderado(40); dt.setPrecioUnitario(40);
		 * dt.setStockMinimo(9); dt.setSubtotal(200); pedidos.add(dt);
		 * 
		 * dt = new DTLineaPedido(); dt.setCantidad(1); dt.setIdArticulo(6);
		 * dt.setNumeroArticulo(6); // dt.setNombreArticulo("Alerfast");
		 * dt.setDescripcionArticulo("Alerfast"); dt.setPrecioPonderado(70);
		 * dt.setPrecioUnitario(70); dt.setStockMinimo(4); dt.setSubtotal(70);
		 * pedidos.add(dt);
		 * 
		 * dt = new DTLineaPedido(); dt.setCantidad(4); dt.setIdArticulo(7);
		 * dt.setNumeroArticulo(7); // dt.setNombreArticulo("Alerfast forte");
		 * dt.setDescripcionArticulo("Alerfast forte");
		 * dt.setPrecioPonderado(90); dt.setPrecioUnitario(90);
		 * dt.setStockMinimo(4); dt.setSubtotal(360); pedidos.add(dt);
		 */

	}

	/**
	 * genera el pedido segun la prediccion en base al pasado
	 */
	public void prediccionDePedido() {
		disablePrediccionDePedido = true;
		disableDesdeUltimoPedido = true;
		hideElement = "visible";
		pedidos.clear();

		try {

			pedidos = this.instanciaSistema.generarPedidoEnBaseAHistorico(5);
		} catch (Exception e) {

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		/*
		 * DTLineaPedido dt = new DTLineaPedido(); dt.setCantidad(3);
		 * dt.setIdArticulo(1); dt.setNumeroArticulo(1); //
		 * dt.setNombreArticulo("Perifar 400");
		 * dt.setDescripcionArticulo("Perifar 400"); dt.setPrecioPonderado(45);
		 * dt.setPrecioUnitario(45); dt.setStockMinimo(7); dt.setSubtotal(135);
		 * pedidos.add(dt);
		 * 
		 * dt = new DTLineaPedido(); dt.setCantidad(2); dt.setIdArticulo(2);
		 * dt.setNumeroArticulo(2); // dt.setNombreArticulo("Aspirina");
		 * dt.setDescripcionArticulo("Aspirina"); dt.setPrecioPonderado(50);
		 * dt.setPrecioUnitario(50); dt.setStockMinimo(9); dt.setSubtotal(100);
		 * pedidos.add(dt);
		 * 
		 * dt = new DTLineaPedido(); dt.setCantidad(3); dt.setIdArticulo(3);
		 * dt.setNumeroArticulo(3); // dt.setNombreArticulo("Buscapina");
		 * dt.setDescripcionArticulo("Buscapina"); dt.setPrecioPonderado(30);
		 * dt.setPrecioUnitario(30); dt.setStockMinimo(7); dt.setSubtotal(90);
		 * pedidos.add(dt);
		 * 
		 * dt = new DTLineaPedido(); dt.setCantidad(6); dt.setIdArticulo(4);
		 * dt.setNumeroArticulo(4); // dt.setNombreArticulo("Biogrip");
		 * dt.setDescripcionArticulo("Biogrip"); dt.setPrecioPonderado(10);
		 * dt.setPrecioUnitario(10); dt.setStockMinimo(10); dt.setSubtotal(60);
		 * pedidos.add(dt);
		 */
	}

	/**
	 * Recalcula el subtotal de la linea pedido
	 * 
	 * @param item
	 *            a recalcular
	 */
	public void nuevoSubtotal(DTLineaPedido item) {

		item.setSubtotal(item.getCantidad()
				* item.getPrecioUnitario().floatValue());
	}

	/**
	 * Elimina la linea pedido de la tabla
	 * 
	 * @param item
	 *            a eliminar
	 */
	public void removeItem(DTLineaPedido item) {
		pedidos.remove(item);
	}

	public void enviarPedido() {
		System.out.println("******* ENVIAR PEDIDO ********");
		FacesContext context = FacesContext.getCurrentInstance();

		if (pedidos.isEmpty()) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					Excepciones.MENSAJE_PEDIDO_VACIO, ""));
			return;
		}

		Pedido p = new Pedido();

		p.setFecha(new Date(Calendar.getInstance().getTimeInMillis()));
		/* Cargo el usuario que realiza el pedido */
		p.setUsuario(this.instanciaSistema.obtenerUsuarioLogueado());

		if (this.formaDePago.equalsIgnoreCase("contado")) {

			p.setFormaDePago(TipoFormaDePago.CONTADO);
		} else {

			p.setFormaDePago(TipoFormaDePago.CREDITO);
		}

		for (DTLineaPedido dtLineaPedido : pedidos) {

			LineaPedido lPedido = new LineaPedido(
					dtLineaPedido.getIdArticulo(),
					dtLineaPedido.getNumeroArticulo(),
					dtLineaPedido.getCantidad());
			p.getLineas().add(lPedido);
		}

		try {

			this.instanciaSistema.realizarPedido(p);

			pedidos.clear();
			disableDesdeUltimoPedido = false;
			disablePrediccionDePedido = false;
			hideElement = "hidden";

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, Excepciones.MENSAJE_OK_PEDIDO,
					""));
		} catch (Excepciones ex) {

			ex.printStackTrace();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public void cancelarPedido() {
		System.out.println("******* CANCELAR PEDIDO ********");
		pedidos.clear();
		disableDesdeUltimoPedido = false;
		disablePrediccionDePedido = false;
		hideElement = "hidden";
	}

	public void agregarProveedor() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (proveedor != 0) {
			if (codigoIdentificador != 0) {
				if (!existeProveedor(proveedor)) {
					try {
						if (!existeCodigoParaProveedor(proveedor,
								codigoIdentificador)) {
							DTProveedor p = new DTProveedor();
							p.setIdProveedor(proveedor);
							p.setNombreComercial(proveedores.get(proveedor)
									.getNombreComercial());
							p.setCodigoIdentificador(codigoIdentificador);
							this.proveedoresSeleccionados.add(p);
							this.proveedor = 0;
							this.codigoIdentificador = 0;
						} else {
							context.addMessage(
									null,
									new FacesMessage(
											FacesMessage.SEVERITY_WARN,
											"Ya existe un artículo con ese código para el proveedor seleccionado.",
											""));
						}
					} catch (Excepciones e) {
						context.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, e
										.getMessage(), ""));
					}
				} else {
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							"Ya seleccionó el proveedor.", ""));
				}
			} else {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Debe ingresar el código que lo identifica.", ""));
			}
		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					"Debe seleccionar un proveedor.", ""));
		}
	}

	private boolean existeCodigoParaProveedor(long idProveedor,
			long codigoIdentificador) throws Excepciones {
		return this.instanciaSistema.existeCodigoParaProveedor(idProveedor,
				codigoIdentificador);
	}

	private boolean existeProveedor(int proveedor) {
		boolean ret = false;
		Iterator<DTProveedor> i = proveedoresSeleccionados.iterator();
		while (i.hasNext() && !ret) {
			ret = proveedor == i.next().getIdProveedor();
		}
		return ret;
	}

	public void altaArticulo() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (!proveedoresSeleccionados.isEmpty()) {
			if ((articulo.getPrecioVenta().compareTo(BigDecimal.ZERO) == 0 && articulo
					.getPorcentajePrecioVenta().compareTo(BigDecimal.ZERO) == 0)
					|| (articulo.getPrecioVenta().compareTo(BigDecimal.ZERO) != 0 && articulo
							.getPorcentajePrecioVenta().compareTo(
									BigDecimal.ZERO) == 0)
					|| (articulo.getPrecioVenta().compareTo(BigDecimal.ZERO) == 0 && articulo
							.getPorcentajePrecioVenta().compareTo(
									BigDecimal.ZERO) != 0)) {
				try {
					/* Cargo los proveedores seleccionados en el articulo */
					Iterator<DTProveedor> i = proveedoresSeleccionados
							.iterator();
					while (i.hasNext()) {
						DTProveedor next = i.next();
						DTProveedor p = new DTProveedor();
						p.setIdProveedor(next.getIdProveedor());
						p.setNombreComercial(proveedores.get(
								next.getIdProveedor()).getNombreComercial());
						p.setCodigoIdentificador(next.getCodigoIdentificador());
						this.articulo.agregarProveedor(p);
					}

					/* Cargo el tipo de iva seleccionado */
					TipoIva ti = new TipoIva();
					ti.setTipoIVA(tipoIvaSeleccionado);
					articulo.setTipoIva(ti);

					/* Cargo el precio de venta según corresponda */
					/*
					 * Si no se carga nada, se asume el mismo que el precio
					 * público
					 */
					if (articulo.getPrecioVenta().compareTo(BigDecimal.ZERO) == 0
							&& articulo.getPorcentajePrecioVenta().compareTo(
									BigDecimal.ZERO) == 0) {
						articulo.setPrecioVenta(articulo.getPrecioUnitario());
						articulo.setPorcentajePrecioVenta(BigDecimal.ONE);
					}
					/* Si se carga precio de venta, se calcula el porcentaje */
					if (articulo.getPrecioVenta().compareTo(BigDecimal.ZERO) != 0
							&& articulo.getPorcentajePrecioVenta().compareTo(
									BigDecimal.ZERO) == 0) {
						BigDecimal porcentaje = articulo.getPrecioVenta()
								.subtract(articulo.getPrecioUnitario());
						porcentaje.multiply(new BigDecimal(100));
						porcentaje.divide(articulo.getPrecioUnitario(), 5,
								RoundingMode.DOWN);
						articulo.setPorcentajePrecioVenta(porcentaje);
					}
					/*
					 * Si se carga un porcentaje sobre el precio público, se
					 * calcula
					 */
					if (articulo.getPrecioVenta().compareTo(BigDecimal.ZERO) == 0
							&& articulo.getPorcentajePrecioVenta().compareTo(
									BigDecimal.ZERO) != 0) {
						articulo.setPrecioVenta(articulo.getPrecioUnitario()
								.multiply(
										articulo.getPorcentajePrecioVenta()
												.add(new BigDecimal(1))));
					}

					/* Cargo el usuario que realiza el alta */
					articulo.setUsuario(this.instanciaSistema
							.obtenerUsuarioLogueado());

					/*
					 * Llamo a la logica para que se de de alta el articulo en
					 * el sistema y en caso de error lo muestro
					 */
					this.instanciaSistema.altaArticulo(articulo);
					// si todo bien aviso y vacio el formulario
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							Excepciones.MENSAJE_OK_ALTA, ""));
					this.articulo = new Articulo();
					this.proveedoresSeleccionados = new ArrayList<DTProveedor>();
					this.proveedor = 0;
					this.codigoIdentificador = 0;
					this.tipoIvaSeleccionado = 0;
				} catch (Excepciones e) {
					if (e.getErrorCode() == Excepciones.ADVERTENCIA_DATOS) {
						context.addMessage(null, new FacesMessage(
								FacesMessage.SEVERITY_WARN, e.getMessage(), ""));
					} else {
						context.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, e
										.getMessage(), ""));
					}
				}
			} else {
				context.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"No puede cargar Precio de venta y Porcentaje de venta al mismo tiempo.",
								""));
			}
		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un proveedor", ""));
		}
	}

	public void cancelarAltaArticulo() {
		refresh();
	}

	public void refresh() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context
				.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse(); // Optional
	}

	public StockBean() {

		this.noEsMedicamento = true;

	}

	public void cargarMarcas() {
		try {
			this.listaMarcas = this.instanciaSistema.obtenerMarcas();
		} catch (Excepciones e) {
			this.message = e.getMessage();
			this.messageClass = "alert alert-danger";
		}
	}

	public void cargarProveedores() {
		try {
			this.proveedores = this.instanciaSistema.obtenerProveedores();
			this.listaProveedores = new ArrayList<DTProveedor>(
					this.proveedores.values());
		} catch (Excepciones e) {
			this.message = e.getMessage();
			this.messageClass = "alert alert-danger";
		}
	}

	public void cargarDrogas() {
		try {
			this.listaDrogas = this.instanciaSistema.obtenerDrogas();
		} catch (Excepciones e) {
			this.message = e.getMessage();
			this.messageClass = "alert alert-danger";
		}
	}

	public void cargarAccionesTerapeuticas() {
		try {
			this.listaAccionesTer = this.instanciaSistema
					.obtenerAccionesTerapeuticas();
		} catch (Excepciones e) {
			this.message = e.getMessage();
			this.messageClass = "alert alert-danger";
		}
	}

	public void cargarTiposArticulo() {
		DTTipoArticulo ta = new DTTipoArticulo();
		ta.setTipoArticulo(model.Enumerados.tipoArticulo.MEDICAMENTO);
		ta.setDescripcion("Medicamento");
		tiposArticulo.add(ta);
		ta = new DTTipoArticulo();
		ta.setTipoArticulo(model.Enumerados.tipoArticulo.PERFUMERIA);
		ta.setDescripcion("Perfumería");
		tiposArticulo.add(ta);
		ta = new DTTipoArticulo();
		ta.setTipoArticulo(model.Enumerados.tipoArticulo.OTROS);
		ta.setDescripcion("Otros");
		tiposArticulo.add(ta);
	}

	public void tipoArticuloChange() {
		if (articulo.getTipoArticulo() == model.Enumerados.tipoArticulo.MEDICAMENTO) {
			this.noEsMedicamento = false;
		} else {
			this.noEsMedicamento = true;
		}
	}

	public void cargarFormasVenta() {
		DTFormasVenta fv = new DTFormasVenta();
		fv.setFormaVenta(model.Enumerados.formasVenta.ventaLibre);
		fv.setDescripcion("Venta libre");
		formasVenta.add(fv);
		fv = new DTFormasVenta();
		fv.setFormaVenta(model.Enumerados.formasVenta.controlado);
		fv.setDescripcion("Controlado");
		formasVenta.add(fv);
		fv = new DTFormasVenta();
		fv.setFormaVenta(model.Enumerados.formasVenta.bajoReceta);
		fv.setDescripcion("Bajo receta");
		formasVenta.add(fv);
		fv = new DTFormasVenta();
		fv.setFormaVenta(model.Enumerados.formasVenta.controlMedico);
		fv.setDescripcion("Control médico");
		formasVenta.add(fv);
	}

	public void cargarTiposIva() {
		try {
			this.tiposIVA = this.instanciaSistema.obtenerTiposIva();
		} catch (Excepciones e) {
			this.message = e.getMessage();
			this.messageClass = "alert alert-danger";
		}
	}

	public void buscarArticulos() {
		resBusqueda = new ArrayList<DTBusquedaArticulo>();

		if (busqueda.equals("")) {
			return;
		}

		try {
			resBusqueda = this.instanciaSistema.buscarArticulos(busqueda);
			if (resBusqueda != null && resBusqueda.size() > 0) {
				nuevoStock = new long[resBusqueda.size()];
				for (int i = 0; i < resBusqueda.size(); i++) {
					nuevoStock[i] = resBusqueda.get(i).getStock();
				}
			}
			System.out.println("CANTIDAD ENCONTRADA: " + resBusqueda.size());
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getHideElement() {
		return hideElement;
	}

	public void setHideElement(String hideElement) {
		this.hideElement = hideElement;
	}

	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}

	public void buscarArticulosDesarme() {
		resBusquedaDesarme = new ArrayList<DTBusquedaArticulo>();

		if (busquedaDesarme.equals("")) {
			return;
		}

		try {
			resBusquedaDesarme = this.instanciaSistema
					.buscarArticulos(busquedaDesarme);
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void confirmarCambioStock() {
		FacesContext contexto = FacesContext.getCurrentInstance();
		try {
			if (motivo.trim().isEmpty()) {
				contexto.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Debe ingresar un motivo.", ""));
			} else {
				int cambios = 0;
				for (int i = 0; i < resBusqueda.size(); i++) {
					if (resBusqueda.get(i).getStock() != nuevoStock[i]) {
						cambios++;
					}
				}

				if (cambios > 0) {

//					long[] ids = new long[cambios];
//					long[] stocks = new long[cambios];
//					int j = 0;

					for (int i = 0; i < resBusqueda.size(); i++) {
						
						long actualStock = resBusqueda.get(i).getStock();
						if (actualStock != nuevoStock[i]) {
//							ids[j] = resBusqueda.get(i).getIdArticulo();
//							stocks[j] = nuevoStock[i];
//							j++;
							
							
							
							//Comparo para generar el registro del movimiento de stock 
							if (actualStock < nuevoStock[i]) {
								
								this.instanciaSistema.modificarStock(resBusqueda.get(i).getIdArticulo(),
										nuevoStock[i], nuevoStock[i]-actualStock, tipoMovimientoDeStock.aumentoStock, motivo);
							} else {
								
								this.instanciaSistema.modificarStock(resBusqueda.get(i).getIdArticulo(),
										nuevoStock[i], actualStock-nuevoStock[i], tipoMovimientoDeStock.bajaStock, motivo);
							}
							
//							is.modificarStock(resBusqueda.get(i).getIdArticulo(), nuevoStock[i]);
						}
					}
					
//					IStock is = FabricaLogica.getIStock();
//					is.modificarStock(ids, stocks);

					contexto.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Cambio de stock realizado con éxito.", ""));

					resBusqueda = new ArrayList<DTBusquedaArticulo>();
					motivo = "";
					busqueda = "";
				} else {
					contexto.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"No hay cambios ingresados.", ""));

				}
			}
		} catch (Excepciones ex){ 
			
			contexto.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							ex.getMessage(),
							""));
		} catch (Exception e) {
			contexto.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al ingresar cambio. Intente nuevamente",
							""));
		}
	}

	public void confirmarDesarme() {
		FacesContext contexto = FacesContext.getCurrentInstance();
		try {
			if (nuevoStockSeleccionado >= articuloSeleccionado.getStock()) {
				contexto.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"El nuevo stock del artículo origen debe ser menor al actual.",
								""));
			} else if (nuevoStockDesarme <= articuloParaDesarme.getStock()) {
				contexto.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"El nuevo stock del artículo destino debe ser mayor al actual.",
								""));
			} else {
//				IStock is = FabricaLogica.getIStock();
//				is.modificarStock(articuloSeleccionado.getIdArticulo(),
//						articuloParaDesarme.getIdArticulo(),
//						nuevoStockSeleccionado, nuevoStockDesarme);
				
				this.instanciaSistema.modificarStock(articuloSeleccionado.getIdArticulo(), nuevoStockSeleccionado, 
						articuloSeleccionado.getStock() - nuevoStockSeleccionado, tipoMovimientoDeStock.desarmeStock, motivo);
				
				this.instanciaSistema.modificarStock(articuloParaDesarme.getIdArticulo(), nuevoStockDesarme, 
						nuevoStockDesarme - articuloParaDesarme.getStock(), tipoMovimientoDeStock.desarmeStock, motivo);
				

				contexto.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"Desarme realizado con éxito.", ""));
			}
		} catch (Excepciones ex){ 
			
			contexto.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							ex.getMessage(),
							""));
		}catch (Exception e) {
			e.printStackTrace();
			contexto.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al ingresar cambio. Intente nuevamente",
							""));
		}
	}

	public void setISistema(ISistema s) {
		this.instanciaSistema = s;

		if (this.instanciaSistema != null) {
			// Cargo las marcas de la base de datos
			cargarMarcas();

			// Cargo los proveedores de la base de datos
			cargarProveedores();

			// Cargo las drogas de la base de datos
			cargarDrogas();

			// Cargo las acciones terapéuticas de la base de datos
			cargarAccionesTerapeuticas();

			// Cargo tipos de articulo para el combo
			cargarTiposArticulo();

			// Cargo formas de venta para el combo
			cargarFormasVenta();

			// Cargo tipos de iva para el combo
			cargarTiposIva();
		}
	}

}
