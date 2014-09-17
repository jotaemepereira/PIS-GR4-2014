package model;

import java.sql.Date;
import java.util.List;

import model.Enumerados.paymentType;

public class Pedido {
	
	Date fecha;
	paymentType formaDePago;
	List<LineaPedido> lineas;
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public paymentType getFormaDePago() {
		return formaDePago;
	}
	public void setFormaDePago(paymentType formaDePago) {
		this.formaDePago = formaDePago;
	}
	public List<LineaPedido> getLineas() {
		return lineas;
	}
	public void setLineas(List<LineaPedido> lineas) {
		this.lineas = lineas;
	}
	
	
}
