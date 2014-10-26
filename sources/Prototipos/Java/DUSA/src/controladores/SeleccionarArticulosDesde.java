package controladores;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import interfaces.IFacturacionPersistencia;
import interfaces.ISeleccionador;

/**  
* @author Guille
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
		return pf.getIdArticulosEnPeriodo(fechaDesde, new Date(Calendar.getInstance().getTimeInMillis()));
	}
	
}
