package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Enumerados.tipoMovimientoDeStock;

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

	public void buscarArticulosDesarme() {
		resBusquedaDesarme = new ArrayList<DTBusquedaArticulo>();

		if (busquedaDesarme.equals("")) {
			return;
		}

		try {
			resBusquedaDesarme = this.instanciaSistema
					.buscarArticulos(busquedaDesarme);
			
			for (Iterator<DTBusquedaArticulo> iterator = resBusquedaDesarme.iterator(); iterator.hasNext();) {
				DTBusquedaArticulo art = (DTBusquedaArticulo) iterator.next();
				
				if (art.getIdArticulo() == this.articuloSeleccionado.getIdArticulo()) {
					
					iterator.remove();
				}
				
			}
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
	
}
