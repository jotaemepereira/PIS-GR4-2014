package model;



import java.util.List;

import controladores.Excepciones;
import controladores.FabricaPersistencia;
import interfaces.IPredictor;
import interfaces.ISeleccionador;
import interfaces.IStockPersistencia;

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
		IStockPersistencia sp = FabricaPersistencia.getStockPersistencia();
		
		for (Long idArticulo : articulos) {
			
			if (sp.existeArticuloDeDUSA(idArticulo)) {
				
				int cantidad = this.predictor.predecir(idArticulo);
				if (cantidad > 0){		
					pedido.agregarArticulo(idArticulo, cantidad);
				}
			}
		}
		
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