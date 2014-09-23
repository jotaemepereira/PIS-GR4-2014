package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import controladores.FabricaSistema;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Presentacion;
import datatypes.DTFormasVenta;
import datatypes.DTLineaPedido;
import datatypes.DTProveedor;

@ManagedBean
@SessionScoped
public class StockBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Articulo articulo = new Articulo();
	private DTProveedor proveedor = new DTProveedor();
	private List<DTProveedor> proveedores = new ArrayList<DTProveedor>();
	private List<DTProveedor> proveedoresSeleccionados = new ArrayList<DTProveedor>();
	private long codigoIdentificador;
	private Presentacion presentacion = new Presentacion();
	private List<Presentacion> presentaciones = new ArrayList<Presentacion>();
	private List<Presentacion> presentacionesSeleccionadas = new ArrayList<Presentacion>();
	private Droga droga = new Droga();
	private List<Droga> drogas = new ArrayList<Droga>();
	private List<Droga> drogasSeleccionadas = new ArrayList<Droga>();
	private AccionTer accionTer = new AccionTer();
	private List<AccionTer> accionesTer = new ArrayList<AccionTer>();
	private List<AccionTer> accionesTerSeleccionadas = new ArrayList<AccionTer>();
	private List<DTFormasVenta> formasVenta = new ArrayList<DTFormasVenta>();
	private int[] tiposIVA;

	
	public Articulo getArticulo() {
		return articulo;
	}
	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
	public DTProveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(DTProveedor proveedor) {
		this.proveedor = proveedor;
	}
	public List<DTProveedor> getProveedores() {
		return proveedores;
	}
	public void setProveedores(List<DTProveedor> proveedores) {
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
	public List<Presentacion> getPresentacionesSeleccionadas() {
		return presentacionesSeleccionadas;
	}
	public void setPresentacionesSeleccionadas(
			List<Presentacion> presentacionesSeleccionadas) {
		this.presentacionesSeleccionadas = presentacionesSeleccionadas;
	}
	public Droga getDroga() {
		return droga;
	}
	public void setDroga(Droga droga) {
		this.droga = droga;
	}
	public List<Droga> getDrogas() {
		return drogas;
	}
	public void setDrogas(List<Droga> drogas) {
		this.drogas = drogas;
	}
	public List<Droga> getDrogasSeleccionadas() {
		return drogasSeleccionadas;
	}
	public void setDrogasSeleccionadas(List<Droga> drogasSeleccionadas) {
		this.drogasSeleccionadas = drogasSeleccionadas;
	}
	public AccionTer getAccionTer() {
		return accionTer;
	}
	public void setAccionTer(AccionTer accionTer) {
		this.accionTer = accionTer;
	}
	public List<AccionTer> getAccionesTer() {
		return accionesTer;
	}
	public void setAccionesTer(List<AccionTer> accionesTer) {
		this.accionesTer = accionesTer;
	}
	public List<AccionTer> getAccionesTerSeleccionadas() {
		return accionesTerSeleccionadas;
	}
	public void setAccionesTerSeleccionadas(List<AccionTer> accionesTerSeleccionadas) {
		this.accionesTerSeleccionadas = accionesTerSeleccionadas;
	}
	public List<DTFormasVenta> getFormasVenta() {
		return formasVenta;
	}
	public void setFormasVenta(List<DTFormasVenta> formasVenta) {
		this.formasVenta = formasVenta;
	}
	public int[] getTiposIVA() {
		return tiposIVA;
	}
	public void setTiposIVA(int[] tiposIVA) {
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
	public List<DTProveedor> getProveedoresSeleccionados() {
		return proveedoresSeleccionados;
	}
	public void setProveedoresSeleccionados(List<DTProveedor> proveedoresSeleccionados) {
		this.proveedoresSeleccionados = proveedoresSeleccionados;
	}
	
	public List<DTLineaPedido> pedidoAutomaticoVentas() {
		return FabricaSistema.getISistema().pedidoAutomaticoVentas();
	}
}
