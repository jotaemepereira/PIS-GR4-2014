package controladores;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import interfaces.IFacturacionPersistencia;
import interfaces.ISeleccionador;
import model.Articulo;

/**  
* @author Santiago
*
*/

public class SeleccionarArticulosDesde implements ISeleccionador  {

	Date fechaDesde;
	
	public SeleccionarArticulosDesde(Date fecha) {
		// TODO Auto-generated constructor stub
		super();
		this.fechaDesde = fecha;
	}
	
	/**
	*@author Guille
	*/		
	@Override
	public List <Long> getIDArticulos() throws Excepciones{
		
		IFacturacionPersistencia pf = FabricaPersistencia.getInstanciaFacturacionPersistencia();
		List<Long> articulosID = null;
		try {
			
			articulosID= pf.getIdArticulosEnPeriodo(fechaDesde, new Date(Calendar.getInstance().getTimeInMillis()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return articulosID;
	}
	
}
