package controladores;

import java.sql.Date;
import java.util.Calendar;

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
		// TODO Auto-generated constructor stub
		super();
		this.fechaDesde = f;
	}
	
	/**
	 * @author Guille
	 */
	@Override
	public int predecir(Long idArticulo) throws Exception {
	
		IFacturacionPersistencia fp = FabricaPersistencia.getInstanciaFacturacionPersistencia();
		return fp.cantidadVendidaEnPeriodo(idArticulo, fechaDesde, new Date(Calendar.getInstance().getTimeInMillis()));
	}
	
}
