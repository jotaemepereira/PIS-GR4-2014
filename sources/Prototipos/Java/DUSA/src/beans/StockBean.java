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
import model.Enumerados;
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
import datatypes.DTModificacionArticulo;
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
	private String radioPrecioVenta;
	
	//Modificación
	private boolean modificacion;
	private Articulo articuloSinCambios;
	private DTModificacionArticulo articuloModificado;	
	 
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
	private char tipoIvaSeleccionado;

	// Busqueda de artículos
	private String busqueda = "";
	private List<DTBusquedaArticulo> resBusqueda = new ArrayList<DTBusquedaArticulo>();

	//Para las selecciones de las tablas
	private DTBusquedaArticulo articuloSeleccionado;
	private DTProveedor proveedorSeleccionado;

	/* Operaciones para Alta de Artículo */
	
	public void altaArticulo() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (!proveedoresSeleccionados.isEmpty()) {
			try {
				/* Cargo los proveedores seleccionados en el articulo */
				Iterator<DTProveedor> i = proveedoresSeleccionados.iterator();
				while (i.hasNext()) {
					DTProveedor next = i.next();
					DTProveedor p = new DTProveedor();
					p.setIdProveedor(next.getIdProveedor());
					p.setNombreComercial(proveedores.get(next.getIdProveedor())
							.getNombreComercial());
					p.setCodigoIdentificador(next.getCodigoIdentificador());
					this.articulo.agregarProveedor(p);
				}

				/* Cargo el tipo de iva seleccionado */
				TipoIva ti = new TipoIva();
				ti.setTipoIVA(tipoIvaSeleccionado);
				articulo.setTipoIva(ti);

				/* Cargo el precio de venta según corresponda */

				/*
				 * Si no se carga nada, se asume el mismo que el precio público.
				 * Si se carga precio de venta, se calcula el porcentaje.
				 */
				if (this.radioPrecioVenta.compareTo("$") == 0) {
					if (articulo.getPrecioVenta().compareTo(BigDecimal.ZERO) == 0) {
						articulo.setPrecioVenta(articulo.getPrecioUnitario());
						articulo.setPorcentajePrecioVenta(BigDecimal.ONE);
					} else {
						BigDecimal porcentaje = articulo
								.getPrecioVenta()
								.multiply(ONEHUNDRED)
								.divide(articulo.getPrecioUnitario(), 5,
										RoundingMode.DOWN).divide(ONEHUNDRED);
						articulo.setPorcentajePrecioVenta(porcentaje);
					}
				}

				/*
				 * Si no se carga nada, se asume el mismo que el precio público.
				 * Si se carga un porcentaje sobre el precio público, se calcula
				 * el precio de venta a partir de ese porcentaje.
				 */
				if (this.radioPrecioVenta.compareTo("%") == 0) {
					if (articulo.getPorcentajePrecioVenta().compareTo(
							BigDecimal.ZERO) == 0) {
						articulo.setPrecioVenta(articulo.getPrecioUnitario());
						articulo.setPorcentajePrecioVenta(BigDecimal.ONE);
					} else {
						articulo.setPrecioVenta(articulo.getPrecioUnitario()
								.multiply(articulo.getPorcentajePrecioVenta()));
					}
				}

				/* Cargo el usuario que realiza el alta */
				articulo.setUsuario(this.instanciaSistema
						.obtenerUsuarioLogueado());

				/*
				 * Llamo a la logica para que se de de alta el articulo en el
				 * sistema y en caso de error lo muestro
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
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
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
	
	/* Fin Operaciones para Alta de Artículo
	
	
	/* Operaciones para Modificación de Artículo */
	
	public void modificarArticulo(){
		FacesContext context = FacesContext.getCurrentInstance();
		if (!proveedoresSeleccionados.isEmpty()) {
			this.articuloModificado.setArticulo(articulo);
		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos un proveedor", ""));
		}
	}
	
	public void cancelarModificarArticulo(){
		refresh();
	}	
	
	/* Manejo del componente wizard en modificarArticulo.xhtml */
	public String onFlowProcess(FlowEvent event) {
		if (event.getNewStep().equals("tabModificacion")){ 
			this.modificacion = true;			
			cargarArticuloParaModificacion();
		}
		return event.getNewStep();
	}

	private void cargarArticuloParaModificacion() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.articulo = this.instanciaSistema
					.obtenerArticulo(articuloSeleccionado.getIdArticulo());
			this.articuloSinCambios = new Articulo(articulo);
			this.articuloModificado = new DTModificacionArticulo();
			this.proveedoresSeleccionados = new ArrayList<DTProveedor>(articulo.getProveedores().values());
			this.noEsMedicamento = articulo.getTipoArticulo() != Enumerados.tipoArticulo.MEDICAMENTO;
		} catch (Excepciones e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	/* Manejo de la búsqueda */
	
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
	
	/* Fin Operaciones para Modificación de Artículo */

	/* Operaciones de la tabla de proveedores tanto para el Alta como la Modificación */
	
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
						
						//Si estoy modificando, lo cargo en la lista de nuevos proveedores del articulo modificado.
						if (modificacion){
							this.articuloModificado.getProveedoresNuevos().add(p);
						}
						
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
	
	public void eliminarProveedor(){
		FacesContext context = FacesContext.getCurrentInstance();
		if (proveedorSeleccionado != null){
			//Si estoy modificando, lo cargo a la lista de proveedoresABorrar del articulo modificado.
			if (modificacion){
				this.articuloModificado.getProveedoresABorrar().add(proveedorSeleccionado);
			}
			this.proveedoresSeleccionados.remove(proveedorSeleccionado);
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

	/* Fin Operaciones de la tabla de proveedores */	

	/* Reseteo de formulario */
	
	public void refresh() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context
				.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse(); // Optional
	}

	/* Loaders */
	
	public void cargarMarcas() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.listaMarcas = this.instanciaSistema.obtenerMarcas();
		} catch (Excepciones e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public void cargarProveedores() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.proveedores = this.instanciaSistema.obtenerProveedores();
			this.listaProveedores = new ArrayList<DTProveedor>(
					this.proveedores.values());
		} catch (Excepciones e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public void cargarDrogas() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.listaDrogas = this.instanciaSistema.obtenerDrogas();
		} catch (Excepciones e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public void cargarAccionesTerapeuticas() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.listaAccionesTer = this.instanciaSistema
					.obtenerAccionesTerapeuticas();
		} catch (Excepciones e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
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
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.tiposIVA = this.instanciaSistema.obtenerTiposIva();
		} catch (Excepciones e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}	

	/* Fin Loaders */
	
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
	
	public DTBusquedaArticulo getArticuloSeleccionado() {
		return articuloSeleccionado;
	}

	public void setArticuloSeleccionado(DTBusquedaArticulo articuloSeleccionado) {
		this.articuloSeleccionado = articuloSeleccionado;
	}

	public DTProveedor getProveedorSeleccionado() {
		return proveedorSeleccionado;
	}

	public void setProveedorSeleccionado(DTProveedor proveedorSeleccionado) {
		this.proveedorSeleccionado = proveedorSeleccionado;
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

	public char getTipoIvaSeleccionado() {
		return tipoIvaSeleccionado;
	}

	public void setTipoIvaSeleccionado(char tipoIvaSeleccionado) {
		this.tipoIvaSeleccionado = tipoIvaSeleccionado;
	}

	public String getRadioPrecioVenta() {
		return radioPrecioVenta;
	}

	public void setRadioPrecioVenta(String radioPrecioVenta) {
		this.radioPrecioVenta = radioPrecioVenta;
	}

	public boolean isModificacion() {
		return modificacion;
	}

	public void setModificacion(boolean modificacion) {
		this.modificacion = modificacion;
	}

	public Articulo getArticuloAModificar() {
		return articuloSinCambios;
	}

	public void setArticuloAModificar(Articulo articuloAModificar) {
		this.articuloSinCambios = articuloAModificar;
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

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}
}
