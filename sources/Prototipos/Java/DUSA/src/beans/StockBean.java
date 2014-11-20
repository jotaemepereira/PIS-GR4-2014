package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import controladores.Excepciones;
import controladores.FabricaLogica;
import controladores.FabricaSistema;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Enumerados;
import model.Enumerados.casoDeUso;
import model.Enumerados.tipoMovimientoDeStock;
import model.TipoIva;
import model.Usuario;
import model.Enumerados.TipoFormaDePago;
import model.LineaPedido;
import model.Pedido;
import model.Presentacion;
import datatypes.DTBusquedaArticuloSolr;
import datatypes.DTBusquedaArticulo;
import datatypes.DTDescuentoReceta;
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
	private boolean noPrecioFijo;

	// Modificación
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
	private List<DTDescuentoReceta> descuentosReceta = new ArrayList<DTDescuentoReceta>();
	private short descuentoRecetaSeleccionado;

	// Busqueda de artículos
	private String busqueda = "";
	private List<DTBusquedaArticulo> resBusqueda = new ArrayList<DTBusquedaArticulo>();

	// Para las selecciones de las tablas
	private DTBusquedaArticulo articuloSeleccionado;
	private DTProveedor proveedorSeleccionado;

	// Busqueda de proveedores
	private String busquedaProveedor = "";
	private List<Articulo> articulosDelProveedor;

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
				if (articulo.getTipoArticulo() == model.Enumerados.tipoArticulo.MEDICAMENTO) {
					if (articulo.isEsEstupefaciente()
							|| articulo.isEsPsicofarmaco()) {
						ti.setTipoIVA(model.Enumerados.tiposIVA.PSICOFARMACOS);
					} else {
						if (tipoIvaSeleccionado == model.Enumerados.tiposIVA.IVA10) {
							ti.setTipoIVA(model.Enumerados.tiposIVA.MEDICAMENTOS);
						} else if (tipoIvaSeleccionado == model.Enumerados.tiposIVA.IVA22) {
							ti.setTipoIVA(model.Enumerados.tiposIVA.IVA22TRIBUTO);
						} else {
							ti.setTipoIVA(tipoIvaSeleccionado);
						}
					}
				} else {
					ti.setTipoIVA(tipoIvaSeleccionado);
				}
				articulo.setTipoIva(ti);

				/* Cargo precio con receta y descuento con receta si corresponde */
				if (descuentoRecetaSeleccionado != 0) {
					if (descuentoRecetaSeleccionado == Enumerados.descuentosReceta.VEINTICINCO) {
						articulo.setPorcentajeDescuentoReceta(new BigDecimal(
								0.75));
						articulo.setPrecioConReceta(articulo
								.getPrecioUnitario()
								.multiply(
										articulo.getPorcentajeDescuentoReceta()));
					} else if (descuentoRecetaSeleccionado == Enumerados.descuentosReceta.CUARENTA) {
						articulo.setPorcentajeDescuentoReceta(new BigDecimal(
								0.60));
						articulo.setPrecioConReceta(articulo
								.getPrecioUnitario()
								.multiply(
										articulo.getPorcentajeDescuentoReceta()));
					} else if (descuentoRecetaSeleccionado == Enumerados.descuentosReceta.FIJO) {
						articulo.setPorcentajeDescuentoReceta(articulo
								.getPrecioConReceta().divide(
										articulo.getPrecioUnitario(), 5,
										RoundingMode.DOWN));
					}
				} else {
					articulo.setPorcentajeDescuentoReceta(BigDecimal.ZERO);
					articulo.setPrecioConReceta(BigDecimal.ZERO);
				}

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
				this.descuentoRecetaSeleccionado = 0;
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

	/*
	 * Fin Operaciones para Alta de Artículo
	 * 
	 * 
	 * /* Operaciones para Modificación de Artículo
	 */

	public void modificarArticulo() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if (!proveedoresSeleccionados.isEmpty()) {

				/* Cargo el tipo de iva seleccionado */
				TipoIva ti = new TipoIva();
				if (articulo.getTipoArticulo() == model.Enumerados.tipoArticulo.MEDICAMENTO) {
					if (articulo.isEsEstupefaciente()
							|| articulo.isEsPsicofarmaco()) {
						ti.setTipoIVA(model.Enumerados.tiposIVA.PSICOFARMACOS);
					} else {
						if (tipoIvaSeleccionado == model.Enumerados.tiposIVA.IVA10) {
							ti.setTipoIVA(model.Enumerados.tiposIVA.MEDICAMENTOS);
						} else if (tipoIvaSeleccionado == model.Enumerados.tiposIVA.IVA22) {
							ti.setTipoIVA(model.Enumerados.tiposIVA.IVA22TRIBUTO);
						} else {
							ti.setTipoIVA(tipoIvaSeleccionado);
						}
					}
				} else {
					ti.setTipoIVA(tipoIvaSeleccionado);
				}
				articulo.setTipoIva(ti);

				/* Cargo precio con receta y descuento con receta si corresponde */
				if (descuentoRecetaSeleccionado != 0) {
					if (descuentoRecetaSeleccionado == Enumerados.descuentosReceta.VEINTICINCO) {
						articulo.setPorcentajeDescuentoReceta(new BigDecimal(
								0.75));
						articulo.setPrecioConReceta(articulo
								.getPrecioUnitario()
								.multiply(
										articulo.getPorcentajeDescuentoReceta()));
					} else if (descuentoRecetaSeleccionado == Enumerados.descuentosReceta.CUARENTA) {
						articulo.setPorcentajeDescuentoReceta(new BigDecimal(
								0.60));
						articulo.setPrecioConReceta(articulo
								.getPrecioUnitario()
								.multiply(
										articulo.getPorcentajeDescuentoReceta()));
					} else if (descuentoRecetaSeleccionado == Enumerados.descuentosReceta.FIJO) {
						articulo.setPorcentajeDescuentoReceta(articulo
								.getPrecioConReceta().divide(
										articulo.getPrecioUnitario(), 5,
										RoundingMode.DOWN));
					}
				} else {
					articulo.setPorcentajeDescuentoReceta(BigDecimal.ZERO);
					articulo.setPrecioConReceta(BigDecimal.ZERO);
				}

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

				/* Cargo el usuario que realiza la modificacion */
				articulo.setUsuario(this.instanciaSistema
						.obtenerUsuarioLogueado());

				/* Proceso los cambios existentes */
				procesarCambios();

				/*
				 * Llamo a la logica para que se realice la modificacion del
				 * articulo en el sistema y en caso de error lo muestro
				 */
				instanciaSistema.modificarArticulo(articuloModificado);

				// si todo bien aviso y vacio el formulario
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						Excepciones.MENSAJE_OK_MODIFICACION, ""));
				this.articulo = new Articulo();
				this.proveedoresSeleccionados = new ArrayList<DTProveedor>();
				this.proveedor = 0;
				this.codigoIdentificador = 0;
				this.tipoIvaSeleccionado = 0;
				this.descuentoRecetaSeleccionado = 0;
			} else {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Debe seleccionar al menos un proveedor", ""));
			}
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
	}

	public void cancelarModificarArticulo() {
		refresh();
	}

	/**
	 * Esta operación se encarga de procesar todos los cambios del formulario
	 * con respecto al artículo inicial. Chequea las modificaciones y marca los
	 * campos que sufrieron cambios.
	 */
	private void procesarCambios() {
		// Si el articulo deja de ser de tipo medicamento se modifican los
		// campos correspondientes
		// a articulos que son medicamentos
		if (articulo.getTipoArticulo() != Enumerados.tipoArticulo.MEDICAMENTO) {
			articulo.setEsEstupefaciente(false);
			articulo.setEsPsicofarmaco(false);
			articulo.setCodigoVenta((char) 0x00);
			articulo.setPorcentajeDescuentoReceta(BigDecimal.ZERO);
			articulo.setPrecioConReceta(BigDecimal.ZERO);
		}

		articulo.setClave1Modificado(articuloSinCambios.getClave1().compareTo(
				articulo.getClave1()) != 0);
		articulo.setClave2Modificado(articuloSinCambios.getClave2().compareTo(
				articulo.getClave2()) != 0);
		articulo.setClave3Modificado(articuloSinCambios.getClave3().compareTo(
				articulo.getClave3()) != 0);
		articulo.setCodigoBarrasModificado(articuloSinCambios.getCodigoBarras()
				.compareTo(articulo.getCodigoBarras()) != 0);
		articulo.setCodigoVentaModificado(articuloSinCambios.getCodigoVenta() != articulo
				.getCodigoVenta());
		articulo.setCostoListaModificado(articuloSinCambios.getCostoLista()
				.compareTo(articulo.getCostoLista()) != 0);
		articulo.setCostoOfertaModificado(articuloSinCambios.getCostoOferta()
				.compareTo(articulo.getCostoOferta()) != 0);
		articulo.setCostoPromedioModificado(articuloSinCambios
				.getCostoPromedio().compareTo(articulo.getCostoPromedio()) != 0);
		articulo.setDescripcionModificado(articuloSinCambios.getDescripcion()
				.compareTo(articulo.getDescripcion()) != 0);
		articulo.setEsEstupefacienteModificado(articuloSinCambios
				.isEsEstupefaciente() != articulo.isEsEstupefaciente());
		articulo.setEsHeladeraModificado(articuloSinCambios.isEsHeladera() != articulo
				.isEsHeladera());
		articulo.setEsPsicofarmacoModificado(articuloSinCambios
				.isEsPsicofarmaco() != articulo.isEsPsicofarmaco());
		articulo.setIdMarcaModificado(articuloSinCambios.getIdMarca() != articulo
				.getIdMarca());
		articulo.setPorcentajeDescuentoRecetaModificado(articuloSinCambios
				.getPorcentajeDescuentoReceta().compareTo(
						articulo.getPorcentajeDescuentoReceta()) != 0);
		articulo.setPorcentajePrecioVentaModificado(articuloSinCambios
				.getPorcentajePrecioVenta().compareTo(
						articulo.getPorcentajePrecioVenta()) != 0);
		articulo.setPrecioConRecetaModificado(articuloSinCambios
				.getPrecioConReceta().compareTo(articulo.getPrecioConReceta()) != 0);
		articulo.setPrecioUnitarioModificado(articuloSinCambios
				.getPrecioUnitario().compareTo(articulo.getPrecioUnitario()) != 0);
		articulo.setPrecioVentaModificado(articuloSinCambios.getPrecioVenta()
				.compareTo(articulo.getPrecioVenta()) != 0);
		if (articuloSinCambios.getPresentacion() != null) {
			articulo.setPresentacionModificado(articuloSinCambios
					.getPresentacion().compareTo(articulo.getPresentacion()) != 0);
		} else {
			articulo.setPresentacionModificado(true);
		}
		articulo.setStockMinimoModificado(articuloSinCambios.getStockMinimo() != articulo
				.getStockMinimo());
		articulo.setTipoArticuloModificado(articuloSinCambios.getTipoArticulo() != articulo
				.getTipoArticulo());
		articulo.setTipoAutorizacionModificado(articuloSinCambios
				.getTipoAutorizacion() != articulo.getTipoAutorizacion());
		articulo.setTipoIvaModificado(articuloSinCambios.getTipoIva()
				.getTipoIVA() != articulo.getTipoIva().getTipoIVA());
		articulo.setUltimoCostoModificado(articuloSinCambios.getUltimoCosto()
				.compareTo(articulo.getUltimoCosto()) != 0);
		articulo.setUsuarioModificado(articuloSinCambios
				.getUsuario()
				.getNombre()
				.compareTo(
						instanciaSistema.obtenerUsuarioLogueado().getNombre()) != 0);
		if (articuloSinCambios.getVencimientoMasCercano() != null) {
			if (articulo.getVencimientoMasCercano() != null) {
				articulo.setVencimientoMasCercanoModificado(articuloSinCambios
						.getVencimientoMasCercano().compareTo(
								articulo.getVencimientoMasCercano()) != 0);
			} else {
				articulo.setVencimientoMasCercanoModificado(true);
			}
		}

		// Chequeo cambios en proveedores
		procesarProveedores();
		// Chequeo cambios en drogas
		procesarDrogas();
		// Chequeo cambios en acciones terapeuticas
		procesarAccionesTer();
		this.articuloModificado.setArticulo(articulo);
	}

	/**
	 * Esta operación se encarga de chequear los cambios en los proveedores para
	 * la modificación cargando en el articuloModificado las listas de
	 * proveedores como corresponda
	 */
	private void procesarProveedores() {
		// Me fijo para cada proveedor del articulo actual si el mismo ya
		// existia
		// en caso de no existir es un proveedor nuevo entonces lo
		// agrego a la lista de nuevasProveedores
		for (DTProveedor proveedor : proveedoresSeleccionados) {
			if (!articuloSinCambios.getProveedores().containsKey(
					proveedor.getIdProveedor())) {
				articuloModificado.getProveedoresNuevos().add(proveedor);
				// Marco que hay cambios en los proveedores
				articulo.setProveedoresModificado(true);
			}
		}
		// Me fijo para cada proveedor del articulo sin cambios si el mismo
		// existe
		// en el articulo actual, en caso de no existir es porque no está mas
		// seleccionado entonces lo agrego a la lista de proveedoresABorrar
		for (DTProveedor proveedor : articuloSinCambios.getProveedores()
				.values()) {
			if (!proveedoresSeleccionados.contains(proveedor)) {
				articuloModificado.getProveedoresABorrar().add(proveedor);
				// Marco que hay cambios en los proveedores
				articulo.setProveedoresModificado(true);
			}
		}
	}

	/**
	 * Esta operación se encarga de chequear los cambios en las drogas para la
	 * modificación cargando en el articuloModificado los array de drogas como
	 * corresponda
	 */
	private void procesarDrogas() {
		if (articulo.getTipoArticulo() == Enumerados.tipoArticulo.MEDICAMENTO) {
			// Me fijo para cada droga del articulo actual si la misma ya
			// existia
			// en caso de no existir es una droga nueva seleccionada entonces la
			// agrego a la lista de nuevasDrogas
			for (long droga : articulo.getDrogas()) {
				if (!existeIdEnArray(droga, articuloSinCambios.getDrogas())) {
					articuloModificado.getDrogasNuevas().add(droga);
					// Marco que hay cambios en las drogas
					articulo.setDrogasModificado(true);
				}
			}
			// Me fijo para cada droga del articulo sin cambios si la misma
			// existe
			// en el articulo actual, en caso de no existir es porque no está
			// mas
			// seleccionada entonces la agrego a la lista de drogasABorrar
			for (long droga : articuloSinCambios.getDrogas()) {
				if (!existeIdEnArray(droga, articulo.getDrogas())) {
					articuloModificado.getDrogasABorrar().add(droga);
					// Marco que hay cambios en las drogas
					articulo.setDrogasModificado(true);
				}
			}
		}
		// Si no es medicamento no puede tener drogas. Si tenía se sacan
		else {
			for (long droga : articuloSinCambios.getDrogas()) {
				articuloModificado.getDrogasABorrar().add(droga);
				// Marco que hay cambios en las drogas
				articulo.setDrogasModificado(true);
			}
		}
	}

	/**
	 * Esta operación se encarga de chequear los cambios en las acciones
	 * terapeuticas para la modificación cargando en el articuloModificado los
	 * array de drogas como corresponda
	 */
	private void procesarAccionesTer() {
		if (articulo.getTipoArticulo() == Enumerados.tipoArticulo.MEDICAMENTO) {
			// Me fijo para cada accionTer del articulo actual si la misma ya
			// existia
			// en caso de no existir es una accionTer nueva seleccionada
			// entonces la
			// agrego a la lista de nuevasAccionesTer
			for (long accionTer : articulo.getAccionesTer()) {
				if (!existeIdEnArray(accionTer,
						articuloSinCambios.getAccionesTer())) {
					articuloModificado.getAccionesTerNuevas().add(accionTer);
					// Marco que hay cambios en las acciones terapeuticas
					articulo.setAccionesTerModificado(true);
				}
			}
			// Me fijo para cada accionTer del articulo sin cambios si la misma
			// existe
			// en el articulo actual, en caso de no existir es porque no está
			// mas
			// seleccionada entonces la agrego a la lista de accionesTerABorrar
			for (long accionTer : articuloSinCambios.getAccionesTer()) {
				if (!existeIdEnArray(accionTer, articulo.getAccionesTer())) {
					articuloModificado.getAccionesTerABorrar().add(accionTer);
					// Marco que hay cambios en las acciones terapeuticas
					articulo.setAccionesTerModificado(true);
				}
			}
		}
		// Si no es medicamento no puede tener acciones terapeuticas. Si tenía
		// se sacan
		else {
			for (long accionTer : articuloSinCambios.getAccionesTer()) {
				articuloModificado.getAccionesTerABorrar().add(accionTer);
				// Marco que hay cambios en las acciones terapeuticas
				articulo.setAccionesTerModificado(true);
			}
		}
	}

	private boolean existeIdEnArray(long id, long[] array) {
		boolean ret = false;
		int i = 0;
		int len = array.length;
		while (!ret && i < len) {
			ret = id == array[i];
			i++;
		}
		return ret;
	}

	/* Manejo del componente wizard en modificarArticulo.xhtml */
	public String onFlowProcess(FlowEvent event) {

		String siguienteTab;
		if (event.getNewStep().equals("tabModificacion")) {
			if (articuloSeleccionado != null) {
				this.modificacion = true;
				cargarArticuloParaModificacion();
				siguienteTab = event.getNewStep();
			} else {
				siguienteTab = event.getOldStep();
			}
		} else {
			siguienteTab = event.getNewStep();
		}

		return siguienteTab;
	}

	private void cargarArticuloParaModificacion() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.articulo = this.instanciaSistema
					.obtenerArticulo(articuloSeleccionado.getIdArticulo());
			if (articulo.getTipoIva() != null) {
				if (articulo.getTipoIva().getTipoIVA() == model.Enumerados.tiposIVA.PSICOFARMACOS
						|| articulo.getTipoIva().getTipoIVA() == model.Enumerados.tiposIVA.MEDICAMENTOS) {
					tipoIvaSeleccionado = model.Enumerados.tiposIVA.IVA10;
				} else if (articulo.getTipoIva().getTipoIVA() == model.Enumerados.tiposIVA.IVA22TRIBUTO) {
					tipoIvaSeleccionado = model.Enumerados.tiposIVA.IVA22;
				} else {
					tipoIvaSeleccionado = articulo.getTipoIva().getTipoIVA();
				}
			}
			if (articulo.getTipoArticulo() == Enumerados.tipoArticulo.MEDICAMENTO) {
				if (articulo.getPorcentajeDescuentoReceta().compareTo(
						new BigDecimal(0.75)) == 0) {
					this.descuentoRecetaSeleccionado = Enumerados.descuentosReceta.VEINTICINCO;
				} else if (articulo.getPorcentajeDescuentoReceta().compareTo(
						new BigDecimal(0.60)) == 0) {
					this.descuentoRecetaSeleccionado = Enumerados.descuentosReceta.CUARENTA;
				} else {
					this.descuentoRecetaSeleccionado = Enumerados.descuentosReceta.FIJO;
					this.noPrecioFijo = false;
				}
			}
			this.articuloSinCambios = new Articulo(articulo);
			this.articuloModificado = new DTModificacionArticulo();
			this.proveedoresSeleccionados = new ArrayList<DTProveedor>(articulo
					.getProveedores().values());
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

		} catch (Excepciones e) {
			e.printStackTrace();
		}
	}

	/* Fin Operaciones para Modificación de Artículo */

	/* Modificar precio de articulos */

	public void buscarProveedores() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (busquedaProveedor.equals("")) {
			return;
		}

		articulosDelProveedor = new ArrayList<Articulo>();
		long idProveedor = Long.parseLong(busquedaProveedor);

		try {
			articulosDelProveedor = this.instanciaSistema
					.obtenerArticulosDelProveedor(idProveedor);
		} catch (Excepciones e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	/*
	 * Operaciones de la tabla de proveedores tanto para el Alta como la
	 * Modificación
	 */

	/*
	 * Operaciones de la tabla de proveedores tanto para el Alta como la
	 * Modificación
	 */

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

	public void eliminarProveedor() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (proveedorSeleccionado != null) {
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

	public void cargarMarcas() throws Excepciones {
		if (listaMarcas.isEmpty()) {

			this.listaMarcas = this.instanciaSistema.obtenerMarcas();
		}
	}

	public void cargarProveedores() throws Excepciones {
		if (listaProveedores.isEmpty()) {

			this.proveedores = this.instanciaSistema.obtenerProveedores();
			this.listaProveedores = new ArrayList<DTProveedor>(
					this.proveedores.values());
		}
	}

	public void cargarDrogas() throws Excepciones {
		if (listaDrogas.isEmpty()) {

			this.listaDrogas = this.instanciaSistema.obtenerDrogas();
		}
	}

	public void cargarAccionesTerapeuticas() throws Excepciones {
		if (listaAccionesTer.isEmpty()) {

			this.listaAccionesTer = this.instanciaSistema
					.obtenerAccionesTerapeuticas();
		}
	}

	public void cargarTiposArticulo() {
		if (tiposArticulo.isEmpty()) {
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
	}

	public void tipoArticuloChange() {
		if (articulo.getTipoArticulo() == model.Enumerados.tipoArticulo.MEDICAMENTO) {
			this.noEsMedicamento = false;
		} else {
			this.noEsMedicamento = true;
		}
	}

	public void descuentoRecetaChange() {
		if (descuentoRecetaSeleccionado == model.Enumerados.descuentosReceta.FIJO) {
			this.noPrecioFijo = false;
		} else {
			this.noPrecioFijo = true;
		}
	}

	public void cargarFormasVenta() {
		if (formasVenta.isEmpty()) {
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
	}

	public void cargarDescuentosReceta() {
		if (descuentosReceta.isEmpty()) {
			DTDescuentoReceta dr = new DTDescuentoReceta();
			dr.setTipoDescuento(Enumerados.descuentosReceta.VEINTICINCO);
			dr.setDescripcion(Enumerados.descuentosRecetaDesc.VEINTICINCO);
			descuentosReceta.add(dr);
			dr = new DTDescuentoReceta();
			dr.setTipoDescuento(Enumerados.descuentosReceta.CUARENTA);
			dr.setDescripcion(Enumerados.descuentosRecetaDesc.CUARENTA);
			descuentosReceta.add(dr);
			dr = new DTDescuentoReceta();
			dr.setTipoDescuento(Enumerados.descuentosReceta.FIJO);
			dr.setDescripcion(Enumerados.descuentosRecetaDesc.FIJO);
			descuentosReceta.add(dr);
		}
	}

	public void cargarTiposIva() throws Excepciones {
		if (tiposIVA.isEmpty()) {
			TipoIva ti = new TipoIva();
			ti.setTipoIVA(model.Enumerados.tiposIVA.IVAEXENTO);
			ti.setDescripcion(model.Enumerados.tiposIVAParaMostrar.IVAEXENTO);
			tiposIVA.add(ti);
			ti = new TipoIva();
			ti.setTipoIVA(model.Enumerados.tiposIVA.IVA10);
			ti.setDescripcion(model.Enumerados.tiposIVAParaMostrar.IVA10);
			tiposIVA.add(ti);
			ti = new TipoIva();
			ti.setTipoIVA(model.Enumerados.tiposIVA.IVA22);
			ti.setDescripcion(model.Enumerados.tiposIVAParaMostrar.IVA22);
			tiposIVA.add(ti);
		}
	}

	/* Fin Loaders */

	public void setISistema(ISistema s) {
		this.instanciaSistema = s;

		if (this.instanciaSistema != null) {

			try {
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

				// Cargo descuentos con receta
				cargarDescuentosReceta();

				// Cargo tipos de iva para el combo
				cargarTiposIva();

			} catch (Excepciones ex) {

				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
			}
		}
	}

	public StockBean() {
		this.listaAccionesTer = new ArrayList<AccionTer>();
		this.listaDrogas = new ArrayList<Droga>();
		this.listaMarcas = new ArrayList<DTProveedor>();
		this.listaProveedores = new ArrayList<DTProveedor>();
		this.tiposIVA = new ArrayList<TipoIva>();
		this.noEsMedicamento = true;
		this.radioPrecioVenta = "$";
		this.noPrecioFijo = true;
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

	public List<DTDescuentoReceta> getDescuentosReceta() {
		return descuentosReceta;
	}

	public void setDescuentosReceta(List<DTDescuentoReceta> descuentosReceta) {
		this.descuentosReceta = descuentosReceta;
	}

	public short getDescuentoRecetaSeleccionado() {
		return descuentoRecetaSeleccionado;
	}

	public void setDescuentoRecetaSeleccionado(short descuentoRecetaSeleccionado) {
		this.descuentoRecetaSeleccionado = descuentoRecetaSeleccionado;
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

	public String getBusquedaProveedor() {
		return busquedaProveedor;
	}

	public void setBusquedaProveedor(String busquedaProveedor) {
		this.busquedaProveedor = busquedaProveedor;
	}

	public List<Articulo> getArticulosDelProveedor() {
		return articulosDelProveedor;
	}

	public void setArticulosDelProveedor(List<Articulo> articulosDelProveedor) {
		this.articulosDelProveedor = articulosDelProveedor;
	}

	public boolean isNoPrecioFijo() {
		return noPrecioFijo;
	}

	public void setNoPrecioFijo(boolean precioFijo) {
		this.noPrecioFijo = precioFijo;
	}
}
