package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import controladores.Excepciones;
import controladores.FabricaSistema;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Presentacion;
import model.Enumerados;
import datatypes.DTFormasVenta;
import datatypes.DTLineaPedido;
import datatypes.DTProveedor;

@ManagedBean
@ViewScoped
public class StockBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Articulo articulo = new Articulo();
	private DTProveedor proveedor = new DTProveedor();
	private List<DTProveedor> proveedores = new ArrayList<DTProveedor>();
	private List<DTProveedor> proveedoresSeleccionados = new ArrayList<DTProveedor>();
	private long codigoIdentificador;
	private Presentacion presentacion = new Presentacion();
	private List<Presentacion> presentaciones = new ArrayList<Presentacion>();
	private Droga droga = new Droga();
	private List<Droga> drogas = new ArrayList<Droga>();
	private AccionTer accionTer = new AccionTer();
	private List<AccionTer> accionesTer = new ArrayList<AccionTer>();
	private List<DTFormasVenta> formasVenta = new ArrayList<DTFormasVenta>();
	private int[] tiposIVA;
	private List<DTLineaPedido> pedidos = new ArrayList<DTLineaPedido>();
	private int iniciado;

	
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
	
	public void pedidoAutomaticoVentas() {
		if (iniciado == 0) {
	/*	for (int i=0; i<5; i++) {
			DTLineaPedido dt = new DTLineaPedido();
			dt.setCantidad(i);
			dt.setIdArticulo(i);
			dt.setNumeroArticulo(i);
			dt.setNombreArticulo("Nombre"+i);
			dt.setPrecioPonderado(i);
			dt.setPrecioUnitario(i);
			dt.setStockMinimo(i);
			dt.setSubtotal(i);
			pedidos.add(dt);
		 }*/
		 iniciado = 1;
		}
		//return FabricaSistema.getISistema().pedidoAutomaticoVentas();
	}
	
	/**
	 * genera el pedido desde el ultimo pedido en el sistema
	 */
	public void desdeUltimoPedido(){
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
		
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				Excepciones.MENSAJE_OK_PEDIDO, ""));
	}
	
	public void cancelarPedido(){
		System.out.println("******* CANCELAR PEDIDO ********");
		pedidos.clear();
	}
	

	
	public void agregarProveedor(){
		DTProveedor p = new DTProveedor();
		p.setIdProveedor(proveedor.getIdProveedor());
		p.setNombreComercial(proveedor.getNombreComercial());
		p.setCodigoIdentificador(codigoIdentificador);
		proveedoresSeleccionados.add(p);
	}
	
	public void altaArticulo(){
		try {
			FabricaSistema.getISistema().altaArticulo(articulo);
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cancelarAltaArticulo(){
		
	}
	
	public StockBean(){
		DTProveedor p1 = new DTProveedor();
		p1.setIdProveedor(1);
		p1.setNombreComercial("DUSA");
		proveedores.add(p1);
			
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
		fv.setDescripcion("Control m�dico");
		formasVenta.add(fv);
		
		pedidoAutomaticoVentas();
	}
}
