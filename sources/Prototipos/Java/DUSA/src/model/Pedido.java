package model;

import java.sql.Date;
import java.util.List;
import model.Enumerados.TipoFormaDePago;

//@autor Guille
public class Pedido {
	
	Long 				idPedido;
	Date 				fecha;
	TipoFormaDePago 	formaDePago;
	int					idUsuario; 
	List<LineaPedido>	lineas;
	
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
	
	public int getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public List<LineaPedido> getLineas() {
		return lineas;
	}
	public void setLineas(List<LineaPedido> lineas) {
		this.lineas = lineas;
	}
	
	
}
