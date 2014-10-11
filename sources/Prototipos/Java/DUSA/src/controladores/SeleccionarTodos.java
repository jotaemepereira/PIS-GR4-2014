package controladores;



import java.util.List;

import persistencia.PStockControlador;
import interfaces.ISeleccionador;
import interfaces.IStockPersistencia;


public class SeleccionarTodos implements ISeleccionador {
	
	public List <Long> getIDArticulos() throws Exception{
		//ver el cast
		PStockControlador ps =  (PStockControlador) FabricaPersistencia.getStockPersistencia();
		
		List<Long> articulosID = null;
		try {
			articulosID= ps.obtenerIdTodosLosArticulos();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return articulosID;
	}
	
}
