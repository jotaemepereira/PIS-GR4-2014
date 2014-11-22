/**
 * Implementacion de ISeleccionador para obtener todos los articulos de DUSA.
 * 
 * Actualmente no se esta usando en la estrategia de generara pedido (si inicializando), para optimizar los tiempos al generar pedido.
 */
package controladores;

import java.util.List;

import interfaces.ISeleccionador;
import interfaces.IStockPersistencia;


public class SeleccionarTodos implements ISeleccionador {
	
	@Override
	public List <Long> getIDArticulos() throws Excepciones{
		
		IStockPersistencia ps = FabricaPersistencia.getStockPersistencia();
		
		return ps.obtenerIdTodosLosArticulos();
	}
	
}
