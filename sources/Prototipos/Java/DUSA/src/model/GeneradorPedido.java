package model;



import java.util.List;

import controladores.Excepciones;
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
	/**
	 * @author Guille
	 * @return Pedido con todas las lineaPedido generado con el ISeleccionador y IPredictor
	 * @throws Excepciones
	 */
	public Pedido generar() throws Excepciones{
		
		Pedido pedido = new Pedido();
		List<Long> articulos = this.seleccionador.getIDArticulos();
		
//		System.out.println("Empieza " + new Date(Calendar.getInstance().getTimeInMillis()));
		for (Long idArticulo : articulos) {
			
			int cantidad = this.predictor.predecir(idArticulo);
			if (cantidad > 0){		

				LineaPedido lPedido = new LineaPedido();
				lPedido.setIdArticulo(idArticulo);
				lPedido.setCantidad(cantidad);
				
				pedido.getLineas().add(lPedido);
			}
		}
		
//		System.out.println("Termina " + new Date(Calendar.getInstance().getTimeInMillis()));
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