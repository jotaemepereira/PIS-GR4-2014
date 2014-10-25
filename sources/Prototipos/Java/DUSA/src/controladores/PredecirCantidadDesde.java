package controladores;

import java.util.Calendar;
import java.util.Date;

import interfaces.IFacturacionPersistencia;
import interfaces.IPredictor;

/**  
* @author Santiago
*
*/

public class PredecirCantidadDesde implements IPredictor  {

	Date fechaDesde;
	
	/**
	 * @author Guille
	 */
	public PredecirCantidadDesde(Date f) {
		
		super();
		this.fechaDesde = f;
	}
	
	/**
	 * @author Guille
	 */
	@Override
	public int predecir(Long idArticulo) throws Excepciones {
	
		IFacturacionPersistencia fp = FabricaPersistencia.getInstanciaFacturacionPersistencia();
		return fp.cantidadVendidaEnPeriodo(idArticulo, fechaDesde, new Date(Calendar.getInstance().getTimeInMillis()));
	}
	
}
