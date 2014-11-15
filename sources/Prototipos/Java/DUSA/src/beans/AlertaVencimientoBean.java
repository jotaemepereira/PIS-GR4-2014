package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Articulo;

@ManagedBean
@ViewScoped
public class AlertaVencimientoBean implements Serializable {

	private ISistema instanciaSistema;
	
	private Date fechaDesde;
	private Date fechaHasta;
	private List<Articulo> articulos;
	private Date[] nuevosVencimientos;

	public void articulosQueSeVencenEnPeriodo(){
		
	}
	
	public AlertaVencimientoBean(){
		
	}
	
	public ISistema getInstanciaSistema() {
		return instanciaSistema;
	}
	public void setInstanciaSistema(ISistema instanciaSistema) {
		this.instanciaSistema = instanciaSistema;
	}
	public List<Articulo> getArticulos() {
		return articulos;
	}
	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}
	public Date[] getNuevosVencimientos() {
		return nuevosVencimientos;
	}
	public void setNuevosVencimientos(Date[] nuevosVencimientos) {
		this.nuevosVencimientos = nuevosVencimientos;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
}
