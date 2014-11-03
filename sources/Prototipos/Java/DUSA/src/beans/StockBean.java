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

	private static final BigDecimal ONEHUNDRED = new BigDecimal(100);
	
	private ISistema instanciaSistema;

	private static final long serialVersionUID = 1L;
	private Articulo articulo = new Articulo();
	private boolean noEsMedicamento;
	private int tipoIvaSeleccionado;
	private String radioPrecioVenta;

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

	// Busqueda de artículos
	private String busqueda = "";
	private List<DTBusquedaArticulo> resBusqueda = new ArrayList<DTBusquedaArticulo>();

	//
	private DTBusquedaArticulo articuloSeleccionado;
	private int tipoMotivo;
	private String motivo;
	private String busquedaDesarme = "";
	private List<DTBusquedaArticulo> resBusquedaDesarme = new ArrayList<DTBusquedaArticulo>();

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

	public String getRadioPrecioVenta() {
		return radioPrecioVenta;
	}

	public void setRadioPrecioVenta(String radioPrecioVenta) {
		this.radioPrecioVenta = radioPrecioVenta;
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

	public void buscarArticulos() {
		resBusqueda = new ArrayList<DTBusquedaArticulo>();

		if (busqueda.equals("")) {
			return;
		}

		try {
			resBusqueda = this.instanciaSistema.buscarArticulos(busqueda);			
			System.out.println("CANTIDAD ENCONTRADA: " + resBusqueda.size());
		} catch (Excepciones e) {
			e.printStackTrace();
		}
	}
	
	public void agregarProveedor() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (proveedor != 0) {
			if (!existeProveedor(proveedor)) {
				try {
					if (codigoIdentificador == 0
							|| !existeCodigoParaProveedor(proveedor,
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
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
				}
			} else {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Ya seleccionó el proveedor.", ""));
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
				 * público.
				 * Si se carga precio de venta, se calcula el porcentaje.
				 */
				if (this.radioPrecioVenta.compareTo("$") == 0){
					if (articulo.getPrecioVenta().compareTo(BigDecimal.ZERO) == 0){
						articulo.setPrecioVenta(articulo.getPrecioUnitario());
						articulo.setPorcentajePrecioVenta(BigDecimal.ONE);
					}else{
						BigDecimal porcentaje = articulo.getPrecioVenta()
								.multiply(ONEHUNDRED).divide(articulo.getPrecioUnitario(), 5,
								RoundingMode.DOWN).divide(ONEHUNDRED);
						articulo.setPorcentajePrecioVenta(porcentaje);
					}
				}
				
				/*
				 * Si no se carga nada, se asume el mismo que el precio
				 * público.
				 * Si se carga un porcentaje sobre el precio público, se
				 * calcula el precio de venta a partir de ese porcentaje.
				 */
				if (this.radioPrecioVenta.compareTo("%") == 0){
					if (articulo.getPorcentajePrecioVenta().compareTo(BigDecimal.ZERO) == 0) {
						articulo.setPrecioVenta(articulo.getPrecioUnitario());
						articulo.setPorcentajePrecioVenta(BigDecimal.ONE);
					}
					else{
						articulo.setPrecioVenta(articulo.getPrecioUnitario()
								.multiply(articulo.getPorcentajePrecioVenta()));
					}
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
				} else if (e.getErrorCode() == Excepciones.USUARIO_NO_TIENE_PERMISOS) {
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
	
	public StockBean() {
		this.noEsMedicamento = true;
		this.radioPrecioVenta = "$";
	}
	
}
