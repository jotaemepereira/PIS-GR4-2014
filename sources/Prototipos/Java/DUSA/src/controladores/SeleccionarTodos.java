package controladores;



import java.util.List;

import interfaces.ISeleccionador;
import interfaces.IStockPersistencia;


public class SeleccionarTodos implements ISeleccionador {
	
	public List <Long> getIDArticulos() throws Excepciones{
		
		IStockPersistencia ps = FabricaPersistencia.getStockPersistencia();
		
		return ps.obtenerIdTodosLosArticulos();
	}
	
}
