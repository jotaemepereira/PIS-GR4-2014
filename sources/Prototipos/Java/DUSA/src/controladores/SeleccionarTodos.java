package controladores;



import java.util.List;

import interfaces.ISeleccionador;
import interfaces.IStockPersistencia;


public class SeleccionarTodos implements ISeleccionador {
	
	public List <Long> getIDArticulos() throws Excepciones{
		//ver el cast
		IStockPersistencia ps = FabricaPersistencia.getStockPersistencia();
		
		List<Long> articulosID = null;
		try {
			articulosID= ps.obtenerIdTodosLosArticulos();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return articulosID;
	}
	
}
