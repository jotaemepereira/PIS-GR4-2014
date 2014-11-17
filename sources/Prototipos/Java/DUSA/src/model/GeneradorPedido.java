package model;



import java.util.Calendar;
import java.util.Date;
import java.util.List;

import controladores.Excepciones;
import datatypes.DTLineaPedido;
import interfaces.IPredictor;
import interfaces.ISeleccionador;

/**  
* @author Santiago
*
*/
public class GeneradorPedido {
	
	ISeleccionador 	seleccionador;
	IPredictor		predictor;
	
	/**
	 * @author Guille
	 * @param sa Encargado de preseleccionar los artículos a predecir.
	 * @param pr Encargado de la predicción por la cual se generan las cantidades del pedido.  
	 */
	public GeneradorPedido(ISeleccionador sa, IPredictor pr) {
		
		super();
		this.seleccionador = sa;
		this.predictor = pr;
	}
	
	public List<DTLineaPedido> generar() throws Excepciones{
		List<DTLineaPedido> pedido = this.predictor.predecir();
		return pedido;
	}
	
	//Setters
	
	public void setPredictor(IPredictor predictor) {
		this.predictor = predictor;
	}
	
	public IPredictor getPredictor() {
		return predictor;
	}
	
	public void setSeleccionador(ISeleccionador seleccionador) {
		this.seleccionador = seleccionador;
	}
	
	public ISeleccionador getSeleccionador() {
		return seleccionador;
	}
}