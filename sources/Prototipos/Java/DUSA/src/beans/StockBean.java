package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import controladores.Excepciones;
import controladores.FabricaSistema;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Presentacion;
import datatypes.DTFormasVenta;
import datatypes.DTLineaPedido;
import datatypes.DTProveedor;
import datatypes.DTTipoArticulo;

@ManagedBean
@ViewScoped
public class StockBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Articulo articulo = new Articulo();
	private int proveedor;
	private Map<Integer,DTProveedor> proveedores = new HashMap<Integer,DTProveedor>();
	private long codigoIdentificador;
	private Presentacion presentacion = new Presentacion();
	private List<Presentacion> presentaciones = new ArrayList<Presentacion>();
	private Droga droga = new Droga();
	private List<Droga> drogas = new ArrayList<Droga>();
	private AccionTer accionTer = new AccionTer();
	private List<AccionTer> accionesTer = new ArrayList<AccionTer>();
	private List<DTFormasVenta> formasVenta = new ArrayList<DTFormasVenta>();
	private List<DTTipoArticulo> tiposArticulo = new ArrayList<DTTipoArticulo>();
	private int[] tiposIVA;
	private List<DTLineaPedido> pedidos = new ArrayList<DTLineaPedido>();
	private String message;
	private String messageClass;
	private Boolean disableDesdeUltimoPedido = false;
	private Boolean disablePrediccionDePedido = false;
	private List<DTProveedor> listaProveedores;
	private List<DTProveedor> proveedoresSeleccionados = new ArrayList<DTProveedor>();
	
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
	public List<DTProveedor> getProveedoresSeleccionados() {
		return proveedoresSeleccionados;
	}
	public void setProveedoresSeleccionados(List<DTProveedor> proveedoresSeleccionados) {
		this.proveedoresSeleccionados = proveedoresSeleccionados;
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
	
	/**
	 * genera el pedido desde el ultimo pedido en el sistema
	 */
	public void desdeUltimoPedido(){
		disablePrediccionDePedido = true;
		disableDesdeUltimoPedido = true;
		pedidos.clear();
		
		DTLineaPedido dt = new DTLineaPedido();
		dt.setCantidad(5);
		dt.setIdArticulo(5);
		dt.setNumeroArticulo(5);
		dt.setNombreArticulo("Ernex");
		dt.setPrecioPonderado(40);
		dt.setPrecioUnitario(40);
		dt.setStockMinimo(9);
		dt.setSubtotal(200);
		pedidos.add(dt);
		
		dt = new DTLineaPedido();
		dt.setCantidad(1);
		dt.setIdArticulo(6);
		dt.setNumeroArticulo(6);
		dt.setNombreArticulo("Alerfast");
		dt.setPrecioPonderado(70);
		dt.setPrecioUnitario(70);
		dt.setStockMinimo(4);
		dt.setSubtotal(70);
		pedidos.add(dt);
		
		dt = new DTLineaPedido();
		dt.setCantidad(4);
		dt.setIdArticulo(7);
		dt.setNumeroArticulo(7);
		dt.setNombreArticulo("Alerfast forte");
		dt.setPrecioPonderado(90);
		dt.setPrecioUnitario(90);
		dt.setStockMinimo(4);
		dt.setSubtotal(360);
		pedidos.add(dt);
	}
	
	/**
	 * genera el pedido segun la prediccion en base al pasado
	 */
	public void prediccionDePedido(){
		disablePrediccionDePedido = true;
		disableDesdeUltimoPedido = true;
		pedidos.clear();
		
		DTLineaPedido dt = new DTLineaPedido();
		dt.setCantidad(3);
		dt.setIdArticulo(1);
		dt.setNumeroArticulo(1);
		dt.setNombreArticulo("Perifar 400");
		dt.setPrecioPonderado(45);
		dt.setPrecioUnitario(45);
		dt.setStockMinimo(7);
		dt.setSubtotal(135);
		pedidos.add(dt);
		
		dt = new DTLineaPedido();
		dt.setCantidad(2);
		dt.setIdArticulo(2);
		dt.setNumeroArticulo(2);
		dt.setNombreArticulo("Aspirina");
		dt.setPrecioPonderado(50);
		dt.setPrecioUnitario(50);
		dt.setStockMinimo(9);
		dt.setSubtotal(100);
		pedidos.add(dt);
		
		dt = new DTLineaPedido();
		dt.setCantidad(3);
		dt.setIdArticulo(3);
		dt.setNumeroArticulo(3);
		dt.setNombreArticulo("Buscapina");
		dt.setPrecioPonderado(30);
		dt.setPrecioUnitario(30);
		dt.setStockMinimo(7);
		dt.setSubtotal(90);
		pedidos.add(dt);
		
		dt = new DTLineaPedido();
		dt.setCantidad(6);
		dt.setIdArticulo(4);
		dt.setNumeroArticulo(4);
		dt.setNombreArticulo("Biogrip");
		dt.setPrecioPonderado(10);
		dt.setPrecioUnitario(10);
		dt.setStockMinimo(10);
		dt.setSubtotal(60);
		pedidos.add(dt);
	}
	
	public void removeItem(DTLineaPedido item) {
		pedidos.remove(item);
	}
	
	public void enviarPedido(){
		System.out.println("******* ENVIAR PEDIDO ********");
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(pedidos.isEmpty()){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Excepciones.MENSAJE_PEDIDO_VACIO, ""));
			return;
		}
		
		pedidos.clear();
		disableDesdeUltimoPedido = false;
		disablePrediccionDePedido = false;
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				Excepciones.MENSAJE_OK_PEDIDO, ""));
	}
	
	public void cancelarPedido(){
		System.out.println("******* CANCELAR PEDIDO ********");
		pedidos.clear();
		disableDesdeUltimoPedido = false;
		disablePrediccionDePedido = false;
	}
	

	
	public void agregarProveedor(){
		FacesContext context = FacesContext.getCurrentInstance();
		if (codigoIdentificador != 0 && proveedor != 0){
			if (!existeProveedor(proveedor)){
				DTProveedor p = new DTProveedor();
				p.setIdProveedor(proveedor);
				p.setNombreComercial(proveedores.get(proveedor).getNombreComercial());
				p.setCodigoIdentificador(codigoIdentificador);
				this.proveedoresSeleccionados.add(p);
			}
			else{
				context.addMessage(
				null,
				new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Ya seleccionó el proveedor.",
						""));
			}
		}
		else{
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							"Debe seleccionar un proveedor e ingresar su código que lo identifica.",
							""));
		}
	}
	
	private boolean existeProveedor(int proveedor) {
		boolean ret = false;
		Iterator<DTProveedor> i = proveedoresSeleccionados.iterator();
		while (i.hasNext() && !ret){
			ret = proveedor == i.next().getIdProveedor();
		}
		return ret;
	}
	public void altaArticulo(){		
		//FacesContext context = FacesContext.getCurrentInstance();
		try {
			/* Llamo a la logica para que se de de alta el articulo en el sistema y
			 en caso de error lo muestro */
			Iterator i = proveedoresSeleccionados.iterator();
			while (i.hasNext()){
				DTProveedor p = new DTProveedor();
				p.setIdProveedor(proveedor);
				p.setNombreComercial(proveedores.get(proveedor).getNombreComercial());
				p.setCodigoIdentificador(codigoIdentificador);
				this.articulo.agregarProveedor(p);
			}
			FabricaSistema.getISistema().altaArticulo(articulo);
		} catch (Excepciones e) {
			if (e.getErrorCode() == Excepciones.ADVERTENCIA_DATOS) {
//				context.addMessage(
//						null,
//						new FacesMessage(
//								FacesMessage.SEVERITY_WARN,
//								e.getMessage(),
//								""));
				this.message = e.getMessage();
				this.messageClass = "alert alert-danger";
			} else {
//				context.addMessage(
//						null,
//						new FacesMessage(
//								FacesMessage.SEVERITY_ERROR,
//								e.getMessage(),
//								""));
				this.message = e.getMessage();
				this.messageClass = "alert alert-danger";
				return;
			}
		}
		// si todo bien aviso y vacio el formulario
		//context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,Excepciones.MENSAJE_OK_ALTA, ""));
		this.message = Excepciones.MENSAJE_OK_ALTA;
		this.messageClass = "alert alert-success";
		this.articulo = new Articulo();
	}
	
	public void cancelarAltaArticulo(){
		
	}
	
	public StockBean(){
		
		//Cargo los proveedores de la base de datos
		try {
			this.proveedores = FabricaSistema.getISistema().obtenerProveedores();
			this.setListaProveedores(new ArrayList<DTProveedor>(this.proveedores.values()));
		} catch (Excepciones e) {
			this.message = e.getMessage();
			this.messageClass = "alert alert-danger";
		}
			
		//Cargo tipos de articulo para el combo
		DTTipoArticulo ta = new DTTipoArticulo();
		ta.setTipoArticulo(model.Enumerados.tipoArticulo.MEDICAMENTO);
		ta.setDescripcion("Medicamento");
		tiposArticulo.add(ta);
		ta = new DTTipoArticulo();
		ta.setTipoArticulo(model.Enumerados.tipoArticulo.PERFUMERIA);
		ta.setDescripcion("PerfumerÃ­a");
		tiposArticulo.add(ta);
		ta = new DTTipoArticulo();
		ta.setTipoArticulo(model.Enumerados.tipoArticulo.OTROS);
		ta.setDescripcion("Otros");
		tiposArticulo.add(ta);
		
		//Cargo formas de venta para el combo
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
		fv.setDescripcion("Control mÃ©dico");
		formasVenta.add(fv);
	}

}
