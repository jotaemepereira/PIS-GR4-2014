package beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import interfaces.ISistema;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.LineaPedido;
import model.Pedido;
import model.Enumerados.TipoFormaDePago;
import controladores.Excepciones;
import datatypes.DTLineaPedido;


@ManagedBean
@ViewScoped
public class GenerarPedidoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private ISistema instanciaSistema;

	private List<DTLineaPedido> pedidos = new ArrayList<DTLineaPedido>();
	private Boolean disableDesdeUltimoPedido = false;
	private Boolean disablePrediccionDePedido = false;
	private String hideElement = "hidden";
	private String formaDePago = "contado";
	
	// Getters y setters
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public List<DTLineaPedido> getPedidos() {
		return pedidos;
	}
	
	public void setPedidos(List<DTLineaPedido> pedidos) {
		this.pedidos = pedidos;
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
	
	public String getHideElement() {
		return hideElement;
	}
	
	public void setHideElement(String hideElement) {
		this.hideElement = hideElement;
	}
	
	public String getFormaDePago() {
		return formaDePago;
	}
	
	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
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
			//Imprimo y notifico error generado
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			disableDesdeUltimoPedido = false;
			disablePrediccionDePedido = false;
			hideElement = "hidden";
		}
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
			disableDesdeUltimoPedido = false;
			disablePrediccionDePedido = false;
			hideElement = "hidden";
		}
	}
	
	/**
	 * Recalcula el subtotal de la linea pedido
	 * 
	 * @param item a recalcular
	 */
	public void nuevoSubtotal(DTLineaPedido item) {

		item.setSubtotal(item.getCantidad() * item.getPrecioUnitario().floatValue());
	}

	/**
	 * Elimina la linea pedido de la tabla
	 * 
	 * @param item a eliminar
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
		//Transformo en LineaPedido para su persistencia
		for (DTLineaPedido dtLineaPedido : pedidos) {

			LineaPedido lPedido = new LineaPedido(
					dtLineaPedido.getIdArticulo(),
					dtLineaPedido.getNumeroArticulo(),
					dtLineaPedido.getCantidad());
			p.getLineas().add(lPedido);
		}

		try {

			this.instanciaSistema.realizarPedido(p);
			
			//Sin errores retorno para un posible nuevo pedido.
			pedidos.clear();
			disableDesdeUltimoPedido = false;
			disablePrediccionDePedido = false;
			hideElement = "hidden";

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, Excepciones.MENSAJE_OK_PEDIDO,
					""));
		} catch (Excepciones ex) {

//			ex.printStackTrace();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
		} catch (Exception e) {
			//Algún otro posible error 
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
	
}
