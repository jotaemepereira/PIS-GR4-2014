package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.sun.org.apache.xpath.internal.functions.Function;

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
	
	//Proveedores
	private int proveedor;
	private long codigoIdentificador;
	private Map<Integer,DTProveedor> proveedores;	
	private List<DTProveedor> listaProveedores;
	private List<DTProveedor> proveedoresSeleccionados = new ArrayList<DTProveedor>();
	
	//Presentaciones	
	private Presentacion presentacion = new Presentacion();
	private List<Presentacion> presentaciones = new ArrayList<Presentacion>();
	
	//Drogas
	private long[] drogasSeleccionadas;
	private Map<Long,Droga> drogas;
	private List<Droga> listaDrogas;
	
	//Acciones Terapeuticas
	private long[] accionesTerSeleccionadas;
	private Map<Long,AccionTer> accionesTer;
	private List<AccionTer> listaAccionesTer;
	
	private List<DTFormasVenta> formasVenta = new ArrayList<DTFormasVenta>();
	private List<DTTipoArticulo> tiposArticulo = new ArrayList<DTTipoArticulo>();
	private int[] tiposIVA;
	private List<DTLineaPedido> pedidos = new ArrayList<DTLineaPedido>();
	private String message;
	private String messageClass;
	private Boolean disableDesdeUltimoPedido = false;
	private Boolean disablePrediccionDePedido = false;
	private String busqueda = "";
	private List<Articulo> resBusqueda;
	
	
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
	public long[] getDrogasSeleccionadas() {
		return drogasSeleccionadas;
	}
	public void setDrogasSeleccionadas(long[] drogasSeleccionadas) {
		this.drogasSeleccionadas = drogasSeleccionadas;
	}
	public Map<Long, Droga> getDrogas() {
		return drogas;
	}
	public void setDrogas(Map<Long, Droga> drogas) {
		this.drogas = drogas;
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
	public Map<Long, AccionTer> getAccionesTer() {
		return accionesTer;
	}
	public void setAccionesTer(Map<Long, AccionTer> accionesTer) {
		this.accionesTer = accionesTer;
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
	public String getBusqueda() {
		return busqueda;
	}
	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}
	
	/**
	 * @return the resBusqueda
	 */
	public List<Articulo> getResBusqueda() {
		return resBusqueda;
	}
	/**
	 * @param resBusqueda the resBusqueda to set
	 */
	public void setResBusqueda(List<Articulo> resBusqueda) {
		this.resBusqueda = resBusqueda;
	}
	
	/**
	 * genera el pedido desde el ultimo pedido en el sistema
	 */
	public void desdeUltimoPedido(){
		disablePrediccionDePedido = true;
		disableDesdeUltimoPedido = true;
		pedidos.clear();
		
		try {
			
			pedidos = FabricaSistema.getISistema().generarPedidoEnBaseAPedidoAnterior();
		} catch (Exception e) {
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							e.getMessage(),
							""));
		}
		
		/*
		DTLineaPedido dt = new DTLineaPedido();
		dt.setCantidad(5);
		dt.setIdArticulo(5);
		dt.setNumeroArticulo(5);
//		dt.setNombreArticulo("Ernex");
		dt.setDescripcionArticulo("Ernex");
		dt.setPrecioPonderado(40);
		dt.setPrecioUnitario(40);
		dt.setStockMinimo(9);
		dt.setSubtotal(200);
		pedidos.add(dt);
		
		dt = new DTLineaPedido();
		dt.setCantidad(1);
		dt.setIdArticulo(6);
		dt.setNumeroArticulo(6);
//		dt.setNombreArticulo("Alerfast");
		dt.setDescripcionArticulo("Alerfast");
		dt.setPrecioPonderado(70);
		dt.setPrecioUnitario(70);
		dt.setStockMinimo(4);
		dt.setSubtotal(70);
		pedidos.add(dt);
		
		dt = new DTLineaPedido();
		dt.setCantidad(4);
		dt.setIdArticulo(7);
		dt.setNumeroArticulo(7);
//		dt.setNombreArticulo("Alerfast forte");
		dt.setDescripcionArticulo("Alerfast forte");
		dt.setPrecioPonderado(90);
		dt.setPrecioUnitario(90);
		dt.setStockMinimo(4);
		dt.setSubtotal(360);
		pedidos.add(dt);
		
		*/
	}
	
	/**
	 * genera el pedido segun la prediccion en base al pasado
	 */
	public void prediccionDePedido(){
		disablePrediccionDePedido = true;
		disableDesdeUltimoPedido = true;
		pedidos.clear();
		
		/*
		DTLineaPedido dt = new DTLineaPedido();
		dt.setCantidad(3);
		dt.setIdArticulo(1);
		dt.setNumeroArticulo(1);
//		dt.setNombreArticulo("Perifar 400");
		dt.setDescripcionArticulo("Perifar 400");
		dt.setPrecioPonderado(45);
		dt.setPrecioUnitario(45);
		dt.setStockMinimo(7);
		dt.setSubtotal(135);
		pedidos.add(dt);
		
		dt = new DTLineaPedido();
		dt.setCantidad(2);
		dt.setIdArticulo(2);
		dt.setNumeroArticulo(2);
//		dt.setNombreArticulo("Aspirina");
		dt.setDescripcionArticulo("Aspirina");
		dt.setPrecioPonderado(50);
		dt.setPrecioUnitario(50);
		dt.setStockMinimo(9);
		dt.setSubtotal(100);
		pedidos.add(dt);
		
		dt = new DTLineaPedido();
		dt.setCantidad(3);
		dt.setIdArticulo(3);
		dt.setNumeroArticulo(3);
//		dt.setNombreArticulo("Buscapina");
		dt.setDescripcionArticulo("Buscapina");
		dt.setPrecioPonderado(30);
		dt.setPrecioUnitario(30);
		dt.setStockMinimo(7);
		dt.setSubtotal(90);
		pedidos.add(dt);
		
		dt = new DTLineaPedido();
		dt.setCantidad(6);
		dt.setIdArticulo(4);
		dt.setNumeroArticulo(4);
//		dt.setNombreArticulo("Biogrip");
		dt.setDescripcionArticulo("Biogrip");
		dt.setPrecioPonderado(10);
		dt.setPrecioUnitario(10);
		dt.setStockMinimo(10);
		dt.setSubtotal(60);
		pedidos.add(dt);
		*/
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
		FacesContext context = FacesContext.getCurrentInstance();
		if (!proveedoresSeleccionados.isEmpty()){
			try {
				/* Cargo los proveedores seleccionados en el articulo */
				Iterator<DTProveedor> i = proveedoresSeleccionados.iterator();
				while (i.hasNext()){
					DTProveedor next = i.next();
					DTProveedor p = new DTProveedor();
					p.setIdProveedor(next.getIdProveedor());
					p.setNombreComercial(proveedores.get(next.getIdProveedor()).getNombreComercial());
					p.setCodigoIdentificador(next.getCodigoIdentificador());
					this.articulo.agregarProveedor(p);
				}
				/* Llamo a la logica para que se de de alta el articulo en el sistema y
				 en caso de error lo muestro */
				FabricaSistema.getISistema().altaArticulo(articulo);
				// si todo bien aviso y vacio el formulario
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,Excepciones.MENSAJE_OK_ALTA, ""));
				this.articulo = new Articulo();
				this.proveedoresSeleccionados = new ArrayList<DTProveedor>();
				this.proveedor = 0;
				this.codigoIdentificador = 0;
			} catch (Excepciones e) {
				if (e.getErrorCode() == Excepciones.ADVERTENCIA_DATOS) {
					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									e.getMessage(),
									""));
				} else {
					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									e.getMessage(),
									""));
				}
			}	
		}
		else{
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Debe seleccionar al menos un proveedor",
							""));
		}
	}
	
	public void cancelarAltaArticulo(){
		refresh();
	}
	
	public void refresh() {
		  FacesContext context = FacesContext.getCurrentInstance();
		  Application application = context.getApplication();
		  ViewHandler viewHandler = application.getViewHandler();
		  UIViewRoot viewRoot = viewHandler.createView(context, context
		   .getViewRoot().getViewId());
		  context.setViewRoot(viewRoot);
		  context.renderResponse(); //Optional
		}
	
	public StockBean(){
		
		//Cargo los proveedores de la base de datos
		cargarProveedores();
		
		//Cargo las drogas de la base de datos
		cargarDrogas();
		
		//Cargo las acciones terapéuticas de la base de datos
		cargarAccionesTerapeuticas();
		
		//Cargo tipos de articulo para el combo
		cargarTiposArticulo();
		
		//Cargo formas de venta para el combo
		cargarFormasVenta();
	}
	
	public void cargarProveedores(){
		try {
			this.proveedores = FabricaSistema.getISistema().obtenerProveedores();
			this.listaProveedores = new ArrayList<DTProveedor>(this.proveedores.values());
		} catch (Excepciones e) {
			this.message = e.getMessage();
			this.messageClass = "alert alert-danger";
		}
	}
	
	public void cargarDrogas(){
		try{
			this.drogas = FabricaSistema.getISistema().obtenerDrogas();
			this.listaDrogas = new ArrayList<Droga>(this.drogas.values());
		} catch (Excepciones e){
			this.message = e.getMessage();
			this.messageClass = "alert alert-danger";
		}
	}
	
	public void cargarAccionesTerapeuticas(){
		try{
			this.accionesTer = FabricaSistema.getISistema().obtenerAccionesTerapeuticas();
			this.listaAccionesTer = new ArrayList<AccionTer>(this.accionesTer.values());
		} catch (Excepciones e){
			this.message = e.getMessage();
			this.messageClass = "alert alert-danger";
		}
	}
	
	public void cargarTiposArticulo(){
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
	
	public void cargarFormasVenta(){
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
	
	public void buscarArticulos(){
		try {
			resBusqueda = FabricaSistema.getISistema().buscarArticulos(busqueda);
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
