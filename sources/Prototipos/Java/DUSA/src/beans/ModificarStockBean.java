package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import model.Enumerados.tipoMovimientoDeStock;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import controladores.Excepciones;
import datatypes.DTBusquedaArticulo;

@ManagedBean
@ViewScoped
public class ModificarStockBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2585320440276487956L;

	private ISistema instanciaSistema;
	
	// Busqueda de artículos
	private String busqueda = "";
	private List<DTBusquedaArticulo> resBusqueda = new ArrayList<DTBusquedaArticulo>();
	private String busquedaDesarme = "";
	private List<DTBusquedaArticulo> resBusquedaDesarme = new ArrayList<DTBusquedaArticulo>();
	
	private DTBusquedaArticulo articuloSeleccionado;
	private DTBusquedaArticulo articuloParaDesarme;
	private String motivo;
	private long nuevoStockSeleccionado;
	private long nuevoStockDesarme;
	private long[] nuevoStock;
	
	
	//Getters y Seters
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getBusqueda() {
		return busqueda;
	}
	
	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}
	
	public List<DTBusquedaArticulo> getResBusqueda() {
		return resBusqueda;
	}
	
	public void setResBusqueda(List<DTBusquedaArticulo> resBusqueda) {
		this.resBusqueda = resBusqueda;
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
	
	public DTBusquedaArticulo getArticuloSeleccionado() {
		return articuloSeleccionado;
	}
	
	public void setArticuloSeleccionado(DTBusquedaArticulo articuloSeleccionado) {
		this.articuloSeleccionado = articuloSeleccionado;
	}
	
	public DTBusquedaArticulo getArticuloParaDesarme() {
		return articuloParaDesarme;
	}
	
	public void setArticuloParaDesarme(DTBusquedaArticulo articuloParaDesarme) {
		this.articuloParaDesarme = articuloParaDesarme;
	}
	
	public String getMotivo() {
		return motivo;
	}
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public long getNuevoStockSeleccionado() {
		return nuevoStockSeleccionado;
	}
	
	public void setNuevoStockSeleccionado(long nuevoStockSeleccionado) {
		this.nuevoStockSeleccionado = nuevoStockSeleccionado;
	}
	
	public long getNuevoStockDesarme() {
		return nuevoStockDesarme;
	}
	
	public void setNuevoStockDesarme(long nuevoStockDesarme) {
		this.nuevoStockDesarme = nuevoStockDesarme;
	}
	
	public long[] getNuevoStock() {
		return nuevoStock;
	}
	
	public void setNuevoStock(long[] nuevoStock) {
		this.nuevoStock = nuevoStock;
	}
	
	//Métodos
	/**
	 * Utilizado en el xhtml por el loginBean
	 * @param s
	 */
	public void setISistema(ISistema s) {
		
		this.instanciaSistema = s;
	}
	
	/**
	 * Manejo del componente wizard en nuevoDesarme.xhtml
	 * @param event
	 * @return
	 */
	public String onFlowProcess(FlowEvent event) {
		
		//Si no se selecciona un artículo se notifica y se mantiene el wizard en el mismo paso (tab).
		if ((event.getNewStep().equals("paso2") && this.articuloSeleccionado == null) || 
			(event.getNewStep().equals("paso3") && this.articuloParaDesarme == null)){
			//TODO:La notificación no se esta desplegando correctamente.
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					Excepciones.MENSAJE_SELECCIONE_ARTICULO, ""));
			
			RequestContext.getCurrentInstance().update("msgs");
			return event.getOldStep();
		}
		
		return event.getNewStep();
	}
	
	public void buscarArticulos() {
		resBusqueda = new ArrayList<DTBusquedaArticulo>();

		if (busqueda.equals("")) {
			return;
		}

		try {
			resBusqueda = this.instanciaSistema.buscarArticulos(busqueda);
			if (resBusqueda != null && resBusqueda.size() > 0) {
				//Se carga el stock para modificar
				nuevoStock = new long[resBusqueda.size()];
				for (int i = 0; i < resBusqueda.size(); i++) {
					nuevoStock[i] = resBusqueda.get(i).getStock();
				}
			}
			System.out.println("CANTIDAD ENCONTRADA: " + resBusqueda.size());
		} catch (Excepciones e) {
			
			e.printStackTrace();
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					e.getMessage(), ""));
		}
	}

	public void buscarArticulosDesarme() {
		resBusquedaDesarme = new ArrayList<DTBusquedaArticulo>();

		if (busquedaDesarme.equals("")) {
			return;
		}

		try {
			resBusquedaDesarme = this.instanciaSistema
					.buscarArticulos(busquedaDesarme);
			//Se elimina de la búsqueda el artículo seleccionado en el paso anterior.
			for (Iterator<DTBusquedaArticulo> iterator = resBusquedaDesarme.iterator(); iterator.hasNext();) {
				DTBusquedaArticulo art = (DTBusquedaArticulo) iterator.next();
				
				if (art.getIdArticulo() == this.articuloSeleccionado.getIdArticulo()) {
					
					iterator.remove();
				}
				
			}
		} catch (Excepciones e) {
			
			e.printStackTrace();
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					e.getMessage(), ""));
		}
	}

	public void confirmarCambioStock() {
		FacesContext contexto = FacesContext.getCurrentInstance();
		try {
			if (this.resBusqueda.isEmpty()) {
				
				contexto.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						Excepciones.MENSAJE_CANT_IGUALES, ""));
			} else if (motivo.trim().isEmpty()) {
				
				contexto.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						Excepciones.MENSAJE_MOTIVO_VACIO, ""));
			} else {
				int cambios = 0;
				for (int i = 0; i < resBusqueda.size(); i++) {
					
					long actualStock = resBusqueda.get(i).getStock();
					if (actualStock != nuevoStock[i]) {
						
						cambios++;
						//Comparo para generar el registro del movimiento de stock 
						if (actualStock < nuevoStock[i]) {
							
							this.instanciaSistema.modificarStock(resBusqueda.get(i).getIdArticulo(),
									nuevoStock[i], nuevoStock[i]-actualStock, tipoMovimientoDeStock.aumentoStock, motivo.trim());
						} else {
							
							this.instanciaSistema.modificarStock(resBusqueda.get(i).getIdArticulo(),
									nuevoStock[i], actualStock-nuevoStock[i], tipoMovimientoDeStock.bajaStock, motivo.trim());
						}
					}
				}

				if (cambios > 0) {
					//Se han realizado cambios con exito.
					String controlPlural = (cambios == 1) ? " artículo.":" artículos.";
					contexto.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Se ha realizado el cambio de stock de " + cambios + controlPlural, ""));//"Cambio de stock realizado con éxito."

					resBusqueda = new ArrayList<DTBusquedaArticulo>();
					motivo = "";
					busqueda = "";
				} else {
					contexto.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							Excepciones.MENSAJE_CANT_IGUALES, ""));

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
							Excepciones.MENSAJE_ERROR_SISTEMA,
							""));
		}
	}

	public void confirmarDesarme() {
		FacesContext contexto = FacesContext.getCurrentInstance();
		try {
			
			if (motivo.trim().isEmpty()) {
				contexto.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						Excepciones.MENSAJE_MOTIVO_VACIO, ""));
			} else if (nuevoStockSeleccionado >= articuloSeleccionado.getStock()) {
				contexto.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								Excepciones.MENSAJE_CANT_INVALIDA_ORIGEN,
								""));
			} else if (nuevoStockDesarme <= articuloParaDesarme.getStock()) {
				contexto.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								Excepciones.MENSAJE_CANT_INVALIDA_DESTINO,
								""));
			} else {
				
				this.instanciaSistema.modificarStock(articuloSeleccionado.getIdArticulo(), nuevoStockSeleccionado, 
						articuloSeleccionado.getStock() - nuevoStockSeleccionado, tipoMovimientoDeStock.desarmeStock, motivo.trim());
				
				this.instanciaSistema.modificarStock(articuloParaDesarme.getIdArticulo(), nuevoStockDesarme, 
						nuevoStockDesarme - articuloParaDesarme.getStock(), tipoMovimientoDeStock.desarmeStock, motivo.trim());
				

				contexto.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						Excepciones.MENSAJE_DESARME_EXITO, ""));
				
				//Refresh de pagina para regresar al estado inicial
				
				this.articuloParaDesarme = null;
				this.articuloSeleccionado = null;
				this.busqueda = "";
				this.busquedaDesarme = "";
				this.resBusqueda.clear();
				this.resBusquedaDesarme.clear();
				nuevoStockSeleccionado = 0;
				nuevoStockDesarme = 0;
				
				FacesContext context = FacesContext.getCurrentInstance();
				Application application = context.getApplication();
				ViewHandler viewHandler = application.getViewHandler();
				UIViewRoot viewRoot = viewHandler.createView(context, context
						.getViewRoot().getViewId());
				context.setViewRoot(viewRoot);
				context.renderResponse(); 
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
							Excepciones.MENSAJE_ERROR_SISTEMA,
							""));
		}
	}
	
}
