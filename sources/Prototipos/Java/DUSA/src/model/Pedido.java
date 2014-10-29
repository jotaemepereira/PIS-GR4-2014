package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.Enumerados.TipoFormaDePago;

//@autor Guille
public class Pedido {
	
	private Long 				idPedido;
	private Date 				fecha;
	private TipoFormaDePago 	formaDePago;
	private Usuario				usuario; 
	private List<LineaPedido>	lineas;
	
	public Pedido() {
		
		this.idPedido = new Long(0);
		this.fecha = null;
		this.usuario = new Usuario();
		this.lineas = new ArrayList<LineaPedido>();
		
	}
	
	//Getters y Setters
	public Long getIdPedido() {
		return idPedido;
	}
	
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public TipoFormaDePago getFormaDePago() {
		return formaDePago;
	}
	
	public void setFormaDePago(TipoFormaDePago formaDePago) {
		this.formaDePago = formaDePago;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<LineaPedido> getLineas() {
		return lineas;
	}
	public void setLineas(List<LineaPedido> lineas) {
		this.lineas = lineas;
	}
	
	
}
